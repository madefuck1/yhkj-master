package com.yhkj;

import com.yhkj.socket.WebSocketEntity;
import org.java_websocket.WebSocketImpl;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @Auther: chen
 * @Date: 2019/2/26
 * @Description:
 */
@EnableCaching
@SpringBootApplication
@MapperScan("com.yhkj.mapper")
public class WebApplication {
    public static void main(String[] args) {
//        // 聊天系统开启
//        WebSocketImpl.DEBUG = false;
//        int port = 8887; // 端口
//        WebSocketEntity s = new WebSocketEntity(port);
//        s.start();
        //spring boot 启动
        SpringApplication.run(WebApplication.class, args);

    }
}

