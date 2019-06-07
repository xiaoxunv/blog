/**
 * 
 */
package com.xiaowei.spring.boot.blog.config;

import java.util.concurrent.Executor;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * yuexiaowei
 * 2019年6月7日 下午8:06:15
 */
@Configuration
@ComponentScan
@EnableAsync//开启异步任务支持
public class TaskExecutorConfig implements AsyncConfigurer{

	/**
	 * yuexiaowei
	 * @return
	 * 2019年6月7日 下午8:06:46
	 */
	@Override
	public Executor getAsyncExecutor() {
		// TODO Auto-generated method stub
		ThreadPoolTaskExecutor taskExecutor=new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(5);
		taskExecutor.setMaxPoolSize(10);
		taskExecutor.setQueueCapacity(25);
		taskExecutor.initialize();
		return taskExecutor;
	}

	/**
	 * yuexiaowei
	 * @return
	 * 2019年6月7日 下午8:06:46
	 */
	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		// TODO Auto-generated method stub
		return null;
	}

}
