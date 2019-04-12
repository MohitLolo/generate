package web;

import lombok.Data;

import java.util.List;

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

}
