package com.wonders.frame.core.service;

import java.util.List;

import com.wonders.frame.core.model.bo.ImportConfig;
import com.wonders.frame.core.model.bo.ImportConfigLog;

public interface ImportConfigService {
	
	public List<ImportConfig> findByEntityAndType(String entity,String type);

	public ImportConfigLog saveLog(ImportConfigLog log);

}
