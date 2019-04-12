package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

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

}
