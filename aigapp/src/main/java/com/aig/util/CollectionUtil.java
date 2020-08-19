package com.aig.util;

import java.util.HashMap;
import java.util.Map;

public class CollectionUtil {

	@SuppressWarnings("rawtypes")
	public static Object getVal( Map map, String keyChain ) {
		String [] keys = keyChain.trim().split("\\.");
		if( keys.length == 1 ) {
			return map.get(keys[0]);
		}
		Object val = null;
		Map temp = map;
		int i = 0;
		for(; i<keys.length; i++) {
			val = temp.get(keys[i]);
			if( val instanceof Map ) {
				temp = (Map)val;
			} else {
				break;
			}
		}
		if( ++i < keys.length ) {
			val = null;
		}
		return val;
	}
	
	public static void main(String[] args) {
		Map<String,Map<String,String>> superMap = new HashMap<>();
		Map<String,String> inner = new HashMap<String,String>();
		inner.put("name", "Honda CRV");
		superMap.put("model", inner);
		System.out.println( getVal(superMap, "model.name") );
		String str = "ballys%20las%20vegas$balls%20las%20vegas%20sully$balls%20las%20vegas%20tequila";
		String [] addrs = str.split("\\$");
		System.out.println( addrs[0] + " - " + addrs[1] + " - " + addrs[2] );
	}
	
}
