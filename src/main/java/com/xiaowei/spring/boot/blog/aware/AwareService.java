/**
 * 
 */
package com.xiaowei.spring.boot.blog.aware;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

/**
 * Spring依赖注入的最大亮点就是你所有的Bean对Spring容器的存在是没有意识的，即你可以将你的容器替换成别的容器，如Google Guice，这时
 * bean之间的耦合度很低
 * 
 * 但是在实际项目中，你不可避免的要用到Spring容器本身的功能资源，这是你的Bean必须要意识到Spring容器的存在，才能调用Spring提供的资源，
 * 这就是所谓的Spring Aware。
 * 
 * 其实Spring Aware本来就是Spring设计用来框架内部使用的，若使用了Spring Aware，你的Bean将会和Spring框架耦合
 * 
 * Spring
 * Aware的目的是为了让Bean获得Spring容器的服务。因为ApplicationContex接口继承了MessageResource接口、
 * ApplicationEventPublisher接口和ResourceLoader接口，
 * 所以Bean继承ApplicationContextAware可以获得Spring容器的所有服务 但原则上我们还是用到什么接口，就实现什么接口
 * yuexiaowei 2019年6月7日 下午6:30:46
 */
public class AwareService implements BeanNameAware, ResourceLoaderAware {
	private String beanName;
	private ResourceLoader resourceLoader;

	/**
	 * yuexiaowei
	 * 
	 * @param name
	 *            2019年6月7日 下午6:30:48
	 */
	@Override
	public void setBeanName(String name) {
		// TODO Auto-generated method stub
		this.beanName = beanName;
	}

	/**
	 * yuexiaowei
	 * 
	 * @param resourceLoader
	 *            2019年6月7日 下午6:31:16
	 */
	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		// TODO Auto-generated method stub
		this.resourceLoader = resourceLoader;
	}
	
	public void outputResult(){
		System.out.println("Bean的名称为："+beanName);
		Resource resource=resourceLoader.getResource("classpath:com/xiaowei/spring/boot/blog/aware/test.txt");
		try {
			System.out.println("ResourceLoader加载的文件内容为："+IOUtils.toString(resource.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
