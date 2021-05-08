package com.gn.demo.utils;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.FileType;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * MyBatis plus 代码生成器
 */
public class MyBatisPlusGenerator {

    /**
     * 作者
     */
    private static final String AUTHOR = "gn";

    /**
     * 工作目录
     */
    private static final String USER_DIR = "C:/Users/sarlin/IdeaProjects/demo";

    /**
     * JDBC 连接地址
     */
    // private static final String JDBC_URL = "jdbc:oracle:thin:@10.67.78.84:15121/HDW";
    private static final String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/mybatisplus?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai";
    /**
     * JDBC 驱动程序
     */
    private static final String JDBC_DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    /**
     * 数据库账号
     */
    private static final String JDBC_USERNAME = "root";
    /**
     * 数据库密码
     */
    private static final String JDBC_PASSWORD = "root";
    /**
     * 包配置 - 父级目录
     */
    private static final String PACKAGE_PARENT = "com.gn.demo";

    /**
     * 包配置 - 模块目录
     * 注意：如果表前缀与模块命相同，生成时会删除前缀，比如：core_admin 最终构建为 Admin, AdminController ...
     */
    private static final String PACKAGE_MODULE_NAME = "";

    /**
     * 包配置 - 实体类目录
     */
    private static final String PACKAGE_ENTITY = "entity";

    /**
     * 包配置 - 数据访问接口目录
     */
    private static final String PACKAGE_MAPPER = "mapper";

    /**
     * 包配置 - 业务处理接口目录
     */
    private static final String PACKAGE_SERVICE = "service";

    /**
     * 包配置 - 业务处理实现目录
     */
    private static final String PACKAGE_SERVICE_IMPL = "service.impl";

    /**
     * 包配置 - 控制器目录
     */
    private static final String PACKAGE_CONTROLLER = "controller";

    /**
     * 要生成的表，用 `,` 分割
     */
    private static final String TABLES = "user";

    /**
     * 全局配置
     *
     * @return {@link GlobalConfig}
     */
    private static GlobalConfig globalConfig() {
        GlobalConfig config = new GlobalConfig();
        config.setOutputDir(USER_DIR + "/src/main/java");
        config.setAuthor(AUTHOR);
        config.setOpen(false);
        //是否覆盖文件
        config.setFileOverride(false);
        // xml resultmap
        config.setBaseResultMap(true);
        // xml columlist
        config.setBaseColumnList(true);
        // 实体属性 Swagger2 注解
        // config.setSwagger2(true);
        //主键策略 uuid
        // config.setIdType(IdType.ASSIGN_UUID);
        return config;
    }

    /**
     * 数据源配置
     *
     * @return {@link DataSourceConfig}
     */
    private static DataSourceConfig dataSourceConfig() {
        DataSourceConfig config = new DataSourceConfig();
        config.setUrl(JDBC_URL);
        config.setDriverName(JDBC_DRIVER_NAME);
        config.setUsername(JDBC_USERNAME);
        config.setPassword(JDBC_PASSWORD);
        config.setTypeConvert(new MySqlTypeConvert() {
            // 自定义数据库表字段类型转换【可选】
            @Override
            public IColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
                if (fieldType.toLowerCase().contains("datetime")) {
                    return DbColumnType.DATE;
                }
                return super.processTypeConvert(globalConfig, fieldType);
            }
        });
        return config;
    }

    /**
     * 包配置
     *
     * @return {@link PackageConfig}
     */
    private static PackageConfig packageConfig() {
        PackageConfig config = new PackageConfig();
        // 哪个父目录下创建包
        config.setParent(PACKAGE_PARENT);
        // 设置模块的名字，比如 core，生成效果为 com.note.core
        config.setModuleName(PACKAGE_MODULE_NAME);
        // 实体类创建在哪个包
        config.setEntity(PACKAGE_ENTITY);
        config.setMapper(PACKAGE_MAPPER);
        config.setService(PACKAGE_SERVICE);
        config.setServiceImpl(PACKAGE_SERVICE_IMPL);
        config.setController(PACKAGE_CONTROLLER);
        return config;
    }

    /**
     * 代码生成模板配置 - Freemarker
     *
     * @return {@link TemplateConfig}
     */
    private static TemplateConfig templateConfig() {
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setEntity("templates/entity.java");
        templateConfig.setMapper("templates/mapper.java");
        templateConfig.setService("templates/service.java");
        templateConfig.setServiceImpl("templates/serviceImpl.java");
        templateConfig.setController("templates/controller.java");
        templateConfig.setXml(null);
        return templateConfig;
    }

    /**
     * 自定义配置
     */
    private static InjectionConfig injectionConfig() {
        InjectionConfig config = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 自定义输出 mapper.xml 到 resources 目录下
        String mapperPath = "/templates/mapper.xml.ftl";
        List<FileOutConfig> focList = new ArrayList<>();

        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(mapperPath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return USER_DIR + "/src/main/resources/mapper/" + tableInfo.getEntityName() + "Mapper"
                        + StringPool.DOT_XML;
            }
        });

        config.setFileCreate((configBuilder, fileType, filePath) -> {
            // 判断自定义文件夹是否需要创建
            // checkDir(USER_DIR + "/src/main/resources/mapper/" + PACKAGE_MODULE_NAME);
            if (fileType == FileType.OTHER || fileType == FileType.ENTITY) {
                // 允许覆盖
                return true;
            }
            // 已经生成 mapper 文件判断存在，不想重新生成返回 false
            return !new File(filePath).exists();
        });

        config.setFileOutConfigList(focList);
        return config;
    }

    /**
     * 代码生成策略配置
     *
     * @return {@link StrategyConfig}
     */
    private static StrategyConfig strategyConfig() {
        // 策略配置,数据库表配置
        StrategyConfig config = new StrategyConfig();
        // 数据库表映射到实体的命名策略
        config.setNaming(NamingStrategy.underline_to_camel);
        // 数据库表字段映射到实体类的命名策略
        config.setColumnNaming(NamingStrategy.underline_to_camel);
        // 实体是否为 lombok 模型
        config.setEntityLombokModel(true);
        config.setInclude(TABLES.split(","));
        // 驼峰转连字符串
        config.setControllerMappingHyphenStyle(true);
        // REST 风格
        config.setRestControllerStyle(true);
        // 表前缀
        config.setTablePrefix(packageConfig().getModuleName() + "_");
        // 字段填充
        List<TableFill> tableFills = new ArrayList<>();

        tableFills.add(new TableFill("is_deleted", FieldFill.INSERT));
        tableFills.add(new TableFill("create_time", FieldFill.INSERT));
        tableFills.add(new TableFill("update_time", FieldFill.INSERT_UPDATE));
        config.setTableFillList(tableFills);

        // CommonBase
        // config.setSuperEntityColumns("id", "create_time", "update_time");
        // config.setSuperEntityClass("com.note.common.base.BaseDomain");
        // config.setSuperServiceClass("com.note.common.base.IBaseService");
        // config.setSuperServiceImplClass("com.note.common.base.BaseServiceImpl");
        // config.setSuperControllerClass("com.note.common.base.BaseController");

        return config;
    }

    public static void main(String[] args) {
        AutoGenerator generator = new AutoGenerator();
        generator.setGlobalConfig(globalConfig());
        generator.setDataSource(dataSourceConfig());
        generator.setPackageInfo(packageConfig());
        generator.setTemplate(templateConfig());
        generator.setTemplateEngine(new FreemarkerTemplateEngine());
        generator.setCfg(injectionConfig());
        generator.setStrategy(strategyConfig());

        generator.execute();
    }

}
