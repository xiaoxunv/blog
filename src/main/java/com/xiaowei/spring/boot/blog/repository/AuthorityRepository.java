/**
 * 
 */
package com.xiaowei.spring.boot.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xiaowei.spring.boot.blog.domain.Authority;

/**
 * AuthorityRepository 接口
 * @author yuexiaowei
 *
 */
//这个接口不用重写什么方法，已经够用了

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
	
}
