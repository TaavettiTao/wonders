package com.wonders.frame.core.tags;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.wonders.frame.core.tags.impl.BetweenImplForDateValidator;

/**
 * @author mengjie
 * 
 * 自定义验证接口@Between，用法：@Between(startDate = "1930-01-01", endDate = "2012-12-31", message = "{..}")  
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.METHOD})
@Constraint(validatedBy = {BetweenImplForDateValidator.class})
public @interface Between {

	public String message();

	public String startDate();

	public String endDate();

	public String format() default "yyyy-MM-dd";

	public Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}

