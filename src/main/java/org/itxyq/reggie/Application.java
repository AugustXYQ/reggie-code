package org.itxyq.reggie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author xyq 13127
 * @version 1.0.0
 * @date 2023/9/1
 * @description 程序启动类 瑞吉外卖
 **/
@ServletComponentScan
@SpringBootApplication
@EnableTransactionManagement
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        //log.info("项目启动成功.....");
    }
}
