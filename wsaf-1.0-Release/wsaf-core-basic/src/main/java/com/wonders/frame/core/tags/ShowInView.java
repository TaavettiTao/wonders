package com.wonders.frame.core.tags;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE,ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ShowInView {
	/** 
     * 是否使用 
     * @return 
     */  
    boolean noUse() default false;  

    /** 
     * 字段名称 
     * @return 
     */  
    String name() default "";  
    /** 
     * 字段描述 
     * @return 
     */  
    //String description() default "";  

    /** 
     * 操作类型 
     * @return 
     */
    OperateType operateType() default OperateType.INPUT;
    
    public enum OperateType {
    	HIDDEN,INPUT,TEXTAREA,SELECT
    
    }
    /** 
     * 字典项类 
     * @return 
     */
    String ccateType() default "";
    
    boolean allowOptionNull() default true;

}
