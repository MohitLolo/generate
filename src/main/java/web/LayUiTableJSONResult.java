package web;

import lombok.Data;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ming
 * @version 1.0
 * @date 2019/4/9 14:12
 * @describe TODO
 */
@Data
public class LayUiTableJSONResult<T> {

    private int code;

    private String msg;

    private Integer count;

    private List<T> data;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("{");
        sb.append("\"code\":").append(code);
        if(null!=msg)
            sb.append(", \"msg\":\"").append(msg).append('\"');
        sb.append(", \"count\":").append(count);
        sb.append(", \"data\": [").append(data.stream().map(T::toString).collect(Collectors.joining(","))).append("]");
        sb.append('}');
        try {
            return new String(sb.toString().getBytes(),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
