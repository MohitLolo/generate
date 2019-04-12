<?xml version="1.0"  encoding="UTF-8"?>
<model-mapping>
    <class
            project="${project}"
            alias="${alias}"
            comment="${tableComment}"
            name="${basePackage}.model.${alias}"
            table="${table}"
            daoI="${basePackage}.dao.${alias}DaoI"
            serviceI="${basePackage}.service.${alias}ServiceI"
            service="${basePackage}.service.${alias}Service"
            controller="${basePackage}.controller.${alias}Controller">


        <#list columns as column>

            <#if column.primaryKey==true>
                <pk name="${column.alias}" column="${column.name?upper_case}" type="${column.dataType!""}" pkType="${primaryKeyType}" pkKey="${table}"/>
            <#else >
                <#list "${column.comment}"?split("$enum") as commentstr>
                    <#if commentstr_index == 0>
                        <#assign  comment = commentstr>
                    </#if>
                    <#if commentstr_index == 1>
                        <#assign enum = commentstr>
                    </#if>
                </#list>
                <property name="${column.alias}" column="${column.name?upper_case}" type="${column.dataType!""}" defaultVal="${column.defaultValue!""}" comment="${comment!""}">
                    <validates>
                        <#if column.nullable==true><validate rule="NotEmptyRule" comment="" args=""/></#if>
                        <#if column.maxLength?? && column.maxLength gt 0><validate rule="StringLenRule" comment="" args="maxLen=${column.maxLength},minLen=0"/></#if>
                        <#if enum?? && enum!= ""><validate rule="MustEnumRule" comment="" args=""/></#if>
                    </validates>
                    <#if enum?? && enum!= "">
                    <enums>
                        <#list enum?split("/") as em>
                            <#list em?split(":") as vc>
                                <#if vc_index==0><#assign v=vc></#if>
                                <#if vc_index==1><#assign c=vc></#if>
                            </#list>
                        <enum value="${v}" comment="${c}"></enum>
                        </#list>
                    </enums>
                    </#if>
                </property>
                <#--empty-->
                <#assign  comment = "">
                <#assign enum = "">
                <#assign v="">
                <#assign c="">
            </#if>

        </#list>


    </class>
</model-mapping>