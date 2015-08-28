package com.wonders.frame.core.tags;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.wonders.frame.core.service.MultiCrudService.RelateType;

@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE,ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MultiRelate {
	/** 
     * 在复杂对象中的类型（包含main：主对象，parent：父对象，child:子对象）
     * @return 
     */  
	RelateType type() default RelateType.MAIN;  
    /** 
     * 外键属性（用于type=parent和child时，指定的外键值在保存时会以main对象的主键值替换，从而建立外键对应关系）
     * @return 
     */  
    String[] fk() default "";  

}
