package constant.sql;

/**
 * @author Ming
 * @version 1.0
 * @date 2019/3/11 17:05
 * @describe mysql SQL
 */
public class MySQL {
    /**
     * query the current connection to all databases
     */
    public static final String SHOW_DATABASES = "SHOW DATABASES";
    /**
     * query all table information for the appointed database
     */
    public static final String SHOW_TABLES = "SELECT " +
            "table_name AS tableName," +
            "table_comment AS tableComment," +
            "create_time AS createTime," +
            "update_time AS updateTime " +
            "FROM " +
            "information_schema.TABLES " +
            "WHERE " +
            "table_schema = (select database())";
    /**
     * query the information of the appointed table
     */
    public static final String SINGLE_TABLE = "SELECT " +
            "table_name AS tableName," +
            "table_comment AS tableComment," +
            "create_time AS createTime," +
            "update_time AS updateTime " +
            "FROM " +
            "information_schema.TABLES " +
            "WHERE " +
            "table_schema = (select database()) AND table_name ='%s'";
    /**
     * DDL
     */
    public static final String TABLE_DDL = "SHOW CREATE TABLE %s";
    /**
     * columns data information of query table
     * COLUMN_KEY:
     *          PRI: primary key
     *          UNI: unique
     *          MUL: multiple
     */
    public static final String TABLE_COLUMNS = "SELECT " +
            "COLUMN_NAME," +  //name
            "COLUMN_DEFAULT," +  //default value
            "IS_NULLABLE," +  //can null
            "DATA_TYPE," + //type
            "CHARACTER_MAXIMUM_LENGTH," + //max length
            "COLUMN_KEY," +
            "COLUMN_COMMENT " + //comment
            "FROM " +
            "information_schema.COLUMNS " +
            "WHERE " +
            "table_name = '%s' " +
            "ORDER BY " +
            "ordinal_position";
    /**
     * query foreign key
     */
    public static final String TABLE_FK = "SELECT" +
            " COLUMN_NAME " +
            "FROM" +
            " INFORMATION_SCHEMA.KEY_COLUMN_USAGE " +
            " WHERE " +
            " TABLE_NAME = '%s' " +
            " AND CONSTRAINT_NAME != 'PRIMARY'";
}
