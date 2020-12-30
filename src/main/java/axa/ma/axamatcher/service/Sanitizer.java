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
				.replace("STE ","") 
				.replace(" STE ","") 
				.replace(" STE","") 
				.replace("SA ","") 
				.replace(" SA ","") 
				.replace(" SA","") 
				.replace(" S.A.R.L ","")
				.replace(" S A R L ","")
				.replace("-SARL","")
				.replace("ESPACE ","")
				.replaceAll("[^a-zA-Z0-9]","")
				.replace("sarl","");
		
	}
	
}
