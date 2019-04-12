package data;

import entity.Column;
import entity.Table;
import util.PropertiesUtil;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static constant.SystemConstant.PROJECT_TYPE_FILE;

/**
 * @author Ming
 * @version 1.0
 * @date 2019/3/14 8:48
 * @describe obtain meta
 */
public interface MetaData {

    /**
     * 获取数据源中所有的表信息
     * @return
     * @throws SQLException
     */
    List<Table> tables() throws SQLException;

    /**
     * 依据表名获取指定的表信息
     * @param tableName
     * @return
     * @throws SQLException
     */
    Table table(String tableName) throws SQLException;
    /**
     * 依据表名获取该表的DDL
     * @param tableName
     * @return
     * @throws SQLException
     */
    String tableDdl(String tableName) throws SQLException;
    /**
     * 依据表名获取该表的各列字段信息
     * @param tableName
     * @return
     */
    List<Column> columns(String tableName);
    /**
     * 获取数据类型转换类
     * @return
     * @throws IOException
     */
    default Conversion getConversion() throws IOException {
       return null!=PROJECT_TYPE_FILE? new Conversion(new PropertiesUtil(PROJECT_TYPE_FILE.openStream()).toMap()) :new Conversion();
    }
}
