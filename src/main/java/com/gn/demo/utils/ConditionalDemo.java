package com.gn.demo.utils;

import com.gn.demo.config.DevCondition;
import com.gn.demo.config.StgCondition;
import com.gn.demo.entity.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * @Author sarlin_gn
 * @Date 2021/5/12 14:21
 * @Desc
 */
@Slf4j
@Configuration
public class ConditionalDemo {

    @Bean(name = "dev")
    @Conditional({DevCondition.class})
    public Person devConditionalDemo() {
        log.info("创建了 devPerson");
        return new Person("dev", 28);
    }

    @Bean(name = "stg")
    @Conditional({StgCondition.class})
    public Person stgConditionalDemo() {
        log.info("创建了 stgPerson");
        return new Person("stg", 28);
    }
}
