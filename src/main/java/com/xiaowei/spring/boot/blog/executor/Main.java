/**
 * 
 */
package com.xiaowei.spring.boot.blog.executor;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.xiaowei.spring.boot.blog.config.TaskExecutorConfig;

/**
 * yuexiaowei
 * 2019年6月7日 下午8:11:58
 */
public class Main {
	
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TaskExecutorConfig.class);
		AsyncTaskService asyncTaskService = context.getBean(AsyncTaskService.class);
		for (int i = 0; i < 10; i++) {
			asyncTaskService.executeAsyncTask(i);
			asyncTaskService.executeAsyncTaskPlus(i);
			
		}
		context.close();
	}

}
