package com.wonders.frame.complex.service;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wonders.frame.complex.model.vo.ComplexQuery;
import com.wonders.frame.core.model.bo.Relation;
import com.wonders.frame.core.model.vo.NodeObj;
import com.wonders.frame.core.model.vo.RelatedNode;
import com.wonders.frame.core.model.vo.ReturnObj;
import com.wonders.frame.core.model.vo.SimplePage;



/**
 * @author mengjie
 * log管理
 *
 * 2013-3-30
 */
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public interface ComplexQueryService {

//	public SimplePage<Object> findJointQuery(ComplexQuery complexQuery);

	ReturnObj find(Class<?> crudObjClazz, HttpServletRequest request);

}