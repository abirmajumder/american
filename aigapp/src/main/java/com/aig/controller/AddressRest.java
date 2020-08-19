package com.aig.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.aig.object.AddressCapsule;
import com.aig.service.AddressService;

@RestController
public class AddressRest {
	
	@Autowired private AddressService addressService;
	
	public static AddressCapsule [] caps = {
			new AddressCapsule("DISNEYLAND PARIS", "WALT DISNEY COMPANY", "", "PARIS", "Île-de-France", "France"),
			new AddressCapsule("APLNG Australia", "CONOCOPHILLIPS", "", "CURTIS ISLAND", "QUEENSLAND", "Australia"),
			new AddressCapsule("LNG Train No 1", "CAMERON LNG, LLC", "", "Hackberry", "LA", "United States"),
			new AddressCapsule("Transmission Intermediate lines", "FORTIS INC", "", "PRINCE GEORGE", "BC", "Canada"),
			new AddressCapsule("EDI North Iraq Perzeen Erbil North Iraq", "Weatherford International Ltd", "Perzeen Erbil North Iraq", "Arbil", "Arbil", "Iraq"),
			new AddressCapsule("Or Yehuda Mall", "INVESSTISSMENTS CANPRO LTEE", "OR YEHUDA MALL", "Or Yehuda", "Tel Aviv", "Israel"),
			new AddressCapsule("AL Jubail741","DOW Chemical Company","A2150 A Avenue 21st Street","Al Jubail","Ash Sharqiyah","Saudi Arabia"),
			new AddressCapsule("Ford Otosan - Assembly_Denizevler MahAl","FORD MOTOR COMPANY","Denizevler Mah","Yenikoey","","Turkey")
			/*new AddressCapsule( ),
			new AddressCapsule( ),
			new AddressCapsule( ),
			new AddressCapsule( ),
			new AddressCapsule( )*/
		};
	
	@PostMapping("/lookup")
	public AddressCapsule lookup( @RequestBody AddressCapsule searchCapsule ) throws Exception {
		AddressCapsule result = null;
		for( AddressCapsule cap : caps ) {
			if( cap.getLocation().startsWith( searchCapsule.getLocation() ) ) {
				result = cap;
				break;
			}
		}
		return result;
	}
	
	@PostMapping("/matchAddress_rest")
	public List<Map<String, Object>> matchAddress( @RequestBody AddressCapsule searchCapsule ) throws Exception {
		return addressService.matchAddress(searchCapsule);
	}
	
	@GetMapping(value = "/match")
	public List<Map<String,Object>> match() throws Exception {
		List<Map<String,Object>> matched = new ArrayList<>();
		for( AddressCapsule add : caps) {
			matched.addAll( addressService.matchAddress(add) );
		}
		return matched;
	}

	
}
