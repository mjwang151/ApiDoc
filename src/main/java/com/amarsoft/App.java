package com.amarsoft;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.Banner.Mode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * Hello world!
 *
 */
@SpringBootApplication
//@MapperScan("com.amarsoft.dao") 使用durid 时不需要配此属性
public class App {

	public static void main(String[] args) { 
		SpringApplication application = new SpringApplication(App.class);
		application.setBannerMode(Mode.OFF);
		application.run(args);
	}
}