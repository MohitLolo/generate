package ${belongPackage};

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xxl.mvc.pub.exception.ExceptionBeanFactory;
import xxl.mvc.pub.pager.Pager;
import xxl.mvc.pub.query.QueryFilterFactory;
import xxl.mvc.pub.query.QueryOrderFactory;
import xxl.mvc.pub.util.string.DHStringUtil;
import xxl.mvc.service.service.BaseService;
import xxl.mvc.validate.aop.XValidate;
import ${basePackage}.service.${alias}ServiceI;
import ${basePackage}.model.${alias};
import ${basePackage}.dao.${alias}DaoI;
/**
* 
* 
*/
@Service("${alias}Service")
public class ${className} extends BaseService<${alias}> implements ${alias}ServiceI {

    @Autowired
    private ${alias}DaoI ${aliasLow}DaoI;

    @Override
    public ${alias} findOneById(long id) throws Exception {
        XValidate.valId("id", id);
        ${alias} ${aliasLow} = ${aliasLow}DaoI.queryOneById(id,null);
        return ${aliasLow};
    }

    @Override
    public List<${alias}> findListByIds(String ids) throws Exception {
        List<${alias}> list = null;
        ids = DHStringUtil.dealIds(ids);
        if(null!=ids && !ids.equals("")){
            list = ${aliasLow}DaoI.queryList_byIds(ids,null);
        }
        return list;
    }

    @Override
    public Pager<${alias}> findPagerList(QueryFilterFactory qff,QueryOrderFactory qof, Pager pager) throws Exception {
        return ${aliasLow}DaoI.queryPagerList(qff, qof, pager,null);
    }

    @Override
    public ${alias} add( ${alias} t ) throws Exception{
        /**
        * 第一：数据校验
        * 	1、temp无法帮您生成，您需要按照自己的逻辑进行校验
        */
        /**
        * 第二：逻辑校验
        */
        

        /**
        * 第三：数据操作
        */
        ${aliasLow}DaoI.insert(t);
        /**
        * 第四：级联操作
        */
        return t;
    }

    @Override
    public ${alias} mod( ${alias} t ) throws Exception{
        /**
        * 第一：数据校验
        * 	1、temp无法帮您生成，您需要按照自己的逻辑进行校验
        */
        XValidate.valId("id", t.getId());
        /**
        * 第二：逻辑校验
            1、比如什么状态下才可以修改
        */
        

        /**
        * 第三：数据操作
        */
        ${aliasLow}DaoI.update(t);
        /**
        * 第四：级联操作
        */
        return t;
    }

    @Override
    public int deletedBusiness( long id ) throws Exception{
        /**
        * 第一：数据校验
        * 	1、id校验
        * 	2、是否存在
        */
        XValidate.valId("id",id);
        ${alias} ${aliasLow} = ${aliasLow}DaoI.queryOneById(id,null);
        if( null == ${aliasLow} ){
            throw ExceptionBeanFactory.exception("抱歉，删除对象时发现该id没有对象");
        }
        /**
        * 第二：逻辑校验
            1、比如什么时候不允许删除
        */
        

        /**
        * 第三：数据操作
        */
        int res = ${aliasLow}DaoI.deleted_1(id);
        /**
        * 第四：级联操作
        */
        return res;
    }

    @Override
    public int delete( long id ) throws Exception{
        /**
        * 第一：数据校验
        * 	1、id校验
        * 	2、是否存在
        */
        XValidate.valId("id",id);
        ${alias} ${aliasLow} = ${aliasLow}DaoI.queryOneById(id,null);
        if( null == ${aliasLow} ){
            throw ExceptionBeanFactory.exception("抱歉，删除对象时发现该id没有对象");
        }
        /**
        * 第二：逻辑校验
            1、比如什么时候不允许删除
        */
        

        /**
        * 第三：数据操作
        */
        int res = ${aliasLow}DaoI.delete(id);
        /**
        * 第四：级联操作
        */
        return res;
    }
}
