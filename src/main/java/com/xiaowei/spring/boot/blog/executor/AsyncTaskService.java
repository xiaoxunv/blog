/**
 * 
 */
package com.xiaowei.spring.boot.blog.executor;

import org.springframework.scheduling.annotation.Async;

/**
 * yuexiaowei
 * 2019年6月7日 下午8:09:25
 */
public class AsyncTaskService {

	/**
	 * 方法自动被注入使用ThreadPoolTaskExecutor作为TaskExecutor
	 * yuexiaowei
	 * @param i
	 * 2019年6月7日 下午8:11:14
	 */
	@Async
	public void executeAsyncTask(Integer i){
		System.out.println("执行异步任务："+i);
		
		
	}
	@Async
	public void executeAsyncTaskPlus(Integer i){
		System.out.println("执行异步任务+1："+i);
		
		
	}
	
}
