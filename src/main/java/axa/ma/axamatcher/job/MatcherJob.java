package axa.ma.axamatcher.job;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import axa.ma.axamatcher.entity.AxaEntreprise;
import axa.ma.axamatcher.entity.Matcher;
import axa.ma.axamatcher.entity.OmpicEntreprise;
import axa.ma.axamatcher.repository.AxaEntrepriseRepository;
import axa.ma.axamatcher.repository.MatcherRepository;
import axa.ma.axamatcher.repository.OmpicEntrepriseRepository;
import axa.ma.axamatcher.service.Similarity;

@Service
public class MatcherJob {
	
	private static final Logger LOG = LoggerFactory.getLogger(MatcherJob.class);

	@Autowired
	private AxaEntrepriseRepository axaRepository;
	
	
	@Autowired
	private OmpicEntrepriseRepository ompicRepository;
	
	@Autowired
	private MatcherRepository matcherRepository;
	
	@Autowired
	private Similarity similarity;

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	
	
	public Job run() {
		return jobBuilderFactory.get("MatchrJob").incrementer(new RunIdIncrementer()).start(step())
				.build();
	}
	
	public Step step() {
		return stepBuilderFactory
				.get("step")
				.<AxaEntreprise, AxaEntreprise>chunk(100)
				.reader(reader())
				.writer(writer())
				.build();
	}
	

	public RepositoryItemReader<AxaEntreprise> reader() {
		
		  HashMap sorts = new HashMap<>();
		  sorts.put("nPolice", Direction.ASC);
		  RepositoryItemReader<AxaEntreprise>  reader = new RepositoryItemReader<AxaEntreprise>();
		  reader.setRepository(axaRepository);
		  reader.setPageSize(100);
		  //reader.setMaxItemCount(10);
		  reader.setSort(sorts);
		  reader.setMethodName("findAll");
		  return reader;
	}
	
	public ItemWriter<AxaEntreprise> writer() {
		return new ItemWriter<AxaEntreprise>() {
			//OmpicEntrepriseRepository ompicEntrepriseRepository = repository;
			
			@Override
			public void write(List<? extends AxaEntreprise> items) throws Exception {
		
				Double sim;
				for (AxaEntreprise entreprise : items) {
					
					for ( OmpicEntreprise ompic : ompicRepository.findAll()) {
						sim= similarity.normalizedLevenshtein(entreprise.getNomClean(), ompic.getDenomClean());
						if (sim>0.8) {
							Matcher matcher= new Matcher();
							matcher.setAlgo("normalizedLevenshtein");
							matcher.setnPolice(entreprise.getnPolice());
							matcher.setDenom(entreprise.getNom());
							matcher.setSimilarity(sim);
							matcher.setNewDenom(ompic.getDenom());
							matcher.setIce(ompic.getIce());
							matcherRepository.save(matcher);
							LOG.info("Found similarity for {}  {}", entreprise.getNom(),sim);
						}
						
						sim= similarity.ratcliffObershelp(entreprise.getNomClean(), ompic.getDenomClean());
						if (sim>0.8) {
							Matcher matcher= new Matcher();
							matcher.setAlgo("ratcliffObershelp");
							matcher.setnPolice(entreprise.getnPolice());
							matcher.setDenom(entreprise.getNom());
							matcher.setSimilarity(sim);
							matcher.setNewDenom(ompic.getDenom());
							matcher.setIce(ompic.getIce());
							matcherRepository.save(matcher);
							LOG.info("Found similarity for {}  {}", entreprise.getNom(), sim);
						}
						
					} 
					
				}

			}
		};
	}


}
