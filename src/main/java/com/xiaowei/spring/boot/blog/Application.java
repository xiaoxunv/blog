package com.xiaowei.spring.boot.blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.endpoint.Endpoint;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xiaowei.spring.boot.blog.endpoint.StatusEndPoint;
import com.xiaowei.spring.boot.blog.service.StatusService;

@SpringBootApplication
@RestController
public class Application {
	@Autowired
	StatusService statusService;
	
	@Bean
	public Endpoint<String> status(){
		Endpoint<String> status=new StatusEndPoint();
		return status;
		
	}
	@RequestMapping("/change")
	public String changeStatus(String status){
		statusService.setStatus(status);
		return "OK";
	}
	

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		
		
		
		
	}
}
