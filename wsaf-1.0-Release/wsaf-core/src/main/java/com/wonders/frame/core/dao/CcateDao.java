/** 
* @Title: CcateDao.java 
* @Package com.wonders.frame.iims.dao 
* @Description: TODO(用一句话描述该文件做什么) 
* @author lushuaifeng
* @version V1.0 
*/
package com.wonders.frame.core.dao;

import org.springframework.data.jpa.repository.Query;

import com.wonders.frame.core.model.bo.Ccate;

/** 
 * @ClassName: CcateDao 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 */
public interface CcateDao  extends GenericRepository<Ccate, Integer>{
	@Query("select a from Ccate a where a.removed=0 and type=?")
	public Ccate findByType(String type);
	
}
