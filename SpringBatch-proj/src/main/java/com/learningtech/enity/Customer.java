package com.learningtech.enity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="CUSTOMER_INFO")
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
	
	@Id
	@Column(name="CUSTOMER_ID")
	private Integer id;
	
	@Column(name="FIRST_NAME")
	private String firstName;
	
	@Column(name="LAST_NAME")
	private String lastName;
	
	@Column(name="EMAIL")
	private String email;
	
	@Column(name="GENDER")
	private String gender;
	
	@Column(name="CONTACT_NO")
	private String contactNo;

	@Column(name="COUNTRY")
	private String country;

	@Column(name="DOB")
	private String dob;

}
