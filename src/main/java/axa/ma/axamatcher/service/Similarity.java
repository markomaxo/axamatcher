package axa.ma.axamatcher.service;
import info.debatty.java.stringsimilarity.NormalizedLevenshtein;
import info.debatty.java.stringsimilarity.RatcliffObershelp;


import org.springframework.stereotype.Service;

@Service 
public class Similarity {
	
	NormalizedLevenshtein nl=  new NormalizedLevenshtein();
	RatcliffObershelp ro = new RatcliffObershelp();
	
	
	public double ratcliffObershelp(String s1,String s2) {
		return ro.similarity(s1, s2);
	}
	
	
	public double normalizedLevenshtein(String s1,String s2) {
		return nl.similarity(s1, s2);
	}

}
