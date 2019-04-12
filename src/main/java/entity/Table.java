package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

/**
 * @author Ming
 * @version 1.0
 * @date 2019/3/11 17:15
 * @describe database table
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
public class Table implements Serializable {

    private String tableName;

    private String tableComment;

    private String createTime;

    private String updateTime;

    @Override
    public String toString() {
        try {
            final StringBuffer sb = new StringBuffer("{");
            if(null!=tableName)
                sb.append("\"tableName\":\"").append(tableName).append('\"');
            if(null!=tableComment)
                sb.append(", \"tableComment\":\"").append(new String(tableComment.toString().getBytes(),"UTF-8")).append('\"');
            if(null!=createTime)
                sb.append(", \"createTime\":\"").append(createTime).append('\"');
            if(null!=updateTime)
                sb.append(", \"updateTime\":\"").append(updateTime).append('\"');
            sb.append('}');
            return new String(sb.toString().getBytes(),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
