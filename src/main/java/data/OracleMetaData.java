package data;

import constant.sql.MySQL;
import constant.sql.Oracle;
import entity.Column;
import entity.Table;
import util.ConnectionUtil;
import util.StringUtil;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ming
 * @version 1.0
 * @date 2019/3/14 9:56
 * @describe oracle;
 */
public class OracleMetaData implements MetaData {

    ConnectionUtil connectionUtil;

    /*public OracleMetaData(DataSource dataSource) {
        connectionUtil = new ConnectionUtil(dataSource);
    }

    public OracleMetaData(String driver, String url, String username, String password) {
        this.connectionUtil = new ConnectionUtil(driver,url,username,password);
    }*/

    public OracleMetaData(ConnectionUtil connectionUtil) {
        this.connectionUtil = connectionUtil;
    }

    @Override
    public List<Table> tables() throws SQLException {
        try {
            List<Table> tables = new ArrayList<>();
            ResultSet resultSet = connectionUtil.executeQuery(Oracle.SHOW_TABLES);
            while (resultSet.next()) {
                Table table = new Table(resultSet.getString(1),
                        resultSet.getString(2), null, null);
                tables.add(table);
            }
            return tables;
        } finally {
            connectionUtil.release();
        }
    }

    @Override
    public Table table(String tableName) throws SQLException {
        try {
            ResultSet resultSet = connectionUtil.executeQuery(String.format(Oracle.SINGLE_TABLE, tableName));
            while (resultSet.next()) {
                return new Table(resultSet.getString(1),
                        resultSet.getString(2), null, null);
            }
            return null;
        } finally {
            connectionUtil.release();
        }
    }

    @Override
    public String tableDdl(String tableName) throws SQLException {
        try {
            ResultSet resultSet = connectionUtil.executeQuery(String.format(Oracle.TABLE_DDL, tableName));
            while (resultSet.next()) {
                return resultSet.getString(1);
            }
            return null;
        } finally {
            connectionUtil.release();
        }
    }

    @Override
    public List<Column> columns(String tableName) {
        try {
            ResultSet columnSet = connectionUtil.executeQuery(String.format(Oracle.TABLE_COLUMNS, tableName));
            List<Column> columns = new ArrayList<>();
            while (columnSet.next()) {
                Column column = new Column();
                column.setName(columnSet.getString(1).toUpperCase())
                        .setAlias(StringUtil.upperField(columnSet.getString(1)))
                        .setDataType(getConversion().get(columnSet.getString(2).toUpperCase()))
                        .setMaxLength(columnSet.getLong(4))
                        .setComment(columnSet.getString(5))
                        .setDefaultValue(columnSet.getString(6))
                        .setPrimaryKey(false)
                        .setUnique(false)
                        .setForeignKey(false);
                columns.add(column);
            }
            ResultSet constraint = connectionUtil.executeQuery(String.format(Oracle.TABLE_CONSTRAINT, tableName));
            while (constraint.next()) {
                String columnName = StringUtil.upperField(constraint.getString(1));
                String constraintType = constraint.getString(2);
                columns.forEach(column -> {
                    if (column.getName().equals(columnName)) {
                        column = "R".equals(constraintType) ? column.setForeignKey(true) :
                                "P".equals(constraintType) ? column.setPrimaryKey(true) :
                                        "U".equals(constraintType) ? column.setUnique(true) : column;
                    }
                });
            }
            return columns;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getCause());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getCause());
        } finally {
            connectionUtil.release();
        }
    }
}
