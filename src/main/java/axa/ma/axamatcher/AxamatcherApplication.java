package axa.ma.axamatcher;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import axa.ma.axamatcher.job.AxaEntrepriseLoaderJob;
import axa.ma.axamatcher.job.MatcherJob;
import axa.ma.axamatcher.job.OmpicEntrepriseLoaderJob;

@SpringBootApplication
@EnableBatchProcessing

public class AxamatcherApplication {

	public static void main(String[] args) {

		ApplicationContext ctx = SpringApplication.run(AxamatcherApplication.class, args);
		JobLauncher jobLauncher = (JobLauncher) ctx.getBean("jobLauncher");

		// AxaEntrepriseLoaderJob Job
		//AxaEntrepriseLoaderJob axaEntrepriseLoaderJob = (AxaEntrepriseLoaderJob) ctx.getBean("axaEntrepriseLoaderJob");
		//runJob(jobLauncher, axaEntrepriseLoaderJob.run());
	

		// OmpicEntrepriseLoaderJobs Job
		//OmpicEntrepriseLoaderJob ompicEntrepriseLoaderJob = (OmpicEntrepriseLoaderJob) ctx.getBean("ompicEntrepriseLoaderJob");
		//runJob(jobLauncher, ompicEntrepriseLoaderJob.run());
		
		
		//MatcherJob Job
		MatcherJob matcherJob = (MatcherJob) ctx.getBean("matcherJob");
		runJob(jobLauncher, matcherJob.run());

	}

	private static void runJob(JobLauncher jobLauncher, Job job) {
		JobParametersBuilder builder = new JobParametersBuilder();
		builder.addLong("date", System.currentTimeMillis());
		try {
			jobLauncher.run(job, builder.toJobParameters());
		} catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
				| JobParametersInvalidException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
