package com.learningtech.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.learningtech.enity.Customer;
import com.learningtech.processor.CustomerProcessor;
import com.learningtech.repository.CustomerRepository;


@Configuration
@EnableBatchProcessing
public class BatchConfig {

	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	// create reader
	@Bean
	FlatFileItemReader<Customer> customerReader() {
		FlatFileItemReader<Customer> itemReader = new FlatFileItemReader<>();
		itemReader.setResource(new FileSystemResource("src/main/resources/customers.csv"));
		itemReader.setName("csv-reader");
		itemReader.setLinesToSkip(1); // skipping header of CSV
		itemReader.setLineMapper(getLineMapper());
		return itemReader;
	}

	private LineMapper<Customer> getLineMapper() {
		DefaultLineMapper<Customer> lineMapper = new DefaultLineMapper<>();
		DelimitedLineTokenizer tokanizor = new DelimitedLineTokenizer();
		tokanizor.setDelimiter(",");
		tokanizor.setStrict(Boolean.FALSE); // setting id value is not there in cell will null
		tokanizor.setNames("id", "firstName", "lastName", "email", "gender", "contactNo", "country", "dob");

		BeanWrapperFieldSetMapper<Customer> setMapper = new BeanWrapperFieldSetMapper<>();
		setMapper.setTargetType(Customer.class);

		lineMapper.setLineTokenizer(tokanizor);
		lineMapper.setFieldSetMapper(setMapper);
		return lineMapper;
	}

	// create processor
	@Bean
	CustomerProcessor customerProcessor() {
		return new CustomerProcessor();
	}

	// create writer
	@Bean
	RepositoryItemWriter<Customer> customerWriter() {
		RepositoryItemWriter<Customer> itemWriter = new RepositoryItemWriter<>();
		itemWriter.setRepository(customerRepository);
		itemWriter.setMethodName("save");
		return itemWriter;
	}

	// create step 
	@Bean
	Step step() {
		return stepBuilderFactory.get("step-1").<Customer, Customer>chunk(10)
				.reader(customerReader())
				.processor(customerProcessor())
				.writer(customerWriter())
				.build();
	}
	
	// create job
	@Bean
	Job createJob() {
		return jobBuilderFactory.get("customer-job")
		.flow(step())
		.end()
		.build();
	}

}
