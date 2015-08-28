package com.wonders.frame.core.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.wonders.frame.core.dao.ObjInfoDao;
import com.wonders.frame.core.model.bo.ObjInfo;
import com.wonders.frame.core.service.ObjInfoService;
import com.wonders.frame.core.utils.JacksonMapper;


@Service("objInfoService")
public class ObjInfoServiceImpl implements ObjInfoService {
	private final Logger logger=LoggerFactory.getLogger(this.getClass());
	@Resource
	private ObjInfoDao objInfoDao;
	
	public Class<?> getEntityClassByType(String type) throws Exception{
		ObjInfo objInfo = objInfoDao.findByType(type);
		if(objInfo!=null){
			HashMap<String, String> hm = JacksonMapper.readValue(objInfo.getParams(),HashMap.class);
			String entityPath=hm.get("entity");
			if(entityPath.equals("")){
				return null;
			}else{
				return Class.forName(entityPath);
			}
		}else{
			return null;
		}

	}

	@Override
	public List<ObjInfo> findByIds(List<Integer> ids) {
		return objInfoDao.findByIds(ids);		
	}
	
	

}