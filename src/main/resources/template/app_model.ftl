package ${belongPackage};

import lombok.*;
import lombok.experimental.*;
import xxl.mvc.pub.model.BaseModel;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=true)
@Alias("${aliasLow}")
public class ${className}  extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;
<#list columns as column>
	<#if column.alias!="id" && column.alias!="createDate" 
    && column.alias!="updateDate" && column.alias!="updateUserId" 
    && column.alias!="createUserId" && column.alias!="remarks"
    && column.alias!="idstr" && column.alias!="address1Id" 
    && column.alias!="address2Id" && column.alias!="address3Id"
    && column.alias!="address4Id" && column.alias!="addressName"
    && column.alias!="version" && column.alias!="tcckey"
    && column.alias!="organizationRootId" && column.alias!="organizationCompanyId"
    && column.alias!="organizationTreeId" >
    /**
     * ${column.comment}
     */
    private ${column.dataType!""} ${column.alias};
    </#if>
</#list>

}