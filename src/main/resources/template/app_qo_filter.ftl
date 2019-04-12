package ${belongPackage};

import xxl.mvc.pub.query.QueryFilterFactory;

public class ${className} extends QueryFilterFactory {

	<#list columns as column>
    public ${className} MEQ_${column.alias}(String ${column.alias}) {
        this.addString_eq("${column.alias}",${column.alias});
        return this;
    }

    public ${className} MLike_${column.alias}(String ${column.alias}) {
        this.addAllLike("${column.alias}",${column.alias});
        return this;
    }

    public ${className} MIn_${column.alias}(String ${column.alias}) {
        this.addIn_string("${column.alias}",${column.alias});
        return this;
    }
    </#list>
    
 }