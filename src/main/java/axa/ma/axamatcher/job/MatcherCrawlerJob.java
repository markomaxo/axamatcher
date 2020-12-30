package axa.ma.axamatcher.job;

import java.sql.ResultSet;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import axa.ma.axamatcher.entity.AxaEntreprise;
import axa.ma.axamatcher.entity.Matcher;
import axa.ma.axamatcher.repository.MatcherRepository;
import axa.ma.axamatcher.service.MatchCrawlerFinder;
import axa.ma.axamatcher.service.MatchFinder;

@Service
public class MatcherCrawlerJob {

	private static final Logger LOG = LoggerFactory.getLogger(MatcherJob.class);

	
	@Autowired
	private MatchCrawlerFinder matchCrawlerFinder;

	@Autowired
	private MatcherRepository matcherRepository;

	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private DataSource dataSource;

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

	public JdbcCursorItemReader<AxaEntreprise> reader() {
		
		JdbcCursorItemReader<AxaEntreprise> reader = new JdbcCursorItemReader<>();
		reader.setSql(
			" select distinct axa_entreprise.nom_clean as nom_clean , axa_entreprise.nom as nom ,  axa_entreprise.ville  as ville from axa_entreprise\n" + 
			"left join  matcher on matcher.axa_clean_name=axa_entreprise.nom_clean\n" + 
			"where matcher.axa_clean_name is null limit 10");
		reader.setDataSource(dataSource);
		reader.setRowMapper(
			(ResultSet resultSet, int rowNum) -> {
			    LOG.info("RowMapper resultset: {}", resultSet);
			    if (!(resultSet.isAfterLast()) && !(resultSet.isBeforeFirst())) {
			    	AxaEntreprise axaEntrepise = new AxaEntreprise();
				axaEntrepise.setNom(resultSet.getString("nom"));
				axaEntrepise.setNomClean(resultSet.getString("nom_clean"));
				axaEntrepise.setVille(resultSet.getString("ville"));
	
				return axaEntrepise;
			    } else {
				LOG.info("Returning null from rowMapper");
				return null;
			    }
			});
		return reader;
	}

	public ItemWriter<AxaEntreprise> writer() {
		return new ItemWriter<AxaEntreprise>() {
			// OmpicEntrepriseRepository ompicEntrepriseRepository = repository;

			@Override
			public void write(List<? extends AxaEntreprise> items) throws Exception {
				
				
				for (AxaEntreprise entreprise : items) {

					Matcher matcher = matchCrawlerFinder.findMatcher(entreprise);
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
