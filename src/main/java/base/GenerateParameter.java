package base;

import lombok.Data;

import java.util.List;

/**
 * @author Ming
 * @version 1.0
 * @date 2019/3/15 11:06
 * @describe
 */
@Data
public class GenerateParameter {
    /**
     * 主键生成策略
     */
    private String primaryKeyType;
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 文件所属的包 例: com.cn.jinhui
     */
    private String basePackage;
    /**
     * 前台页面生成产出路径
     */
    private String frontGeneratePackage;
    /**
     * 是否是树形结构(默认非树结构,该属性暂时无效)
     */
    private boolean isTree = false;
    /**
     * 需生成文件的模板号集合
     */
    private List<Integer> templates;
    /**
     * 是否覆盖本地文件生成
     */
    private boolean coverage = false;
    //TODO other args
}
