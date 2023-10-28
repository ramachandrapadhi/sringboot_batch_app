package com.learningtech.processor;

import org.springframework.batch.item.ItemProcessor;

import com.learningtech.enity.Customer;

public class CustomerProcessor implements ItemProcessor<Customer, Customer> {

	@Override
	public Customer process(Customer item) throws Exception {
//	write logic for processor
//		if(item.getCountry().equalsIgnoreCase("india")) {
//			return item;
//		}
		return item;
	}

}
