package com.wonders.frame.core.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wonders.frame.core.model.vo.ValidError;

public class ValidUtil {
	private final static Logger logger = LoggerFactory.getLogger(ValidUtil.class);
	
	private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();  
	static{
		  
	}
	
	public static Validator getValidator(){

		 return validator;  
	}
	
	public static List<ValidError> check(Object obj,Class<?>...groups){
		
		 return buildValidErrors(validator.validate(obj,groups));//验证某个对象   	
		 
	}
	
	public static List<ValidError> check(Object obj,String propertyName,Class<?>...groups){
		
		 return buildValidErrors(validator.validateProperty(obj, propertyName,groups));//验证某个对象的某个属性   
		 		 
	}
	
	public static List<ValidError> check(Class<Object> beanType,String propertyName,Object value,Class<?>...groups){

		 return buildValidErrors(validator.validateValue(beanType, propertyName, value,groups));//验证对象类型的某个属性值   
	}
	

	
	private static List<ValidError> buildValidErrors(Set<ConstraintViolation<Object>> constraintViolations){
		List<ValidError> errors=new ArrayList<ValidError>();
		try{
		 
		 Iterator<ConstraintViolation<Object>> iter = constraintViolations.iterator();  
			
		 while  (iter.hasNext()) {  
			 
			 ConstraintViolation<Object> cv=iter.next();

			 errors.add(new ValidError(cv.getRootBeanClass().getSimpleName(),cv.getLeafBean().getClass().getSimpleName(),cv.getPropertyPath().toString(),cv.getInvalidValue(),cv.getMessage()));
		
		 }
		}catch(Exception e){
			logger.error("Throwable Exception",e);
		}
		 return errors;
		 
	}
	
	
	public static void main(String[] args) {

	}
}
