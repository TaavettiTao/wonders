package com.wonders.frame.core.tags.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.wonders.frame.core.tags.FixLength;

/**
 * @author mengjie
 * 
 *         matches标签实现类
 * 
 */
public class FixLengthImplValidator implements
		ConstraintValidator<FixLength, Long> {
	private long length;

	@Override
	public boolean isValid(Long validLong,
			ConstraintValidatorContext constraintContext) {

		if(validLong!=null){
			if (validLong.toString().length() != length) {
				return false;
			} else {
				return true;
			}
		}else{
			return false;
		}
		
	}

	@Override
	public void initialize(FixLength fixLen) {
		this.length = fixLen.length();
	}

	/**
	 * 判断一个输入的字符串是否是合法的long型数值(1.全数字，2.数值不能越界)
	 * @param str
	 * @return
	 */
/*	public static boolean isValidLong(String str) {
		try {
			long _v = Long.parseLong(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}*/

	/**
	 * 判断一个字符串是否是整型数字的方法
	 * 
	 * @param str
	 * @return
	 */
	/*public boolean isDigit(String str) {
		if (null == str || str.length() == 0) {
			return false;
		}
		for (int i = str.length(); --i >= 0;) {
			int c = str.charAt(i);
			if (c < 48 || c > 57) {
				return false;
			}
		}
		return true;
	}*/
}
