<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${belongPackage}.${daoName}" >

    <resultMap id="${alias}Map" type="${aliasLow}">
        <#list columns as column>
        <result property="${column.alias}" column="${column.name?upper_case}"/>
        </#list>
    </resultMap>

</mapper>