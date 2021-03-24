package axa.ma.axamatcher.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.persistence.Column;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import axa.ma.axamatcher.entity.AxaEntreprise;
import axa.ma.axamatcher.entity.Matcher;
import axa.ma.axamatcher.entity.OmpicEntreprise;
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
	
	@Autowired
	private Sanitizer sanitizer;

	private List<OmpicEntreprise> ompicList;
	
	private Map<String, OmpicEntreprise> ompicMap; 
	
	Map<String, OmpicEntreprise> abbreviationMap;
	

	@Autowired
	public MatchFinder(OmpicEntrepriseRepository ompicRepository , Sanitizer sanitizer) {

		this.ompicRepository = ompicRepository;
		ompicList = ompicRepository.findAll();
		
		this.sanitizer = sanitizer;
		
//		ompicMap = ompicList.stream().
//				collect(Collectors.toMap(OmpicEntreprise::getDenomClean, ompic -> ompic , (oldValue, newValue) -> newValue));
//		
//		abbreviationMap = createAbbreviationMap(ompicList);
		
		// this.ompicRepository=ompicRepository;
		LOG.info("--------------------------------------");
	}

	public Matcher findMatcher(AxaEntreprise items) {
		
		Matcher result = new Matcher();

		if (matcherRepository.countByompicDenominationHashee(items.getAxaDenominationHachee()) > 0) {
			return result;
		}

		result =  process(items, ompicList , ompicMap);
		return result;

	}

	private Matcher process(AxaEntreprise axaEntrepise, List<OmpicEntreprise> ompicEntreprisesList , Map<String, OmpicEntreprise> ompicMap) {
		TreeMap<Double, Matcher> matchersMap = new TreeMap<>();
		String axaName = axaEntrepise.getAxaDenominationHachee();
		//String axaVille = axaEntrepise.getVille();
		Integer lenmin = (int) (axaEntrepise.getAxaDenominationHachee().length() * 0.75);
		Integer lenmax = (int) (axaEntrepise.getAxaDenominationHachee().length() * 1.25);
//		if (ompicMap.containsKey(axaName)) {
//			return toMatcher(axaEntrepise, ompicMap.get(axaName), "avglevenrat", 1.0);
//		}
		ompicEntreprisesList.parallelStream().forEach(ompicEntrerise -> {
			Double sim = 0.0;
			Matcher  matcher = new Matcher();
			String ompicName = ompicEntrerise.getOmpicDenominationHashee();
			// String ompicVille = ompicEntrerise.getVille();
			Integer len = ompicName.length();
			// if (axaVille.isEmpty() || axaVille.equals(ompicVille)) {
			
			if (len >= lenmin && len <= lenmax) {
			  sim = similarity.calculate(axaName, ompicName);
			}

			// }

			if (sim>0.50 && sim > matcher.getSimilarity()) {
				matcher = toMatcher(axaEntrepise, ompicEntrerise, "avglevenrat", sim);
				matchersMap.put(sim, matcher);
			}
 
		});
		Matcher matcher = matchersMap != null && !matchersMap.isEmpty() ? matchersMap.lastEntry().getValue() : new Matcher();
		LOG.debug("Found {} entreprise for {}  ", matcher.getSimilarity(), axaEntrepise.getAxaDenomination());
		return matcher;
	}

	private Matcher toMatcher(AxaEntreprise axaEntrepise, OmpicEntreprise ampicEntreprise, String algo,
            Double similarity) {

 

        Matcher matcher = new Matcher();
        matcher.setOmpicDenominationHashee(axaEntrepise.getAxaDenominationHachee()); 
        matcher.setSimilarity(similarity);
        matcher.setBilid(ampicEntreprise.getBilid());
        matcher.setOmpicAdresse(ampicEntreprise.getOmpicAdresse());
        matcher.setOmpicCodeTribunal(ampicEntreprise.getOmpicCodeTribunal());
        matcher.setOmpicDenomination(ampicEntreprise.getOmpicDenomination());
        matcher.setOmpicIce(ampicEntreprise.getOmpicIce());
        matcher.setOmpicRc(ampicEntreprise.getOmpicRc());
        matcher.setOmpicAdresse(ampicEntreprise.getOmpicAdresse());
        matcher.setOmpicCodesActivite(ampicEntreprise.getOmpicCodesActivite());
        matcher.setOmpicCodeVille(ampicEntreprise.getOmpicCodeVille());
        matcher.setOmpicActivite(ampicEntreprise.getOmpicActivite());
        matcher.setOmpicCapital(ampicEntreprise.getOmpicCapital());
        matcher.setOmpicFormeJuridique(ampicEntreprise.getOmpicFormeJuridique());
        matcher.setOmpicFlagSiege(ampicEntreprise.getOmpicFlagSiege());
        matcher.setOmpicFlagNma(ampicEntreprise.getOmpicFlagNma());
        matcher.setOmpicDateRadiation(ampicEntreprise.getOmpicDateImmatriculation());
        matcher.setOmpicDateImmatriculation(ampicEntreprise.getOmpicDateImmatriculation());
        matcher.setOmpicVille(ampicEntreprise.getOmpicCodeVille());
        matcher.setOmpicSigle(ampicEntreprise.getOmpicSigle());
        matcher.setIdj(axaEntrepise.getIdaxa());
        matcher.setSource("ompic");
    

        return matcher;
    }
	
	private String createAbbreviation(String input) {
		input = sanitizer.clean(input);
		return input.replaceAll("\\B.|\\P{L}", "").toLowerCase();
	}
	
	private Map<String, OmpicEntreprise> createAbbreviationMap(List<OmpicEntreprise> ompicEntreprisesList){
		Map<String, OmpicEntreprise> abbreviationMap = new HashMap<>();
		for(OmpicEntreprise o : ompicEntreprisesList) {
			abbreviationMap.put(createAbbreviation(o.getOmpicDenomination()), o);
		}
		return abbreviationMap;
	}
	
	public Matcher findMatcherAbbreviation(AxaEntreprise axaEntrepise, List<OmpicEntreprise> ompicEntreprisesList , Map<String, OmpicEntreprise> ompicMap , Map<String, OmpicEntreprise> abbreviationMap) {
		Matcher result = new Matcher();
		String axaName = axaEntrepise.getAxaDenominationHachee();
		String axaNameAbbreviation = createAbbreviation(axaEntrepise.getAxaDenomination());
		if (abbreviationMap.containsKey(axaName)) {
			result = toMatcher(axaEntrepise, abbreviationMap.get(axaName), "avglevenrat", 1.0);
		} else if(ompicMap.containsKey(axaNameAbbreviation)) {
			result = toMatcher(axaEntrepise, ompicMap.get(axaNameAbbreviation), "avglevenrat", 1.0);
		}
		return result;
	}

}
