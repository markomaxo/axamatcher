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

import axa.ma.axamatcher.entity.AxaEntreprise;
import axa.ma.axamatcher.repository.AxaEntrepriseRepository;
import axa.ma.axamatcher.service.Sanitizer;

@Service 
public class AxaEntrepriseLoaderJob {

	private static final Logger LOG = LoggerFactory.getLogger(AxaEntreprise.class);

	@Autowired
	private AxaEntrepriseRepository repository;
	
	
	@Autowired
	private Sanitizer sanitizer;

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;


	public Job run() {
		return jobBuilderFactory.get("AxaEntrepriseLoaderJob").incrementer(new RunIdIncrementer()).start(step())
				.build();
	}
	
	
	


	public Step step() {
		return stepBuilderFactory
				.get("step")
				.<AxaEntreprise, AxaEntreprise>chunk(500)
				.reader(reader())
				.processor(processor())
				.writer(writer())
				.build();
	}

	
	public FlatFileItemReader<AxaEntreprise> reader() {
		FlatFileItemReader<AxaEntreprise> itemReader = new FlatFileItemReader<AxaEntreprise>();
		itemReader.setLineMapper(lineMapper());
		itemReader.setLinesToSkip(1);
		//itemReader.setMaxItemCount(300);
		itemReader.setResource(new FileSystemResource("data/axa.csv"));
		return itemReader;
	}

	
	public ItemWriter<AxaEntreprise> writer() {
		return new ItemWriter<AxaEntreprise>() {
			AxaEntrepriseRepository axaEntrepriseRepository = repository;

			@Override
			public void write(List<? extends AxaEntreprise> items) throws Exception {
				for (AxaEntreprise item : items) {
					axaEntrepriseRepository.save(item);
				}

			}
		};
	}
	
	
	public ItemProcessor<AxaEntreprise, AxaEntreprise> processor() {
		return new ItemProcessor<AxaEntreprise, AxaEntreprise>() {
			public AxaEntreprise process(AxaEntreprise axaEntreprise) throws Exception {
				axaEntreprise.setAxaDenominationHachee(sanitizer.clean(axaEntreprise.getAxaDenomination()));
				return axaEntreprise;
			}
		};
	}

	public LineMapper<AxaEntreprise> lineMapper() {
		DefaultLineMapper<AxaEntreprise> lineMapper = new DefaultLineMapper<AxaEntreprise>();
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		//lineTokenizer.setDelimiter(",");
        lineTokenizer.setNames(new String[] { "idaxa","police","axa_produit", "axa_situation_police","axa_date_premier_effet", "date_resiliation","axa_Abreviation", "axa_denomination","axa_adresse","axa_suite_adresse","axa_code_ville", "axa_ville" ,"axa_rc"  });
        lineTokenizer.setIncludedFields(new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 });
		BeanWrapperFieldSetMapper<AxaEntreprise> fieldSetMapper = new BeanWrapperFieldSetMapper<AxaEntreprise>();
		fieldSetMapper.setTargetType(AxaEntreprise.class);
		lineMapper.setLineTokenizer(lineTokenizer);
		lineMapper.setFieldSetMapper(fieldSetMapper);
		return lineMapper;
	}
	
	
	



}
