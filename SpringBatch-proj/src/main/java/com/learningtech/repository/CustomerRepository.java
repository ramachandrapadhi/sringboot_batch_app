package com.learningtech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learningtech.enity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}
