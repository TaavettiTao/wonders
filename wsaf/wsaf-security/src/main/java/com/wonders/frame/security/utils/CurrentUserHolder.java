package com.wonders.frame.security.utils;

import org.springframework.security.core.context.SecurityContextHolder;

import com.wonders.frame.security.model.bo.UserDetail;

/**
 * 通过spring security提供的SecurityContextHolder对象
 * 获取当前登录的用户信息
 * @author Administrator
 *
 */
public final class CurrentUserHolder {
	
	private CurrentUserHolder() {}
	
	public static UserDetail get() {
		try {
			return (UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (ClassCastException e) {
			return null;
		} catch (NullPointerException e) {
			return null;
		}
	}	
	
}
