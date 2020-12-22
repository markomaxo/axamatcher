package axa.ma.axamatcher.job;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStream;
import org.springframework.batch.item.ItemStreamException;
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
import axa.ma.axamatcher.service.MatchFinder;
import axa.ma.axamatcher.service.Similarity;

@Service
public class MatcherJob {

	private static final Logger LOG = LoggerFactory.getLogger(MatcherJob.class);

	@Autowired
	private AxaEntrepriseRepository axaRepository;

	@Autowired
	private MatchFinder matchFinder;

	@Autowired
	private MatcherRepository matcherRepository;

	@Autowired
	private Similarity similarity;

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	public Job run() {
		return jobBuilderFactory.get("MatchrJob").incrementer(new RunIdIncrementer()).start(step()).build();
	}

	public Step step() {
		return stepBuilderFactory
				.get("step")
				.<AxaEntreprise, AxaEntreprise>chunk(10)
				.reader(reader())
				.writer(writer())
				.listener(listener())
				.build();
	}

	public RepositoryItemReader<AxaEntreprise> reader() {

		HashMap sorts = new HashMap<>();
		sorts.put("nPolice", Direction.ASC);
		RepositoryItemReader<AxaEntreprise> reader = new RepositoryItemReader<AxaEntreprise>();
		reader.setRepository(axaRepository);
		reader.setPageSize(10);
	    //reader.setMaxItemCount(100);
		reader.setSort(sorts);
		reader.setMethodName("findAll");
		return reader;
	}

	public ItemWriter<AxaEntreprise> writer() {
		return new ItemWriter<AxaEntreprise>() {
			// OmpicEntrepriseRepository ompicEntrepriseRepository = repository;

			@Override
			public void write(List<? extends AxaEntreprise> items) throws Exception {
				
				
				for (AxaEntreprise entreprise : items) {

					Matcher matcher = matchFinder.findMatcher(entreprise);
					if (matcher.getSimilarity() >0) {
						matcherRepository.save(matcher);
					}
				}

			}

		};
	}
	
	public ChunkListener listener() {
		
		return new ChunkListener(){
			@Override
		    public void beforeChunk(ChunkContext context) {
		    }
		 
		    @Override
		    public void afterChunk(ChunkContext context) {
		         
		        int count = context.getStepContext().getStepExecution().getReadCount();
		        LOG.info("ITEM PROCESSED : " + count);
		    }
		     
		    @Override
		    public void afterChunkError(ChunkContext context) {
		    }
		};
	   
	}

}
