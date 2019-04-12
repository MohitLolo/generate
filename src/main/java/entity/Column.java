package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author Ming
 * @version 1.0
 * @date 2019/3/13 15:45
 * @describe table column
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Column implements Serializable {

    private String name;

    private String alias;

    private String defaultValue;

    private boolean nullable;

    private String dataType;

    private Long maxLength;

    private boolean primaryKey;

    private boolean foreignKey;

    private boolean unique;

    private String comment;

}