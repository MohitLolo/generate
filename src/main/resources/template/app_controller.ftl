package ${belongPackage};

import java.util.List;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ${basePackage}.model.${alias};
import ${basePackage}.modelo.qo.${alias}QueryFilterFactory;
import ${basePackage}.modelo.qo.${alias}QueryModel;
import ${basePackage}.service.${alias}ServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import xxl.mvc.pub.exception.ExceptionBeanFactory;
import xxl.mvc.controller.web.BaseController;
import xxl.mvc.controller.anno.PagerAnno;
import xxl.mvc.pub.pager.Pager;
import xxl.mvc.pub.query.QueryFilterFactory;
import xxl.mvc.pub.query.QueryOrderFactory;
import xxl.mvc.pub.util.string.DHStringUtil;
import xxl.mvc.pub.util.web.AjaxMethods;
import xxl.mvc.validate.aop.XValidate;
import xxl.mvc.validate.anno.ArgValidate;
import xxl.mvc.controller.easyui.EasyuiResponseUtil;
import xxl.mvc.controller.easyui.LayuiResponseUtil;
import xxl.mvc.validate.rules.IdRule;
<#--columns lowercase -->
<#assign columnNames=""/>
<#list columns as column>
    <#if column_index ==0>
        <#assign columnNames=column.alias/>
    <#else>
        <#assign columnNames = columnNames+","+column.alias/>
    </#if>
</#list>
/**
 * ${tableComment}
 * @date ${.now}
 */
@Controller
@RequestMapping("app/${table}/")
public class ${className} extends BaseController {

	@Autowired
    private ${alias}ServiceI ${aliasLow}Service;

    static final String  VALIDATEFIELD = "${columnNames}";


    /**
    * 去首页，也就是集合jsp界面
    */
    @RequestMapping(value="index",method=RequestMethod.GET)
    public String index(HttpServletRequest req,HttpServletResponse res) throws Exception{
        return this.forward(req, "${aliasLow}/index");
    }


    /**
    * 去修改界面
    */
    @RequestMapping(value="toMod",method=RequestMethod.GET)
    public String toMod(HttpServletRequest req,HttpServletResponse res,@ArgValidate(value={IdRule.class},name="id") long id) throws Exception{
        ${alias} bean = ${aliasLow}Service.findOneById(id);
        req.setAttribute("bean", bean);
        return this.forward(req, "${aliasLow}/edit");
    }


    /**
    * 去添加界面
    */
    @RequestMapping(value="toAdd",method=RequestMethod.GET)
    public String toAdd(HttpServletRequest req,HttpServletResponse res) throws Exception{
        return this.forward(req, "${aliasLow}/add");
    }


    /**
    * 逻辑删除
    */
    @RequestMapping(value="modDeleted",method=RequestMethod.POST)
    public void modDeleted(HttpServletRequest req,HttpServletResponse res,@ArgValidate(value={IdRule.class},name="id") long id) throws Exception{
        ${aliasLow}Service.deletedBusiness(id);
        AjaxMethods.sendMsgJSON(req, res, "ok");
    }


    /**
    * 逻辑删除
    */
    @RequestMapping(value="modDeleteds",method=RequestMethod.POST)
    public void modDeleteds(HttpServletRequest req,HttpServletResponse res,String ids) throws Exception{
        if(null!=ids && !ids.equals("")){
            ids = ids.trim();
            if(ids.endsWith(",")){
                ids = ids.substring(0,ids.length()-1);
            }
            String[] args = ids.split(",");
            for(String id : args){
                if(null!=id && !id.equals("")){
                    ${aliasLow}Service.deletedBusiness(new Long(id));
                }
            }
        }
        AjaxMethods.sendMsgJSON(req, res, "ok");
    }


    /**
    * 物理删除
    */
    @RequestMapping(value="delete",method=RequestMethod.POST)
    public void delete(HttpServletRequest req,HttpServletResponse res,@ArgValidate(value={IdRule.class},name="id") long id) throws Exception{
        ${aliasLow}Service.delete(id);
        AjaxMethods.sendMsgJSON(req, res, "ok");
    }


    /**
    * 添加
    */
    @RequestMapping(value="add",method=RequestMethod.POST)
    public void add(HttpServletRequest req,HttpServletResponse res,@ArgValidate(model=${alias}.class,modelFields=VALIDATEFIELD) ${alias} bean ) throws Exception{
        ${aliasLow}Service.add(bean);
        AjaxMethods.sendMsgJSON(req, res, "ok");
    }


    /**
    * 修改
    */
    @RequestMapping(value="mod",method=RequestMethod.POST)
    public void mod(HttpServletRequest req,HttpServletResponse res,@ArgValidate(model=${alias}.class,modelFields=VALIDATEFIELD) ${alias} bean ) throws Exception{
        long id = bean.getId();
        ${alias} beanold = ${aliasLow}Service.findOneById(id);
        if(null==beanold){
            throw ExceptionBeanFactory.exception("抱歉，该对象不存在");
        }
        /**
        * setXXX.......
        * beanold.setName(bean.getName());
        */
        ${aliasLow}Service.mod(beanold);
        AjaxMethods.sendMsgJSON(req, res, "ok");
    }


    /**
    * easyui datagrid检索
    */
    @RequestMapping(value="list",method=RequestMethod.GET)
    public void list(HttpServletRequest req,HttpServletResponse res,${alias}QueryModel qm,@PagerAnno Pager<${alias}> pager) throws Exception{
        //qm.putOper("name","like").putOrder("createDate","btad");
        QueryFilterFactory qff = new QueryFilterFactory(qm);
        QueryOrderFactory qof = new QueryOrderFactory();
        qof = qof.add(qm.getOrderBy(), qm.getOrderDes());
        pager = ${aliasLow}Service.findPagerList(qff, qof, pager);
        LayuiResponseUtil.datagrid(req, res, pager, null);
    }

}