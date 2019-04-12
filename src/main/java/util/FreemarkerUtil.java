package util;

import constant.SystemConstant;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.*;
import java.net.URL;
import java.util.Map;


/**
 * @author Ming
 * @version 1.0
 * @date 2019/3/12 15:57
 * @describe freemarkerUtil
 */
public class FreemarkerUtil {

    /**
     * directory of templates in jar
     */
    private static final String TEMPLATE = "/template/";

    /**
     * enerate the specified file to the specified directory based on the template
     * @param dirUrl Template directory path URL
     * @param templateName Template name
     * @param producePath Generate Address (Folder)
     * @param fileName Generate file name
     * @param data Template parsing data
     * @param coverage coverage existing
     */
    public static void freemarkerGenerate(URL dirUrl,
                                          String templateName,
                                          String producePath,
                                          String fileName,
                                          Map<String,Object> data,
                                          boolean coverage) {
        Configuration configuration = new Configuration();
        Writer out = null;
        try {
            /**
             * using templates in jar if null of template
             */
            String ftlPathProject = SystemConstant.PROJECT_TEMPLATE_DIRECTORY+templateName;
            File projectTemplate = new File(ftlPathProject);
            if (!projectTemplate.exists()) {
                configuration.setClassForTemplateLoading(FreemarkerUtil.class, FreemarkerUtil.TEMPLATE);
            } else {
                configuration.setDirectoryForTemplateLoading(new File(dirUrl.toURI()));
            }
            Template template = configuration.getTemplate(templateName,"UTF-8");
            File docFile = new File(producePath);
            if (!docFile.exists()) { // folder
                docFile.mkdirs();
            }
            File generateFile = new File(docFile+File.separator+fileName);
            if (generateFile.exists() && !coverage) {
                System.out.println("file already exists "+ fileName);
                return;
            }
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(docFile + File.separator + fileName),"UTF-8"));
            template.process(data, out);
            System.out.println("complete generate " + fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (null != out) {
                    out.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
