package ${belongPackage};



public interface ${className} {
<#list columns as column>
    <#list "${column.comment}"?split("$enum") as commentstr>
        <#if commentstr_index == 0>
            <#assign  comment = commentstr>
        </#if>
        <#if commentstr_index == 1>
            <#assign enum = commentstr>
        </#if>
    </#list>
    <#if enum?? && enum!= "">
    enum ${column.alias?upper_case} {
        <#list enum?split("/") as em>
            <#list em?split(":") as vc>
                <#if vc_index==0><#assign v=vc></#if>
                <#if vc_index==1><#assign c=vc?upper_case></#if>
            </#list>
            ${c}("${c}","${v}"),
        </#list>
        ;
        private ${column.alias?upper_case}(String value,String desc) {
        this.value = value;
        this.desc = desc;
        }
        public final String value;
        public final String desc;
        public static String getDesc(String s){
            if(null==s || s.equals("") ){
             return "";
            }
            for(${column.alias?upper_case} typeEnum : ${column.alias?upper_case}.values()){
                if(typeEnum.name().equals(s)){
                    return typeEnum.desc;
                }
            }
            return "";
        }
        public static boolean contains(String s) {
            if(null==s || s.equals("")) {
                return false;
            }
            for(${column.alias?upper_case} typeEnum : ${column.alias?upper_case}.values()){
                if(typeEnum.name().equals(s)) {
                    return true;
                }
            }
            return false;
        }
    }
    </#if>
    <#--empty-->
    <#assign  comment = "">
    <#assign enum = "">
    <#assign v="">
    <#assign c="">
</#list>
}