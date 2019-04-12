package constant.sql;

/**
 * @author Ming
 * @version 1.0
 * @date 2019/3/11 17:05
 * @describe Oracle SQL
 */
public class Oracle {
    /**
     * query all table information for user
     */
    public static final String SHOW_TABLES =
            "SELECT a.TABLE_NAME ,b.COMMENTS FROM user_tables a,user_tab_comments b " +
            "WHERE  a.TABLE_NAME=b.TABLE_NAME " +
            "ORDER BY TABLE_NAME";
    /**
     * query the information of the appointed table
     */
    public static final String SINGLE_TABLE = "SELECT a.TABLE_NAME ,b.COMMENTS FROM user_tables a,user_tab_comments b " +
            "WHERE  a.TABLE_NAME=b.TABLE_NAME AND a.TABLE_NAME = '%s' ORDER BY TABLE_NAME ";
    /**
     * DDL
     */
    public static final  String TABLE_DDL = "SELECT DBMS_METADATA.GET_DDL ( 'TABLE', '%s' ) AS DDL FROM DUAL";
    /**
     * columns data information of query table
     */
    public static final String TABLE_COLUMNS = "SELECT" +
            " utc.COLUMN_NAME," +
            " utc.DATA_TYPE," +
            " utc.NULLABLE," +
            " utc.DATA_LENGTH," +
            " ucm.COMMENTS, " +
            " utc.DATA_DEFAULT   " +
            "FROM" +
            " user_tab_columns utc" +
            " LEFT JOIN user_col_comments ucm ON ucm.table_name = utc.table_name " +
            " AND ucm.COLUMN_NAME = utc.COLUMN_NAME " +
            "WHERE" +
            " utc.table_name = '%s' ";
    /**
     * query table constraint
     * CONSTRAINT_TYPE:
     * C (check constraint on a table) pass
     * P (primary key) use
     * U (unique key) use
     * R (referential integrity/foreign key) use
     * V (with check option, on a view) pass
     * O (with read only, on a view) pass
     */
    public static final String TABLE_CONSTRAINT = "SELECT " +
            " ucc.COLUMN_NAME, " +
            " uc.constraint_type  " +
            "FROM " +
            " USER_CONSTRAINTS uc, " +
            " user_cons_columns ucc  " +
            "WHERE " +
            " uc.CONSTRAINT_NAME = ucc.CONSTRAINT_NAME  " +
            " AND ( uc.constraint_type = 'P' OR uc.constraint_type = 'R' OR uc.constraint_type = 'U' )  " +
            " AND uc.table_name = '%s'";
}
