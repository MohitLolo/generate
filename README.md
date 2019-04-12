#### **代码生成器(coco-generate)使用文档**
##### 1.引入项目:

     于项目的pom文件中(maven项目)，加入依赖：
     
     <dependency>
     	<groupId>cn.coco</groupId>
     	<artifactId>generate</artifactId>
     	<version>1.0</version>
     </dependency>
     
     非maven项目将：generate-1.0.jar 配置入项目Build Path
##### 2.构建与配置:
######2.2 GenerationUtil的构建

GenerationUtil为代码生成器对外部暴露的主类，提供两个方法:

    代码生成方法:
    void generateCode(GenerateParameter parameter,String... tables)
    注:GenerateParameter为代码生成的构建参数(后面详细介绍参数属性的各项含义)，tables为需代码生成的表名
    数据源元数据获取方法:
    MetaData getMetaData();
    注:MetaData 类型包含以下方法
        1.获取数据源中所有的表信息
        2.依据表名获取指定的表信息
        3.依据表名获取该表的DDL
        4.依据表名获取该表的各列字段信息
 
目前支持两种构建方式:

     (1) 基于Spring IOC的构建(推荐使用);
     (2) 数据源参数构建

######2.2.1 基于Spring IOC的 构建方式:
    xml配置方式：
   
    例如:
            <bean id="ConnectionUtil" class="base.GenerationUtil">
       		<constructor-arg ref="dataSource"></constructor-arg>
       	</bean>   
    注：其中构造器注入的dataSource需为 java.sql.DataSource 的实现(各线程池均实现了该接口)，例如：
             <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource" destroy-method="close">
    		<property name="driverClass">
    			<!-- <value>oracle.jdbc.OracleDriver</value> -->
    			<value>${jdbc.driver}</value>
    		</property>
    		<property name="jdbcUrl">
    			<value>${jdbc.url}</value>
    		</property>
    		<property name="user">
    			<value>${jdbc.name}</value>
    		</property>
    		<property name="password">
    			<value>${jdbc.pwd}</value>
    		</property>
    		<property name="initialPoolSize" value="3"/>
    		<property name="maxPoolSize" value="10"/>
    		<property name="minPoolSize" value="2"/>
    		<property name="acquireIncrement" value="3"/>
    		<property name="maxIdleTime" value="600"/>
    		<property name="maxStatements" value="0"/>
    		<property name="maxStatementsPerConnection" value="80"/>
    		<property name="idleConnectionTestPeriod" value="60"></property>
    	</bean>
   注解声明式(下例为springboot中声明):
   
       @SpringBootApplication
       public class GenerateTestApplication {
        
            @Bean
            @ConfigurationProperties("spring.datasource.hikari")
            public DataSource getDataSource() {
                return DataSourceBuilder.create().build();
            }
        
            @Bean
            public GenerationUtil getGenerationUtil() {
                return new GenerationUtil(getDataSource());
            }
        
            public static void main(String[] args) {
                SpringApplication.run(GenerateTestApplication.class, args);
            }
        
        }
        
        application.yml中配置线程池
        spring:
          datasource:
            hikari:
              username: ***
              password: ***
              driver-class-name: com.mysql.jdbc.Driver
              jdbc-url: jdbc:mysql://*****:3306/****?characterEncoding=utf8
              minimum-idle: 5
              maximum-pool-size: 15
              auto-commit: true
              idle-timeout: 60000
              max-lifetime: 1800000
              connection-timeout: 60000
              connection-init-sql: SELECT 1
              validation-timeout: 3000
              login-timeout: 5
######2.2.2 数据源参数构建方式:
        GenerationUtil generationUtil = 
           new GenerationUtil("数据库驱动类","数据库URL","账号","密码");
######2.3 代码生成器的配置:
代码生成器的参数配置依照：

        GenerateParameter 参数配置优先于本地项目 src/main/resources/generate/generate.properties 配置文件中配置 优先于 jar包默认配置项的约定使用参数
    
######2.3.1 generate.properties文件中各项的含义:
    注:如需使用本地generate.properties文件,需将generate.properties文件，创建于 src/main/resources/template 文件夹下
    配置文件中各项含义如下:
        model.xml.pk.type=snowflakeIdWorker
        model.xml.project=jinhui
        base.package=com.cn.jinhui
        front.generate.package=C:/generate/JSP/
        
        front.generate.package : 前台页面生成产出路径
        base.package : 文件所属的包 例: com.cn.jinhui
        model.xml.project : 项目名称
        model.xml.pk.type : 主键生成策略
