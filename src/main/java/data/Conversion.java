package data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ming
 * @version 1.0
 * @date 2019/3/15 10:12
 * @describe
 */
public class Conversion extends HashMap<String,String> {

    /**
     * K db type , V java type
     */
    {
        super.put("VARCHAR","java.lang.String");
        super.put("CHAR","java.lang.String");
        super.put("BLOB","java.lang.byte[]");
        super.put("TEXT","java.lang.String");
        super.put("INTEGER","java.lang.Long");
        super.put("TINYINT","java.lang.Integer");
        super.put("SMALLINT","java.lang.Integer");
        super.put("MEDIUMINT","java.lang.Integer");
        super.put("BIT","java.lang.Boolean");
        super.put("BIGINT","java.math.BigInteger");
        super.put("FLOAT","java.lang.Float");
        super.put("DOUBLE","java.lang.Double");
        super.put("DECIMAL","java.math.BigDecimal");
        super.put("DATE","java.util.Date");
        super.put("TIME","java.util.Date");
        super.put("DATETIME","java.util.Date");
        super.put("TIMESTAMP","java.util.Date");
        super.put("YEAR","java.util.Date");
        super.put("INT","java.lang.Integer");

        super.put("CHAR","java.lang.String");
        super.put("VARCHAR2","java.lang.String");
        super.put("LONG","java.lang.String");
        super.put("NUMBER","java.lang.Double");
        super.put("RAW","java.lang.byte[]");
        super.put("LONGRAW","java.lang.byte[]");
    }

    public Conversion cover(Map<String, String> attr) {
        this.putAll(attr);
        return this;
    }

    public Conversion() {
        super();
    }

    public Conversion(Map<String,String> attr) {
        this.cover(attr);
    }

}
