package com.aig.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class TdsService implements ITdsService {

	@Override
	public Map<String, Object> getHeaderGroup() {
		Map<String, Object> data = new HashMap<>();
		data.put("name", "Purnendu Poddar");
		return data ;
	}

}
