package axa.ma.axamatcher.service;

import org.springframework.stereotype.Service;

@Service
public class Sanitizer {
	
	public String clean(String input) {
		return input
				.toLowerCase()
				.replace("à","a")
				.replace("â","a")
				.replace("é","e")
				.replace("è","e")
				.replace("ê","e")
				.replaceAll("[^a-zA-Z0-9]","")
				.replace("sarl","");
		
	}
	
}
