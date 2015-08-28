/** 
* @Title: CcateServiceImpl.java 
* @Package com.wonders.frame.core.service.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author lushuaifeng
* @version V1.0 
*/
package com.wonders.frame.core.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wonders.frame.core.dao.CcateDao;
import com.wonders.frame.core.model.bo.Ccate;
import com.wonders.frame.core.service.CcateService;

/** 
 * @ClassName: CcateServiceImpl 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 */
@Service("ccateService")
public class CcateServiceImpl implements CcateService{
	private final Logger logger=LoggerFactory.getLogger(this.getClass());
	@Resource
	private CcateDao ccateDao;
	
	
	@Transactional(readOnly = true)
	public Ccate findByType(String type) {
		Ccate entity = ccateDao.findByType(type);
		return entity;
	}


}
