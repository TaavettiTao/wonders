package com.wonders.frame.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
@ContextConfiguration({"classpath:applicationContext.xml"})
public class TestSpringBeanUtil extends AbstractTransactionalJUnit4SpringContextTests {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	public void testSetApplicationContext(){
	}

	public void testGetBean() {
	}

	public void testGetProperties(String filepath) {
	}

	public void testGetDataSource(String source) {
	}

	public void testGetSessionFactory() {
	}
}
