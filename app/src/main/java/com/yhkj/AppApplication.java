package com.yhkj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @Auther: chen
 * @Date: 2019/3/2
 * @Description:
 */
@EnableCaching
@SpringBootApplication
@MapperScan("com.yhkj.mapper")
@ServletComponentScan(basePackages = "com.yhkj")
public class AppApplication {

    public static void main(String[] args) {

        SpringApplication.run(AppApplication.class, args);

    }
}
