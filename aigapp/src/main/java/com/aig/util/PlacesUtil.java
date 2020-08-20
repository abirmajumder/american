package com.aig.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.aig.object.AddressCapsule;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PlacesUtil {
	
	final static String PLACES_URL = "https://maps.googleapis.com/maps/api/place/findplacefromtext/json?input=#S_STR#&inputtype=textquery&fields=formatted_address,geometry,name,place_id,type&key=#KEY#";
	final static String PLACES_URL_WTH_LOC = PLACES_URL + "&locationbias=point:#LAT#,#LNG#"; 
	
	private static ObjectMapper mapper = new ObjectMapper();
	private static RestTemplate restTemplate = new RestTemplate(); 

	private static Function<Object,String> safeStr = o -> null == o ? "" : o.toString() ;
	
	@SuppressWarnings("unchecked")
	public static Map<String,Object> searchPlaces( String address ) throws Exception {
		String reqAddr = PLACES_URL.replaceFirst("#S_STR#", address);
		String res = restTemplate.exchange(reqAddr, HttpMethod.GET, getHttp(), String.class).getBody();
		return mapper.readValue(res, Map.class);
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String,Object> searchPlacesWithLatLng( String address, String lat, String lng) throws Exception {
		String reqAddr = PLACES_URL_WTH_LOC.replaceFirst("#S_STR#", address)
							.replaceFirst("#LAT#", lat).replaceFirst("#LNG#", lng);
		String res = restTemplate.exchange(reqAddr, HttpMethod.GET, getHttp(), String.class).getBody();
		return mapper.readValue(res, Map.class);
	}
	
	private static HttpEntity<String> getHttp() { 
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		return entity;
	}
	
	public static boolean matchLocation( String location, Map<String,Object> candidate ) {
		String name = safeStr.apply(CollectionUtil.getVal(candidate, "name"));
		return null != location && name.toLowerCase().contains(location.toLowerCase());
	}
	
	public static List<Map<String,Object>> matchLocation( AddressCapsule address, List<Map<String,Object>> candidates ) {
		List<Map<String,Object>> matched = candidates.stream().filter( can -> matchLocation(address.getLocation(), can) ).collect(Collectors.toList());
		if( matched.isEmpty() ) {
			String loc = address.getLocation().replaceAll(address.getCity(), "")
											  .replaceAll(address.getState(), "")
											  .replaceAll(address.getCountry(), "")
											  ;
			matched = candidates.stream().filter( can -> matchLocation(loc, can) ).collect(Collectors.toList());
		}
		return matched;
	}
	
	@SuppressWarnings({ "unchecked" })
	public static List<Map<String,Object>> candidates( Map<String,Object> map ) {
		Object candidates = map.get("candidates"); 
		return null == candidates ? new ArrayList<>() : (List<Map<String,Object>>)candidates;
	}
	
}
