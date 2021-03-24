package axa.ma.axamatcher.job;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import axa.ma.axamatcher.entity.OmpicEntreprise;
import axa.ma.axamatcher.repository.OmpicEntrepriseRepository;
import axa.ma.axamatcher.service.Sanitizer;


@Service
public class OmpicEntrepriseLoaderJob {

	
	private static final Logger LOG = LoggerFactory.getLogger(OmpicEntreprise.class);

	@Autowired
	private OmpicEntrepriseRepository repository;
	
	
	@Autowired
	private Sanitizer sanitizer;

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	

	public Job run() {
		return jobBuilderFactory.get("OmpicEntrepriseLoaderJob").incrementer(new RunIdIncrementer()).start(step())
				.build();
	}
	
	public Step step() {
		return stepBuilderFactory
				.get("step")
				.<OmpicEntreprise, OmpicEntreprise>chunk(500)
				.reader(reader())
				.processor(processor())
				.writer(writer())
				.build();
	}
	
	public FlatFileItemReader<OmpicEntreprise> reader() {
		FlatFileItemReader<OmpicEntreprise> itemReader = new FlatFileItemReader<OmpicEntreprise>();
		itemReader.setLineMapper(lineMapper());
		itemReader.setLinesToSkip(1);
		//itemReader.setMaxItemCount(100);
		itemReader.setResource(new FileSystemResource("data/ompic.csv"));
		return itemReader;
	}
	
	
	public ItemWriter<OmpicEntreprise> writer() {
		return new ItemWriter<OmpicEntreprise>() {
			OmpicEntrepriseRepository ompicEntrepriseRepository = repository;

			@Override
			public void write(List<? extends OmpicEntreprise> items) throws Exception {
				for (OmpicEntreprise item : items) {
					ompicEntrepriseRepository.save(item);
				}

			}
		};
	}
	
	
	
	
	public ItemProcessor<OmpicEntreprise, OmpicEntreprise> processor() {
		return new ItemProcessor<OmpicEntreprise, OmpicEntreprise>() {
			public OmpicEntreprise process(OmpicEntreprise ompicEntreprise) throws Exception {
				ompicEntreprise.setOmpicDenominationHashee(sanitizer.clean(ompicEntreprise.getOmpicDenomination()));
				ompicEntreprise.setOmpicDenominationHasheeLength(ompicEntreprise.getOmpicDenomination().length());
				
				return ompicEntreprise;
			}
		};
	}
	
	public LineMapper<OmpicEntreprise> lineMapper() {
		DefaultLineMapper<OmpicEntreprise> lineMapper = new DefaultLineMapper<OmpicEntreprise>();
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setNames(new String[] {"bilid","ompic_ice","ompic_code_tribunal","ompic_rc","ompic_denomination","ompic_forme_juridique","ompic_sigle","ompic_capital","ompic_date_immatriculation","ompic_date_radiation","ompic_adresse","ompic_code_ville","ompic_activite","ompic_flag_siege","ompic_codes_activite","ompic_flag_nma"});
        lineTokenizer.setIncludedFields(new int[] { 0, 1, 2, 3, 4, 5, 6, 7,8,9,10,11,12,13,14,15, 16 });
		BeanWrapperFieldSetMapper<OmpicEntreprise> fieldSetMapper = new BeanWrapperFieldSetMapper<OmpicEntreprise>();
		fieldSetMapper.setTargetType(OmpicEntreprise.class);
		lineMapper.setLineTokenizer(lineTokenizer);
		lineMapper.setFieldSetMapper(fieldSetMapper);
		return lineMapper;
	}
	
	
	

}
