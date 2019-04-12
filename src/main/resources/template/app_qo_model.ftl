package ${belongPackage};

import xxl.mvc.pub.query.model.QueryModel;
import ${basePackage}.model.${alias};

import lombok.*;
import lombok.experimental.*;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper=true)
public class  ${className} extends QueryModel {

    public Class getModelClass(){
        return ${alias}.class;
    }
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
     * 原类型 ：${column.dataType}
     */
    private String ${column.alias};
   	</#if>
    </#list>
    
}