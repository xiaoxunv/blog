package com.xiaowei.spring.boot.blog.service;

import org.springframework.stereotype.Service;
/**
 * 此类无任务特别，仅为改变status得值
 * @author yuexiaowei
 *
 */
@Service
public class StatusService {

	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
