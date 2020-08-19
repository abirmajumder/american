package com.aig.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
	
	public static String removeNumbers( String str ) {
		if( isEmpty(str) ) return "";
		Pattern pat = Pattern.compile("\\d+");
		Matcher m = pat.matcher(str);
		while (m.find()) {
			str = str.replaceAll(m.group(), "");
		}
		//System.out.println(str);
		return str;
	}
	
	public static String spaceNumbers( String str ) {
		if( isEmpty(str) ) return ""; 
		Pattern pat = Pattern.compile("\\d+");
		Matcher m = pat.matcher(str);
		String number = "";
		while (m.find()) {
			number = m.group();
			str = str.replaceAll( number , " " + number + " ");
		}
		//System.out.println(str.trim().replaceAll("  ", " "));
		return str.trim().replaceAll("  ", " ");
	}

	public static boolean isEmpty(String addressLine) {
		return null == addressLine || addressLine.length() == 0;
	}
}
