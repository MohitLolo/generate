package util;

import javax.sql.DataSource;
import java.sql.*;

/**
 * @author Ming
 * @version 1.0
 * @date 2019/3/14 8:49
 * @describe
 */
public class ConnectionUtil {

    private DataSource dataSource;

    private Connection connection;

    private PreparedStatement preparedStatement;

    private Statement statement;

    private ResultSet resultSet;

    private String driver;

    private String url;

    private String username;

    private String password;


    private Connection getConnection(String driver, String url, String username, String password) {
        try {
            if (null != this.connection && !this.connection.isClosed()) {
                return this.connection;
            }
            Class.forName(driver);
            return this.connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ConnectionUtil(DataSource dataSource) {
        try {
            this.dataSource = dataSource;
            this.connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ConnectionUtil(String driver, String url, String username, String password) {
        this.driver = driver;
        this.url = url;
        this.username = username;
        this.password = password;
        this.connection = getConnection(driver,url, username, password);
    }

    public Connection getConnection() {
        try {
            if (null != this.connection && !this.connection.isClosed()) {
                return this.connection;
            }else if (null != dataSource){
                return this.connection = dataSource.getConnection();
            }else {
                return this.connection = this.getConnection(driver,url,username,password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private PreparedStatement getPreparedStatement(String sql) throws SQLException {
        return this.preparedStatement = this.getConnection().prepareStatement(sql);
    }

    private Statement getStatement() throws SQLException {
        return statement = this.getConnection().createStatement();
    }

    public ResultSet executeQuery(String sql) throws SQLException {
        return this.resultSet = this.getPreparedStatement(sql).executeQuery();
    }

    public String dataBaseType() throws SQLException {
        try {
            return this.getConnection().getMetaData().getDriverName().toUpperCase();
        }finally {
            release();
        }
    }

    public void release() {
        try {
            if (null != this.resultSet) {
                this.resultSet.close();
            }
            if (null != this.preparedStatement) {
                this.preparedStatement.close();
            }
            if (null != this.statement) {
                this.statement.close();
            }
            if (null != this.connection) {
                this.connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
