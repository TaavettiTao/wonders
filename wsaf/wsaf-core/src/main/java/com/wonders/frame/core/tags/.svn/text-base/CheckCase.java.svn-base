package com.wonders.frame.core.tags;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.wonders.frame.core.tags.enums.CaseMode;
import com.wonders.frame.core.tags.impl.CheckCaseValidator;
/**
 * @author mengjie
 * 
 *         自定义验证密码是否一致接口@CheckCase，判断一个给定字符串是否全是大写或者小写字符。
 *         用法：@CheckCase(CaseMode.UPPER)
 * 
 */
@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckCaseValidator.class)
@Documented
public @interface CheckCase {
    String message() default "{com.mycompany.constraints.checkcase}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    CaseMode value();
}
