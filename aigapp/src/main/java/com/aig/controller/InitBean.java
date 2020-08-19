package com.aig.controller;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class InitBean implements InitializingBean {
	
	@Value("classpath:test1.txt")
	Resource testRes;

	@Override
	public void afterPropertiesSet() throws Exception {
		int size = testRes.getInputStream().available();
		byte [] data = new byte[ size ];
		testRes.getInputStream().read(data);
		System.out.println(new String(data) );
	}
	
}
