package data;

import constant.sql.MySQL;
import entity.Column;
import entity.Table;
import util.ConnectionUtil;
import util.StringUtil;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ming
 * @version 1.0
 * @date 2019/3/14 9:42
 * @describe mysql;
 */
public class MySQLMetaData implements MetaData {

    ConnectionUtil connectionUtil;

    /*public MySQLMetaData(DataSource dataSource) {
        this.connectionUtil = new ConnectionUtil(dataSource);
    }

    public MySQLMetaData(String driver, String url, String username, String password) {
        this.connectionUtil = new ConnectionUtil(driver,url,username,password);
    }*/

    public MySQLMetaData(ConnectionUtil connectionUtil) {
        this.connectionUtil = connectionUtil;
    }

    public List<String> allDataBases() throws SQLException {
        try {
            ResultSet resultSet = connectionUtil.executeQuery(MySQL.SHOW_DATABASES);
            List<String> databases = new ArrayList<>();
            while (resultSet.next()) {
                String database = resultSet.getString(1);
                databases.add(database);
            }
            return databases;
        } finally {
            connectionUtil.release();
        }
    }


    @Override
    public List<Table> tables() throws SQLException {
        try {
            List<Table> tables = new ArrayList<>();
            ResultSet resultSet = connectionUtil.executeQuery(MySQL.SHOW_TABLES);
            while (resultSet.next()) {
                Table table = new Table(resultSet.getString(1),
                        resultSet.getString(2),resultSet.getString(3),resultSet.getString(4));
                tables.add(table);
            }
            return tables;
        }finally {
            connectionUtil.release();
        }
    }

    @Override
    public Table table(String tableName) throws SQLException {
        try {
            ResultSet resultSet = connectionUtil.executeQuery(String.format(MySQL.SINGLE_TABLE,tableName));
            while (resultSet.next()) {
                return new Table(resultSet.getString(1),
                        resultSet.getString(2),resultSet.getString(3),resultSet.getString(4));
            }
            return null;
        }finally {
            connectionUtil.release();
        }
    }

    @Override
    public String tableDdl(String tableName) throws SQLException {
        try {
            ResultSet resultSet = connectionUtil.executeQuery(String.format(MySQL.TABLE_DDL, tableName));
            while (resultSet.next()) {
                return resultSet.getString(2);
            }
            return null;
        }finally {
            connectionUtil.release();
        }
    }

    @Override
    public List<Column> columns(String tableName) {
        try {
            ResultSet columnSet = connectionUtil.executeQuery(String.format(MySQL.TABLE_COLUMNS, tableName));
            List<Column> columns = new ArrayList<>();
            while (columnSet.next()) {
                Column column = new Column();
                String columnKey = columnSet.getString(6);
                column.setName(columnSet.getString(1).toUpperCase())
                        .setAlias(StringUtil.upperField(columnSet.getString(1)))
                        .setDefaultValue(columnSet.getString(2))
                        .setNullable("YES".equals(columnSet.getString(3))?true:false)
                        .setDataType(getConversion().get(columnSet.getString(4).toUpperCase()))
                        .setMaxLength(columnSet.getLong(5))
                        .setPrimaryKey("PRI".equals(columnKey)?true:false)
                        .setUnique("UNI".equals(columnKey)?true:false)
                        .setComment(columnSet.getString(7))
                        .setForeignKey(false);
                columns.add(column);
            }
            ResultSet foreignKeyColumns = connectionUtil.executeQuery(String.format(MySQL.TABLE_FK, tableName));
            while (foreignKeyColumns.next()) {
                String foreignKey = StringUtil.upperField(foreignKeyColumns.getString(1));
                columns.forEach(column -> {
                    if (column.getName().equals(foreignKey))
                        column.setForeignKey(true);
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
