package axa.ma.axamatcher.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

import javax.lang.model.util.Elements;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import axa.ma.axamatcher.entity.AxaEntreprise;
import axa.ma.axamatcher.entity.Matcher;
import axa.ma.axamatcher.repository.MatcherRepository;

@Service
public class MatchCrawlerFinder {
	@Autowired
	private MatcherRepository matcherRepository;
	
	
	private static final Logger LOG = LoggerFactory.getLogger(MatchCrawlerFinder.class);

	public Matcher findMatcher(AxaEntreprise axaEntrepise) {

		Matcher result = new Matcher();

		if (matcherRepository.countByAxaCleanName(axaEntrepise.getNomClean()) > 0) {
			return result;
		}

		result = process(axaEntrepise);
	

		return result;

	}
	
	
	private Matcher process(AxaEntreprise axaEntrepise) {

		Matcher result = new Matcher();
		
		//To do url encode
		
		String entrepriseNom= axaEntrepise.getNom();
		try {
			Document doc = Jsoup.connect("https://maroc.welipro.com/recherche?q="+entrepriseNom +"&type=&rs=&cp=1&cp_max=2035272260000&et=&v=").get();	  
		    Elements entrepriseListes = (Elements) doc.select(".card border-bottom-1 .border-bottom-success .rounded-bottom-0");
			LOG.info("Found {} entreprise for {}  ", entrepriseListes , entrepriseNom);
			
		    
		}catch(Exception e) {
			 //.doc.select("p").forEach(System.out::println);
			
			LOG.info(e.getMessage());
		}
	 
		
		// TO DO
		return result;
		
	}
}