######2.3.2 GenerateParameter类型中各项属性的含义:
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
######2.3.3 conversion.properties文件的使用:
        代码生成器将会自动转换部分数据库类型为java类型，实际应用中，难免遇到代码生成器无法转换的数据库字段类型，或是默认转换类型并不符合生成需求，
      此时需使用本地conversion.properties文件进行转换类型设置
        于本地项目 src/main/resources/template 目录下创建conversion.properties文件
        写入 :
            bigint=java.lang.Long
        即可对bigint的数据库类型进行生成文件中转换问java的java.lang.Long类型
 
##### 3.使用:
        将构建完成的GenerationUtil注入(非IOC方式无需注入)
        @Resource
        GenerationUtil generationUtil;
        在具体方法中调用 generationUtil 的 generateCode 方法，传入GenerateParameter( @link 2.3.2 )参数与需要生成代码的表名参数即可。
        
        IOC下完整的配置: 
        @Configuration
        public class GenerateConfig {
        
            @Bean
            @ConfigurationProperties("spring.datasource.hikari")
            public DataSource getDataSource() {
                return DataSourceBuilder.create().build();
            }
        
            @Bean
            @Lazy
            public GenerationUtil getGenerationUtil() {
                return new GenerationUtil(getDataSource());
            }
        
            @Bean
            @Lazy
            public GenerateWebServletResources generateWeb() {
                GenerateServlet generateServlet = new GenerateServlet();
                generateServlet.setGenerationUtil(getGenerationUtil());
                return generateServlet;
            }
        
            @Bean
            @Lazy
            public ServletRegistrationBean servletRegistrationBean() {
                return new ServletRegistrationBean(generateWeb(), "/generate/*");
            }
        }
        项目启动后访问配置的相对web路径即可访问到代码生成页面,根据提示操作即可。
        
##### 4.模板序号与模板枚举类:

        模板的使用依据，本地项目 src/main/resources/generate/template 下模板优先于jar包中默认模板原则。
######4.1 模板及模板序号:     
            
            项目暂提供10个代码生成模板：
            app_controller.ftl -- 后台controller模板 序号 1001
            app_dao.ftl --后台dao模板 序号 1002
            app_dao_xml.ftl -- 后台daoXml模板 序号 1003
            app_enum.ftl --后台enum模板 序号 1004
            app_model.ftl --后台model模板 序号 1005
            app_model_mapping.ftl --后台model.mapping模板 序号 1006
            app_qo_filter.ftl --后台QueryFilterFactory模板 序号 1007
            app_qo_model.ftl --后台QueryModel模板 序号 1008
            app_service.ftl --后台service模板 序号 1009
            app_service_impl.ftl --后台serviceImpl模板 序号 1010
            front_add.ftl --前台add页面模板 序号 1011
            front_edit.ftl --前台edit页面模板 序号 1012
            front_index.ftl --前台index页面模板 序号 1013
            front_js.ftl --前台js模板 序号 1014
        
######4.2 模板枚举:          
            jar包中提供模板的枚举类型(TemplateEnum)，并提供了一些方法：
            
            /**
             * 获取指定模板的模板序号
             * @param template 模板文件名称
             * @return
             */
            public static int getSequenceNumber(String template)
            
            /**
             * 获取指定的模板名称
             * @param sequenceNumber 模板序号
             * @return
             */
            public static String getTemplate(Integer sequenceNumber)
            
            /**
             * 根据模板序号获取指定模板枚举
             * @param sequenceNumber 模板序号
             * @return
             */
            public static TemplateEnum getEnum(Integer sequenceNumber)
            
            /**
             * 获取所有的模板序号
             * @return
             */
            public static List<Integer> allSequenceNumber() 

            /**
             * 获取所有的后台模板序号
             * @return
             */
            public static List<Integer> allAppSequenceNumber()
            
            /**
             * 获取所有前台的模板序号
             * @return
             */
            public static List<Integer> allFrontSequenceNumber()
        
        如果因需求需要增加模板文件，需添加对应的模板枚举，枚举的构建参数说明:
            
            int sequenceNumber; // 模板序号
            String template; // 模板名称
            String belong; // 生成文件所属包
            int fileType; // 生成文件类型 0:java ; 1:jsp ; 2:js ; 3:xml
            String suffix; // 生成文件后缀
                