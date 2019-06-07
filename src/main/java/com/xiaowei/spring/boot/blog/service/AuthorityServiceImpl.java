package com.xiaowei.spring.boot.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaowei.spring.boot.blog.domain.Authority;
import com.xiaowei.spring.boot.blog.repository.AuthorityRepository;

/**
 * Authority 服务接口的实现
 * 
 * @author yuexiaowei
 *
 */
@Service
public class AuthorityServiceImpl implements AuthorityService {

	
	@Autowired
	private AuthorityRepository authorityRepository;

	@Override
	public Authority getAuthorityById(Long id) {
		// TODO Auto-generated method stub

		return authorityRepository.findOne(id);
	}

}
