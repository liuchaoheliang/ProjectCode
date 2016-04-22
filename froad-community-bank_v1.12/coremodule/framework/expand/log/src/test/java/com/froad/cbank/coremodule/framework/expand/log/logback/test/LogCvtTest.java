package com.froad.cbank.coremodule.framework.expand.log.logback.test;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/applicationContext.xml"})
public class LogCvtTest extends TestCase {

	@Test
	public void error() {
		try {
			throw new Exception("1");
		} catch (Exception e) {
			LogCvt.error("错误日志",e);
		}
		
	}
}
