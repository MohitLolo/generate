package base;

import constant.SystemConstant;
import data.MetaData;
import data.MySQLMetaData;
import data.OracleMetaData;
import data.TemplateEnum;
import entity.Column;
import entity.Table;
import util.ConnectionUtil;
import util.FreemarkerUtil;
import util.StringUtil;

import javax.sql.DataSource;
import java.io.File;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ming
 * @version 1.0
 * @date 2019/3/15 9:35
 * @describe
 */
public class GenerationUtil {

    private ConnectionUtil connectionUtil;

    private MetaData metaData;

    private GenerateProperties generateProperties = new GenerateProperties();

    public GenerationUtil(DataSource dataSource) {
        this.connectionUtil = new ConnectionUtil(dataSource);
        initMetaData();
    }

    public GenerationUtil(String driver, String url, String username, String password) {
        this.connectionUtil = new ConnectionUtil(driver,url,username,password);
        initMetaData();
    }

    public MetaData getMetaData() {
        return this.metaData;
    }

    private void initMetaData() {
        try {
            String dataBaseType = connectionUtil.dataBaseType().toUpperCase();
            if (dataBaseType.contains(SystemConstant.DATABASE_TYPE_MYSQL)) {
                this.metaData = new MySQLMetaData(connectionUtil);
            } else if (dataBaseType.contains(SystemConstant.DATABASE_TYPE_ORACLE)) {
                this.metaData = new OracleMetaData(connectionUtil);
            } else {
                throw new RuntimeException("unsupported database types");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * main generation function
     * @param parameter
     * @param tables
     */
    public void generateCode(GenerateParameter parameter,String... tables) {
        if(null==tables) {
            try {
                tables = metaData.tables().stream().map(Table::getTableName).toArray(String[]::new);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println("=================generate begin==================");
        Arrays.stream(tables).forEach(table -> {
            try {
                /**
                 * metaData
                 */
                Table tableInfo = metaData.table(table);
                List<Column> columns = metaData.columns(table);

                /**
                 * parameter
                 */
                String primaryKeyType = parameter.getPrimaryKeyType()==null?
                        generateProperties.getPrimaryKeyType():parameter.getPrimaryKeyType();
                String alias = StringUtil.upperTable(tableInfo.getTableName());
                String aliasLow = StringUtil.upperField(tableInfo.getTableName());
                String basePackage = parameter.getBasePackage()==null?generateProperties.getBasePackage():parameter.getBasePackage();
                String project = parameter.getProjectName()==null?generateProperties.getProjectName():parameter.getProjectName();
                String frontGeneratePackage = (parameter.getFrontGeneratePackage()==null?
                        generateProperties.getFrontGeneratePackage():
                        parameter.getFrontGeneratePackage())+File.separator+alias.toLowerCase()+File.separator;
                String applicationProducePath = SystemConstant.PACKAGE_PATH + basePackage.replace(".", File.separator)+File.separator;

                /**
                 * freemarker data
                 */
                Map<String,Object> data = new HashMap<>();
                data.put("project",project);
                data.put("table",tableInfo.getTableName());
                data.put("primaryKeyType",primaryKeyType);
                data.put("alias",alias);
                data.put("aliasLow",aliasLow);
                data.put("tableComment",tableInfo.getTableComment());
                data.put("columns",columns);
                data.put("basePackage",basePackage);

                /**
                 * generate
                 */
                parameter.getTemplates().forEach(numb->{
                    TemplateEnum template = TemplateEnum.getEnum(numb);
                    String fileName = alias+template.suffix;
                    data.put("belongPackage",basePackage+"."+template.belong.replace(File.separator,"."));
                    switch (template.fileType) {
                        case 0:  // .java
                            data.put("className",fileName);
                            FreemarkerUtil.freemarkerGenerate(SystemConstant.TEMPLATE_DIRECTORY,
                                    template.template,
                                    applicationProducePath+template.belong,
                                    fileName+".java",data,parameter.isCoverage());
                            break;
                        case 1:  //.jsp
                            FreemarkerUtil.freemarkerGenerate(SystemConstant.TEMPLATE_DIRECTORY,
                                    template.template,
                                    frontGeneratePackage, template.template.replace("_",
                                            alias.toLowerCase()).replace(".ftl","")+".jsp",data,parameter.isCoverage());
                            break;
                        case 2:  //.js
                            FreemarkerUtil.freemarkerGenerate(SystemConstant.TEMPLATE_DIRECTORY,template.template,
                                    frontGeneratePackage,fileName+".js",data,parameter.isCoverage());
                            break;
                        case 3: //xml
                            data.put("daoName",alias+template.suffix);
                            FreemarkerUtil.freemarkerGenerate(SystemConstant.TEMPLATE_DIRECTORY,template.template,
                                    applicationProducePath+template.belong,fileName+".xml",data,parameter.isCoverage());
                            break;
                        default:
                            break;
                    }
                });

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                connectionUtil.release();
            }
        });
        System.out.println("=================generate finish==================");
    }

}
