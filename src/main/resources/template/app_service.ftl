package ${belongPackage};

import java.util.List;
import xxl.mvc.pub.pager.Pager;
import ${basePackage}.model.${alias};
import xxl.mvc.pub.query.QueryFilterFactory;
import xxl.mvc.pub.query.QueryOrderFactory;
import xxl.mvc.service.service.BaseServiceI;

public interface ${className} extends BaseServiceI {

	public ${alias} findOneById(long id) throws Exception;

    public List<${alias}> findListByIds(String ids) throws Exception;

    public Pager<${alias}> findPagerList(QueryFilterFactory qff,QueryOrderFactory qof,Pager pager) throws Exception;

    public ${alias} add(${alias} t) throws Exception;

    public ${alias} mod(${alias} t) throws Exception;

    public int deletedBusiness(long  id) throws Exception;

    public int delete(long  id) throws Exception;
 
 }