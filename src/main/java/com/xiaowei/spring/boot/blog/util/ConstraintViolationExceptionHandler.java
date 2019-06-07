/**
 * 
 */
package com.xiaowei.spring.boot.blog.util;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.StringUtils;

/**
 * 处理异常，主要处理bean验证，假设在验证过程中有问题，比如字段过长，或者数据格式不正确， 会抛出一些异常，
 * 有时候异常不止一个，就用这个类来处理，异常可能是一个数组，通过这个工具类来做一些转化，
 * @author Administrator
 *
 */
public class ConstraintViolationExceptionHandler {

	/**
	 * 获取批量异常信息
	 * @param e
	 * @return
	 */
	public static String getMessage(ConstraintViolationException e) {
		List<String> msgList = new ArrayList<>();
		for (ConstraintViolation<?> constraintViolation : e.getConstraintViolations()) {
			msgList.add(constraintViolation.getMessage());
        }
		String messages = StringUtils.join(msgList.toArray(), ";");
		return messages;
	}

}
