package web;

import base.GenerateParameter;
import base.GenerationUtil;
import entity.Table;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ming
 * @version 1.0
 * @date 2019/4/8 10:50
 * @describe
 */
public class GenerateServlet extends GenerateWebServletResources {

    private GenerationUtil generationUtil;

    public GenerationUtil getGenerationUtil() {
        return generationUtil;
    }

    public void setGenerationUtil(GenerationUtil generationUtil) {
        this.generationUtil = generationUtil;
    }

    public GenerateServlet() {
        super("/webPages");
    }

    /**
     * 路由解析
     * @param request
     * @return
     * 此处可以考虑使用注解，构建注解解析器实现类似spring mvc中注解映射路径
     */
    @Override
    protected String jsonResult(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        String servletPath = request.getServletPath();
        String requestURI = request.getRequestURI();
        String path = requestURI.substring(contextPath.length() + servletPath.length());
        if (path.contains("list")) {
            return tableList(request);
        }
        if (path.contains("code")) {
            return produce(request);
        }
        return null;
    }

    private String produce(HttpServletRequest request) {
        try {
            GenerateParameter parameter = new GenerateParameter();
            String basePackage = request.getParameter("basePackage");
            String coverage = request.getParameter("coverage");
            String primaryKeyType = request.getParameter("frontGeneratePackage");
            if(null!= basePackage && !"".equals(basePackage))
                parameter.setBasePackage(basePackage);
            if(null!= coverage && !"".equals(coverage))
                parameter.setCoverage("true".equals(coverage));
            if(null!= primaryKeyType && !"".equals(primaryKeyType))
                parameter.setPrimaryKeyType(primaryKeyType);

            parameter.setTree("true".equals(request.getParameter("isTree")));
            parameter.setFrontGeneratePackage(request.getParameter("frontGeneratePackage"));


            String templates = request.getParameter("templates");
            String tableStr = request.getParameter("tables");
            if(null!=tableStr && null!=templates){
                parameter.setTemplates(Arrays.stream(templates.split(",")).map(Integer::valueOf).collect(Collectors.toList()));
                String[] tables = tableStr.split(",");
                generationUtil.generateCode(parameter,tables);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return "success";
    }

    private String tableList(HttpServletRequest request) {
        try {
            List<Table> tableList = generationUtil.getMetaData().tables();
            String tableName = request.getParameter("tableName");
            if (null != tableName && !"".equals(tableName)) {
                final String lowTable = tableName.toLowerCase();
                tableList = tableList.stream().filter(e->e.getTableName().contains(lowTable)).collect(Collectors.toList());
            }
            LayUiTableJSONResult<Table> result = new LayUiTableJSONResult();
            result.setCode(0);
            result.setCount(tableList.size());
            //result.setMsg("查询成功");
            result.setData(tableList);
            return result.toString();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uri = request.getRequestURI();
        try {
            List<Table> tables = generationUtil.getMetaData().tables();
            if (uri.contains("/generate/index.html")) {

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/
}
