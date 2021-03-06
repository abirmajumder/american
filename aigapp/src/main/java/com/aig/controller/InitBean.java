package com.aig.controller;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class InitBean implements InitializingBean {
	
	@Value("classpath:test1.txt")
	Resource testRes;
	@Value("${glms.query.headers}")
	String query;

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println( query );
		int size = testRes.getInputStream().available();
		byte [] data = new byte[ size ];
		testRes.getInputStream().read(data);
		System.out.println(new String(data) );
	}
	
}
