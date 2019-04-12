package constant;

import base.GenerateProperties;

import java.io.File;
import java.net.URL;

/**
 * @author Ming
 * @version 1.0
 * @date 2019/3/12 9:03
 * @describe
 */
public class SystemConstant {

    public static final String DATABASE_TYPE_MYSQL = "MYSQL";
    public static final String DATABASE_TYPE_ORACLE = "ORACLE";
    /**
     * basic path to code generation
     */
    public static final String PACKAGE_PATH = System.getProperty("user.dir") + File.separator + "src" +File.separator+ "main" + File.separator + "java" + File.separator;
    /**
     * basic path to resources
     */
    public static final String RESOURCES_PATH = System.getProperty("user.dir") + File.separator + "src" +File.separator+ "main" + File.separator + "resources" + File.separator;
    /**
     * basic path to generate template in project
     */
    public static final String PROJECT_TEMPLATE_DIRECTORY = System.getProperty("user.dir") + File.separator + "src" +File.separator+ "main" + File.separator + "resources" + File.separator + "generate" + File.separator +"template" + File.separator;
    /**
     * project configuration directory
     */
    public static final String PROJECT_CONFIGURATION_DIRECTORY = "generate";
    /**
     * configure file name
     */
    public static final String GENERATE_PROPERTIES = "generate.properties";
    /**
     * translate properties
     */
    public static final String CONVERSION_PROPERTIES = "conversion.properties";
    /**
     * template
     */
    public static final String TEMPLATE = "template";
    /**
     * basic directory of project template
     */
    public static final URL TEMPLATE_DIRECTORY = SystemConstant.class.getClassLoader().getResource(PROJECT_CONFIGURATION_DIRECTORY + File.separator + TEMPLATE+File.separator);
    /**
     * translate properties URL
     */
    public static final URL PROJECT_TYPE_FILE = SystemConstant.class.getClassLoader().getResource(PROJECT_CONFIGURATION_DIRECTORY+ File.separator+CONVERSION_PROPERTIES);
}
