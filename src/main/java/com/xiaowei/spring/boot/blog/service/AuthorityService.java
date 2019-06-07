package com.xiaowei.spring.boot.blog.service;

import com.xiaowei.spring.boot.blog.domain.Authority;

/**
 * Authority 服务接口
 * @author yuexiaowei
 *
 */
public interface AuthorityService {

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Authority getAuthorityById(Long id);
}
