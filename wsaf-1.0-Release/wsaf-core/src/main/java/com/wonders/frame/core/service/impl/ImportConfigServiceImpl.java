package com.wonders.frame.core.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wonders.frame.core.dao.ImportConfigDao;
import com.wonders.frame.core.dao.ImportConfigLogDao;
import com.wonders.frame.core.dao.RuleTypeDao;
import com.wonders.frame.core.model.bo.ImportConfig;
import com.wonders.frame.core.model.bo.ImportConfigLog;
import com.wonders.frame.core.model.bo.RuleType;
import com.wonders.frame.core.service.ImportConfigService;
import com.wonders.frame.core.service.RuleTypeService;

@Service("importConfigService")
public class ImportConfigServiceImpl implements ImportConfigService{
	
	@Resource
	private ImportConfigDao importConfigDao;
	@Resource
	private ImportConfigLogDao importConfigLogDao;
	@Override
	public List<ImportConfig> findByEntityAndType(String entity, String type) {
		return importConfigDao.findByEntityAndType(entity, type);
	}
	@Override
	public ImportConfigLog saveLog(ImportConfigLog log) {
		return importConfigLogDao.save(log);
	}
	
	
	


}
