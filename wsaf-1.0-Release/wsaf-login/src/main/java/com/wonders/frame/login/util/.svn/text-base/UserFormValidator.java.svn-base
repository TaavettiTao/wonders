package com.wonders.frame.login.util;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.wonders.frame.console.model.bo.User;

@Component("contactFormValidator")
public class UserFormValidator implements Validator {

	
	@SuppressWarnings("unchecked")
	public boolean supports(Class clazz)// 该校验支持的目标类
	{
		return User.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {// 对目标类对象进行校验，错误记录在errors中
		User user = (User) target;
		// LoginName
		userCheck(user.getLoginName(), errors);
		// Password
		passwordCheck(user.getPassword(), errors);
		// Email
		emailCheck(user.getEmail(), errors);
		// mobile
//		moblieCheck(user.getMobile1(), errors);

	}

	public void loginValidate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "loginName","required.loginName", "LoginName is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password","required.password", "Password is required.");
	}

	private void userCheck(String loginName, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "loginName","required.loginName", "LoginName is required.");
		Pattern pattern = Pattern.compile("^[\u4E00-\u9FA5a-zA-Z0-9_]{1,14}$");
		Matcher matcher = pattern.matcher(loginName);
		if (loginName.length() < 1) {
			errors.rejectValue("loginName", "filed.loginName","LoginName's length must be more than 1 character.");
		}else if(!matcher.matches()){
			errors.rejectValue("loginName", "filed.loginName","User loginName is allowed only for English, underline, figures and Chinese characters.");
		}
	}

	public void isExistCheck(Object target, Errors errors) {
		List<User> users = (List<User>) target;
		if (users.size() == 0) {
			errors.rejectValue("loginName", "filed.loginName","name is not exist!.");
		}
	}
	private void passwordCheck(String password, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password","required.password", "Password is required.");
		Pattern pattern = Pattern.compile("^[a-zA-Z0-9_]{1,}$");
		Matcher matcher = pattern.matcher(password);
		if (password.length() < 8 || password.length() > 30) {
			errors.rejectValue("password", "filed.password","Password's length must be between 8 and 30.");
		}else if(!matcher.matches()){
			errors.rejectValue("password", "filed.password","Password is allowed only by English, underline and digital components.");
		}
	}

	private void moblieCheck(String moblie, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mobile","required.mobile", "Mobile is required.");
		Pattern pattern = Pattern.compile("^1[3|5|8][\\d]{9}$");
		Matcher matcher = pattern.matcher(moblie);
		if (!matcher.matches()) {
			errors.rejectValue("mobile", "filed.mobile","Mobile is invalidate");
		}
	}

	private void emailCheck(String email, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email","required.email", "Email is required.");
		Pattern pattern = Pattern.compile("[\\w-]{1,}@[\\w-]{1,}.[\\w-]{1,}");
		Matcher matcher = pattern.matcher(email);
		if (!matcher.matches()) {
			errors.rejectValue("email", "filed.email", "Email is invalidate");
		}
	}

	// public static void main(String[] args) {
	// String s="13345678912";
	// Pattern pattern =Pattern.compile("^1[3|5|8][\\d]{9}$");
	// Matcher matcher= pattern.matcher(s);
	// System.out.println(matcher.matches());
	// }
}