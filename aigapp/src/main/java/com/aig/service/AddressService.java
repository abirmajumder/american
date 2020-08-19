package com.aig.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.aig.object.AddressCapsule;
import com.aig.util.CollectionUtil;
import com.aig.util.PlacesUtil;
import com.aig.util.StringUtil;

@Service
public class AddressService {
	
	private List<Map<String,Object>> searchAndTrace( String searchStr, Map<String,Object> trace) throws Exception {
		List<Map<String,Object>> searchResult = PlacesUtil.candidates( PlacesUtil.searchPlaces(searchStr) );
		trace.put(searchStr, searchResult);
		return searchResult;
	}

	public List<Map<String,Object>> searchWithLocAccCtStCountry( AddressCapsule address, Map<String,Object> trace) throws Exception {
		String searchStr = address.getLocation() + " " + address.getAccount() + " " + address.getCity() + 
							" " + address.getState() + " " + address.getCountry();
		return searchAndTrace(searchStr, trace);
	}
	
	public List<Map<String,Object>> searchWithAccCtStCountry( AddressCapsule address, Map<String,Object> trace) throws Exception {
		String searchStr = address.getAccount() + " " + address.getCity() + 
							" " + address.getState() + " " + address.getCountry();
		return searchAndTrace(searchStr, trace);
	}
	
	public List<Map<String,Object>> searchWithLocCtStCountry( AddressCapsule address, Map<String,Object> trace) throws Exception {
		String searchStr = address.getLocation() + " " + address.getCity() + 
							" " + address.getState() + " " + address.getCountry();
		return searchAndTrace(searchStr, trace);
	}
	
	public List<Map<String,Object>> searchWithAddressCtStCountry( AddressCapsule address, Map<String,Object> trace) throws Exception {
		String searchStr = address.getAddressLine() + " " + address.getCity() + 
							" " + address.getState() + " " + address.getCountry();
		return searchAndTrace(searchStr, trace);
	}
	
	private void processSearch( AddressCapsule address, List<Map<String,Object>> allResults, List<Map<String,Object>> matched, 
			List<Map<String,Object>> results) {
		matched.addAll( PlacesUtil.matchLocation(address, results) );
		allResults.addAll(results);
	}
	
	private List<Map<String,Object>> traceMatch( List<Map<String,Object>> matched, String trace ) {
		matched.get(0).put("Status", "MATCH FOUND With " + trace);
		return matched;
	}
	
	public List<Map<String,Object>> matchAddress( AddressCapsule address ) throws Exception {
		List<Map<String,Object>> matched = new ArrayList<>();
		Map<String,Object> trace = new LinkedHashMap<String,Object>(); 
		matched.add(trace);
		List<Map<String,Object>> results = new ArrayList<>();
		
		processSearch(address, results, matched, searchWithLocAccCtStCountry(address, trace));
		if( matched.size() > 1 ) 	return traceMatch( matched, "Location" );

		processSearch(address, results, matched, searchWithLocCtStCountry(address, trace));
		if( matched.size() > 1 ) 	return traceMatch( matched, "Location"  );
		
		processSearch(address, results, matched, searchWithAccCtStCountry(address, trace));
		if( matched.size() > 1 ) 	return traceMatch( matched, "Location"  );
		
		if( !StringUtil.isEmpty( address.getAddressLine() ) ) {
			processSearch(address, results, matched, searchWithAddressCtStCountry(address, trace));
			if( matched.size() > 1 ) 	return traceMatch( matched, "Location"  );
		}
		
		String numLessLocation = StringUtil.removeNumbers(address.getLocation());
		if( !numLessLocation.equals(address.getLocation())  ) {
			AddressCapsule nummLessAddress = new AddressCapsule(numLessLocation, address.getAccount(), address.getAddressLine(), 
												address.getCity(), address.getState(), address.getCountry());
			processSearch(nummLessAddress, results, matched, searchWithLocAccCtStCountry(nummLessAddress, trace));
			if( matched.size() > 1 ) 	return traceMatch( matched, "Location"  );
			
			processSearch(nummLessAddress, results, matched, searchWithLocCtStCountry(nummLessAddress, trace));
			if( matched.size() > 1 ) 	return traceMatch( matched, "Location"  );
		}
		
		String spacedUpLocation = StringUtil.spaceNumbers(address.getLocation());
		if( !spacedUpLocation.equals(address.getLocation())  ) {
			address.setLocation(spacedUpLocation); 
			
			processSearch(address, results, matched, searchWithLocAccCtStCountry(address, trace));
			if( matched.size() > 1 ) 	return traceMatch( matched, "Location"  );
			
			processSearch(address, results, matched, searchWithLocCtStCountry(address, trace));
			if( matched.size() > 1 ) 	return traceMatch( matched, "Location"  );
		}
		
		if( matched.size() <= 1 ) {
			List<String> lookUpParts = new ArrayList<>();
			for( String part : address.getLocation().split(" ") ) {
				if( !"a".equalsIgnoreCase(part) && !"an".equalsIgnoreCase(part) && !"the".equalsIgnoreCase(part) && !"and".equalsIgnoreCase(part) ) {
					lookUpParts.add(part);
				}
			}
			for( Map<String,Object> candidate : results) {
				String name = safeStr.apply(CollectionUtil.getVal(candidate, "name")).toLowerCase();
				if( lookUpParts.stream().anyMatch( prt -> name.contains(prt.toLowerCase()) ) ) {
					boolean shouldExclude = matched.stream().map( can -> safeStr.apply(CollectionUtil.getVal(can, "place_id")))
							.anyMatch( pid -> pid.equalsIgnoreCase(safeStr.apply(CollectionUtil.getVal(candidate, "place_id"))));
					if(!shouldExclude) {
						matched.add(candidate);
						traceMatch(matched, "Location : " + name + " Partially");
					}
				}
			}
		}
		
		return matched;
	}
	
	private Function<Object,String> safeStr = o -> null == o ? "" : o.toString() ;
}





/*String loc = address.getLocation().replaceAll(address.getCity(), "")
	.replaceAll(address.getState(), "")
	.replaceAll(address.getCountry(), "");*/
