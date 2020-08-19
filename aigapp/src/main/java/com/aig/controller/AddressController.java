package com.aig.controller;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.aig.object.AddressCapsule;
import com.aig.service.AddressService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class AddressController {
	
	private ObjectMapper mapper = new ObjectMapper();
	@Autowired private AddressService addressService;

	@GetMapping("/address")
	public String matchScreen( ModelMap model) throws JsonProcessingException {
		//System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject))
		model.put("addresses", mapper.writeValueAsString( AddressRest.caps )) ;
		model.put("md", new AddressCapsule());
		model.put("capsl", mapper.writeValueAsString( new AddressCapsule()));
		return "address_match";
	}
	
	@PostMapping("/matchAddress")
	public String matchAddress( @ModelAttribute("md") AddressCapsule address, ModelMap model) throws Exception {
		model.put("addresses", mapper.writeValueAsString( AddressRest.caps )) ;
		System.out.println( address );
		model.put("md", address);
		model.put("capsl", mapper.writeValueAsString(address));
		
		List<Map<String,Object>> matched = addressService.matchAddress(address);
		Map<String,Object> trace = matched.remove(0);
		if( !trace.isEmpty() ) {
			for( String key : trace.keySet()) {
				Object result = trace.get(key);
				if( result instanceof Collection ) {
					trace.put(key, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result));
				}
			}
			model.put("tracer", trace.keySet());
			model.put("trace", trace);
		}
		model.put("matches", matched);
		return "address_match";
	}
}
