package data;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ming
 * @version 1.0
 * @date 2019/3/15 9:09
 * @describe
 */
public enum TemplateEnum {

    CONTROLLER(1001,"app_controller.ftl","controller",0,"Controller"),
    DAO(1002,"app_dao.ftl","dao",0,"DaoI"),
    DAO_XML(1003,"app_dao_xml.ftl","dao",3,"DaoI"),
    ENUM(1004,"app_enum.ftl","modelo"+ File.separator+"em",0,"Enum"),
    MODEL(1005,"app_model.ftl","model",0,""),
    MODEL_MAPPING(1006,"app_model_mapping.ftl","model",3,".model.mapping"),
    QO_FILTER(1007,"app_qo_filter.ftl","modelo"+ File.separator+"qo",0,"QueryFilterFactory"),
    QO_MODEL(1008,"app_qo_model.ftl","modelo"+ File.separator+"qo",0,"QueryModel"),
    SERVICE(1009,"app_service.ftl","service",0,"ServiceI"),
    SERVICE_IMPL(1010,"app_service_impl.ftl","service",0,"Service"),
    ADD(1011,"front_add.ftl","",1,""),
    EDIT(1012,"front_edit.ftl","",1,""),
    INDEX(1013,"front_index.ftl","",1,""),
    JS(1014,"front_js.ftl","",2,"")
    ;
    public int sequenceNumber; //模板序号
    public String template; //模板名称
    public String belong; //代码生成所属包
    public int fileType; //生成文件的类型0:java ; 1:jsp ; 2:js ; 3:xml
    public String suffix; //生成文件的后缀

    TemplateEnum(int sequenceNumber, String template, String belong,int fileType,String suffix) {
        this.sequenceNumber = sequenceNumber;
        this.template = template;
        this.belong = belong;
        this.fileType = fileType;
        this.suffix = suffix;
    }

    /**
     * 获取指定模板的模板序号
     * @param template 模板文件名称
     * @return
     */
    public static int getSequenceNumber(String template) {
        return Arrays.stream(TemplateEnum.values())
                .filter(e->e.template.equals(template))
                .map(c->c.sequenceNumber)
                .findAny()
                .orElse(0);
    }

    /**
     * 获取指定的模板名称
     * @param sequenceNumber 模板序号
     * @return
     */
    public static String getTemplate(Integer sequenceNumber) {
        return Arrays.stream(TemplateEnum.values())
                .filter(e->e.sequenceNumber==sequenceNumber)
                .map(c->c.template)
                .findAny()
                .orElse(null);
    }

    /**
     * 根据模板序号获取指定模板枚举
     * @param sequenceNumber 模板序号
     * @return
     */
    public static TemplateEnum getEnum(Integer sequenceNumber) {
        return Arrays.stream(TemplateEnum.values())
                .filter(e->e.sequenceNumber==sequenceNumber)
                .findAny()
                .orElse(null);
    }

    /**
     * 获取所有的模板序号
     * @return
     */
    public static List<Integer> allSequenceNumber() {
        return Arrays.stream(TemplateEnum.values())
                .map(e->e.sequenceNumber)
                .collect(Collectors.toList());
    }

    /**
     * 获取所有的后台模板序号
     * @return
     */
    public static List<Integer> allAppSequenceNumber() {
        return Arrays.stream(TemplateEnum.values())
                .filter(e->e.template.contains("app_"))
                .map(e->e.sequenceNumber)
                .collect(Collectors.toList());
    }

    /**
     * 获取所有前台的模板序号
     * @return
     */
    public static List<Integer> allFrontSequenceNumber() {
        return Arrays.stream(TemplateEnum.values())
                .filter(e->e.template.contains("front_"))
                .map(e->e.sequenceNumber)
                .collect(Collectors.toList());
    }
}
