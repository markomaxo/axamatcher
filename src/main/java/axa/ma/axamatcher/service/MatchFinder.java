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

	private List<OmpicEntreprise> ompicList;

	@Autowired
	public MatchFinder(OmpicEntrepriseRepository ompicRepository) {

		this.ompicRepository = ompicRepository;
		ompicList = ompicRepository.findAll();

		// this.ompicRepository=ompicRepository;
		LOG.info("--------------------------------------");
	}

	public Matcher findMatcher(AxaEntreprise axaEntrepise) {

		Matcher result = new Matcher();

		if (matcherRepository.countByAxaCleanName(axaEntrepise.getNomClean()) > 0) {
			return result;
		}

		result = process(axaEntrepise, ompicList);
	

		return result;

	}

	private Matcher process(AxaEntreprise axaEntrepise, List<OmpicEntreprise> ompicEntrerisesList) {
		Matcher result = new Matcher();
		String axaName = axaEntrepise.getNomClean();
		String axaVille = axaEntrepise.getVille();
		Integer lenmin = (int) (axaEntrepise.getNomClean().length() * 0.75);
		Integer lenmax = (int) (axaEntrepise.getNomClean().length() * 1.25);
		Double sim;

		for (OmpicEntreprise ompicEntrerise : ompicEntrerisesList) {
			sim = 0.0;
			String ompicName = ompicEntrerise.getDenomClean();
			String ompicVille = ompicEntrerise.getVille();
			Integer len = ompicName.length();
			if (axaVille.isEmpty() || axaVille.equals(ompicVille)) {
				if (len >= lenmin && len <= lenmax) {
					sim = similarity.calculate(axaName, ompicName);
				}

			}

			if (sim > 0.85 &&   sim >= result.getSimilarity() ) {
				result=toMatcher(axaEntrepise, ompicEntrerise, "avglevenrat", sim);
			}

		}

		LOG.debug("Found {} entreprise for {}  ", result.getSimilarity(), axaEntrepise.getNom());
		return result;
	}

	private Matcher toMatcher(AxaEntreprise axaEntrepise, OmpicEntreprise ampicEntreprise, String algo,
			Double similarity) {

		Matcher matcher = new Matcher();
		matcher.setAlgo(algo);
		matcher.setAxaCleanName(axaEntrepise.getNomClean());
		matcher.setSimilarity(similarity);
		matcher.setNewDenom(ampicEntreprise.getDenom());
		matcher.setIce(ampicEntreprise.getIce());

		return matcher;

	}

}
