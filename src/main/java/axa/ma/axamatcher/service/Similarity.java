package axa.ma.axamatcher.service;
import info.debatty.java.stringsimilarity.NormalizedLevenshtein;
import info.debatty.java.stringsimilarity.RatcliffObershelp;


import org.springframework.stereotype.Service;

@Service 
public class Similarity {
	
	NormalizedLevenshtein nl=  new NormalizedLevenshtein();
	RatcliffObershelp ro = new RatcliffObershelp();
	
	
	
	public double calculate(String s1,String s2) {
		
		if (s1.equals(s2)) {
			return 1;
		}
		else  {
			return (ro.similarity(s1, s2) + nl.similarity(s1, s2))/2;
		}
	     
	}
	
	
	private  double ratcliffObershelp(String s1,String s2) {
		return ro.similarity(s1, s2);
	}
	
	
	private double normalizedLevenshtein(String s1,String s2) {
		return nl.similarity(s1, s2);
	}

}
