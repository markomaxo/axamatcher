package axa.ma.axamatcher.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import axa.ma.axamatcher.entity.AxaEntreprise;
import axa.ma.axamatcher.entity.Matcher;
import axa.ma.axamatcher.repository.MatcherRepository;

@Service
public class MatchCrawlerFinder {
	@Autowired
	private MatcherRepository matcherRepository;
	
	public Matcher findMatcher(AxaEntreprise axaEntrepise) {

		Matcher result = new Matcher();

		if (matcherRepository.countByAxaCleanName(axaEntrepise.getNomClean()) > 0) {
			return result;
		}

		result = process();
	

		return result;

	}
	
	
	private Matcher process() {

		Matcher result = new Matcher();
		
		// TO DO
		return result;
		
	}
}
