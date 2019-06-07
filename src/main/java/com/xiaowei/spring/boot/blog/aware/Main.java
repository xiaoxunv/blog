/**
 * 
 */
package com.xiaowei.spring.boot.blog.aware;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.xiaowei.spring.boot.blog.config.AwareConfig;

/**
 * yuexiaowei 2019年6月7日 下午6:51:21
 */
public class Main {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AwareConfig.class);
		AwareService awareService = context.getBean(AwareService.class);
		awareService.outputResult();
		
		context.close();
	}
}
