package base;

import constant.Configuration;
import constant.SystemConstant;
import lombok.Getter;
import util.PropertiesUtil;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * @author Ming
 * @version 1.0
 * @date 2019/3/12 10:06
 * @describe generate properties
 */
@Getter
public class GenerateProperties {
    /**
     * primary key generation strategy
     */
    private String primaryKeyType;
    /**
     * entry name
     */
    private String projectName;
    /**
     * base package path
     */
    private String basePackage;
    /**
     * front-end generated address
     */
    private String frontGeneratePackage;

    /**
     * init
     */
    public GenerateProperties() {
        URL localPropertiesUrl =
                this.getClass().getClassLoader().getResource(SystemConstant.GENERATE_PROPERTIES);
        URL projectPropertiesUrl =
                this.getClass().getClassLoader().getResource(SystemConstant.PROJECT_CONFIGURATION_DIRECTORY +
                        File.separator + SystemConstant.GENERATE_PROPERTIES);
        try {
            /**
             * local properties
             */
            PropertiesUtil local = new PropertiesUtil(localPropertiesUrl.openStream());
            String primaryKeyType = local.getValue(Configuration.PRIMARY_KEY_TYPE);
            String projectName = local.getValue(Configuration.PROJECT_NAME);
            String basePackage = local.getValue(Configuration.BASE_PACKAGE);
            String frontGeneratePackage = local.getValue(Configuration.FRONT_GENERATE_PACKAGE);

            if (null == projectPropertiesUrl) {
                this.primaryKeyType = primaryKeyType;
                this.projectName = projectName;
                this.basePackage = basePackage;
                this.frontGeneratePackage = frontGeneratePackage;

                return;
            } else {
                PropertiesUtil project = new PropertiesUtil(projectPropertiesUrl.openStream());
                String primaryKeyTypeProject = project.getValue(Configuration.PRIMARY_KEY_TYPE);
                String projectNameProject = project.getValue(Configuration.PROJECT_NAME);
                String basePackageProject = project.getValue(Configuration.BASE_PACKAGE);
                String frontGeneratePackageProject = project.getValue(Configuration.FRONT_GENERATE_PACKAGE);

                this.primaryKeyType = "".equals(primaryKeyTypeProject)?
                        primaryKeyType:primaryKeyTypeProject;

                this.projectName = "".equals(projectNameProject)?
                        projectName:projectNameProject;

                this.basePackage = "".equals(basePackageProject)?
                        basePackage:basePackageProject;

                this.frontGeneratePackage = "".equals(frontGeneratePackageProject)?
                        frontGeneratePackage:frontGeneratePackageProject;

                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
