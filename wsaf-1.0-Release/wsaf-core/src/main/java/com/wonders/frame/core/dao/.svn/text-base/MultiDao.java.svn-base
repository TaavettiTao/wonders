package com.wonders.frame.core.dao;

import java.util.HashMap;
import java.util.List;

import com.wonders.frame.core.model.vo.SimplePage;
import com.wonders.frame.core.model.vo.MultiQuerySqlElement;

public interface MultiDao {
	public SimplePage<HashMap<String,String>> findByPage(MultiQuerySqlElement se,Integer pageNum,Integer pageSize) throws Exception;
	
	public List<HashMap<String,String>> findAll(MultiQuerySqlElement se,Integer rowNum, Integer rowSize) throws Exception;
}
