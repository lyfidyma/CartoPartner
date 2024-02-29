package com.carto.sn.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.carto.sn.service.Location;

@Controller
public class MapLocationController {
	
//	@RequestMapping("/getPlace")
//	 public String locationSubmit(@ModelAttribute Location location, BindingResult bindingResult, @RequestParam("name") String name) {
//		 
//		 
//			RestTemplate restTemplate = new RestTemplate();
//
//			ResponseEntity<String> topic_body = restTemplate.exchange("https://nominatim.openstreetmap.org/?addressdetails=1&q="+location.getName()+"&format=json&limit=1", 
//					HttpMethod.GET, null, String.class );
//					 
//			String  topics = topic_body.getBody(); 
//			
//			topics = topics.replace("\"address\":{", "");
//			topics = topics.replace("[","");
//			topics = topics.replace("]","");
//			topics = topics.replace("}}","");
//			topics = topics.replace("{","");
//			
//			List<String> test = new ArrayList<String>();
//			
//			String[] list = topics.split(",\"");
//			
//			
//			for (int i = 0; i < list.length; i++) {
//				
//				String j =list[i].replace("\"", "");
//				list[i] = j;
//
//				 String[] list1 = list[i].split(":");
//				 
//				
//				if (list1[0].equals("lat")  ) {
//					test.add(list1[1]);
//				}
//				if ( list1[0].equals("lon") ) {
//								
//					test.add(list1[1]);
//							}
//				if ( list1[0].equals("place_id") ) {
//					
//					test.add(list1[1]);
//				}
//				if ( list1[0].equals("country") ) {
//					
//					test.add(list1[1]);
//				}
//			}			
//			System.out.println(location.getLatitude());
//			System.out.println(location.getLongitud());
//			return "cartographie";
//	  }
//	 
	
}


