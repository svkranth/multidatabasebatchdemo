package com.vishnu.multidatasourcedemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
//@EnableBatchProcessing
@ComponentScan({"com.vishnu.multidatasourcedemo.config","com.vishnu.multidatasourcedemo.launcher","com.vishnu.multidatasourcedemo.writer"
,"com.vishnu.multidatasourcedemo.reader","com.vishnu.multidatasourcedemo.model","com.vishnu.multidatasourcedemo.dao","com.vishnu.multidatasourcedemo.entity"})
public class MultidatasourcedemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(MultidatasourcedemoApplication.class, args);
	}

}
