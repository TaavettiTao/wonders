package com.wonders.frame.complex.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wonders.frame.complex.dao.TestDao;
import com.wonders.frame.complex.service.TestService;
import com.wonders.frame.exception.BusinessException;


@Service("testService")
public class TestServiceImpl implements TestService {
	@Resource
	private TestDao testDao;
	
	public void exception() throws Exception {
		throw new BusinessException("service异常");
	}

	@Override
	public void dao() throws Exception {
		testDao.exception();
	}
}
