package com.gn.demo.config;
/*
 * @Description: 根据不同环境变量读取对应配置
 * @Author: GuiNing
 * @Date: 2023/9/5 11:27
 */
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Properties;

@Slf4j
public class PropertiesConfig implements ApplicationContextAware {
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Environment environment = applicationContext.getEnvironment();
        String[] activeProfiles = environment.getActiveProfiles();
        String activeProfile = "dev";
        if (activeProfiles.length > 0) {
            activeProfile = activeProfiles[0];
        }
        String fileName = StrUtil.format("application-{}.yml", activeProfile);
        Properties properties = new Properties();

        try {
            properties.load(new ClassPathResource(fileName).getInputStream());
        } catch (IOException e) {
            log.error("{}.load.fail:", fileName, e);
        }
        setProperties(properties);
    }

    private static void setProperties(Properties properties) {
        Field[] fields = PropertiesConfig.class.getFields();
        for (Field field : fields) {
            String name = field.getName();
            Class<?> type = field.getType();

            try {
                field.set(name, Convert.convert(type, properties.get(name)));
            } catch (IllegalAccessException e) {
                log.error("{}.set.fail:", name, e);
            }
        }
    }
}
