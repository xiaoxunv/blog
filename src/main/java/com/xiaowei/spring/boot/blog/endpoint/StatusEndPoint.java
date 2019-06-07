package com.xiaowei.spring.boot.blog.endpoint;

import org.springframework.beans.BeansException;

import org.springframework.boot.actuate.endpoint.AbstractEndpoint;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.xiaowei.spring.boot.blog.service.StatusService;
//通过这个设置，我们可以在application.properties中通过endpoints.status配置我们的断点
@ConfigurationProperties(prefix="endpoints.status",ignoreUnknownFields=false)
public class StatusEndPoint extends AbstractEndpoint<String> implements ApplicationContextAware{

	ApplicationContext context;
	
	public StatusEndPoint(){
		super("status");
	}
	/**
	 * 通过重写这个方法，返回我们要监控的内容
	 */
	@Override
	public String invoke() {
		StatusService statusService=context.getBean(StatusService.class);
		return "The Current Status is :"+statusService.getStatus();
	}

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		this.context=context;
	}
}
