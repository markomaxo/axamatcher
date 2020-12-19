package axa.ma.axamatcher.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import axa.ma.axamatcher.entity.AxaEntreprise;
import axa.ma.axamatcher.entity.Matcher;
import axa.ma.axamatcher.entity.OmpicEntreprise;
import axa.ma.axamatcher.job.MatcherJob;
import axa.ma.axamatcher.repository.MatcherRepository;
import axa.ma.axamatcher.repository.OmpicEntrepriseRepository;

@Service
public class MatchFinder {
	
	
	private static final Logger LOG = LoggerFactory.getLogger(MatchFinder.class);


	@Autowired
	private OmpicEntrepriseRepository ompicRepository;
	
	@Autowired
	private MatcherRepository matcherRepository;
	
	
	@Autowired
	private Similarity similarity;

	public List<Matcher> findMatcher(AxaEntreprise axaEntrepise) {

		List<Matcher> result = new ArrayList<Matcher>();
		
		
		if(matcherRepository.countByAxaCleanName(axaEntrepise.getNomClean())  > 0){
			return result;
		}
		
		String city = axaEntrepise.getVille();
		Integer l1=     (int) (axaEntrepise.getNomClean().length()*0.75);
		Integer l2=     (int) (axaEntrepise.getNomClean().length()*1.25);
		
		List<OmpicEntreprise> entreptisesInCity = ompicRepository.findByVille(city,l1,l2);
		
		
		
		if (entreptisesInCity.isEmpty()) {
			
			LOG.debug("Search in ALL  for {}  ", axaEntrepise.getNom());
			result = process(axaEntrepise, ompicRepository.findByLenght(l1, l2));
			

		} else {
			LOG.debug("Search in city  {} for {}  ", city ,axaEntrepise.getNom());
			result = process(axaEntrepise, entreptisesInCity);
		}

		return result;

	}

	private List<Matcher> process(AxaEntreprise axaEntrepise, List<OmpicEntreprise> ompicEntrerisesList) {
		List<Matcher> result = new ArrayList<Matcher>();
		String axaName = axaEntrepise.getNomClean();
		for ( OmpicEntreprise ompicEntrerise :  ompicEntrerisesList) {
	
			String ompicName=ompicEntrerise.getDenomClean();
			if (isComparable(axaName, ompicName)) {
				Double sim= similarity.normalizedLevenshtein(axaEntrepise.getNomClean(), ompicEntrerise.getDenomClean());
				if (sim>0.9) {
					result.add(toMatcher(axaEntrepise,ompicEntrerise,"normalizedLevenshtein",sim));
				}
				
				sim= similarity.ratcliffObershelp(axaEntrepise.getNomClean(), ompicEntrerise.getDenomClean());
				if (sim>0.9) {
					result.add(toMatcher(axaEntrepise,ompicEntrerise,"ratcliffObershelp",sim));
				}
			}
			
			
			
		}
		
		LOG.debug("Found {} entreprise for {}  ", result.size() ,axaEntrepise.getNom());
		return result;
	}
	
	
	private boolean isComparable(String nomAxa, String nomOmpic) {
		return (Math.abs(nomAxa.length()- nomOmpic.length())/nomAxa.length() )< 0.25;
	}
	
	private Matcher toMatcher(AxaEntreprise axaEntrepise, OmpicEntreprise ampicEntreprise, String algo, Double similarity) {
		
		Matcher matcher= new Matcher();
		matcher.setAlgo(algo);
		matcher.setAxaCleanName(axaEntrepise.getNomClean());
		matcher.setSimilarity(similarity);
		matcher.setNewDenom(ampicEntreprise.getDenom());
		matcher.setIce(ampicEntreprise.getIce());
		
		
		return matcher;
		
	}
	
	

}
