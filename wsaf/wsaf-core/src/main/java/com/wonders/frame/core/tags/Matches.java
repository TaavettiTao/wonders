package com.wonders.frame.core.tags;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
/**
 * @author mengjie
 * 
 * 自定义验证接口@Between，用法：@Between(startDate = "1930-01-01", endDate = "2012-12-31", message = "{..}")  
 *
 */
import com.wonders.frame.core.tags.impl.MatchesValidator;

/**
 * @author mengjie
 * 
 *         自定义验证密码是否一致接口@Matches，用法：@Matches(field = "password", verifyField =
 *         "confirmPassword", message = "{..}") )
 * 
 */
@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MatchesValidator.class)
@Documented
public @interface Matches {
	String message() default "{constraint.not.matches}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	String field();

	String verifyField();
}
