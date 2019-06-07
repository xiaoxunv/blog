package com.xiaowei.spring.boot.blog.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.xiaowei.spring.boot.blog.domain.User;

/**
 * 用户仓库.
 *
 * @since 1.0.0 2017年3月2日
 * @author <a href="https://waylau.com">Way Lau</a> 
 */
/*
 * JpaRepository相对于CrudRepository多了几个接口，继承了分页排序的repository，
 */
public interface UserRepository extends JpaRepository<User, Long>{
	/**
	 * 根据用户名分页查询用户列表，根据名称进行搜索，他会把匹配到的用户放进Page这个对象里面
	 * @param name
	 * @param pageable
	 * @return
	 */
	Page<User> findByNameLike(String name, Pageable pageable);
	
	/**
	 * 根据用户账号查询用户
	 * @param username
	 * @return
	 */
	User findByUsername(String username);
	
	/**
	 * 根据名称列表查询用户列表
	 * @param usernames
	 * @return
	 */
	List<User> findByUsernameIn(Collection<String> usernames);
}
