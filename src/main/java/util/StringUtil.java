package util;

/**
 * @author Ming
 * @version 1.0
 * @date 2019/3/15 11:22
 * @describe TODO
 */
public class StringUtil {

    /**
     * to class name
     * @param str table name
     * @return
     */
    public static String upperTable(String str) {
        StringBuffer sbf = new StringBuffer();
        if (str.contains("_")) {
            String[] split = str.split("_");
            for (int i = 0, index = split.length; i < index; i++) {
                String upperTable = upperTable(split[i]);
                sbf.append(upperTable);
            }
        } else {
            char[] ch = str.toCharArray();
            if (ch[0] >= 'a' && ch[0] <= 'z') {
                ch[0] = (char) (ch[0] - 32);
            }
            sbf.append(ch);
        }
        return sbf.toString();
    }

    /**
     * filed
     * @param str
     * @return
     */
    public static String upperField(String str) {
        StringBuffer sbf = new StringBuffer();
        str = upperTable(str);
        return sbf.append(Character.toLowerCase(str.charAt(0)))
                .append(str.substring(1))
                .toString();
    }

}
