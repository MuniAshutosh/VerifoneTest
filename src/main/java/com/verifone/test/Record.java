package com.verifone.test;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Record {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long simCardNumber;
	int mobileNumber;
	LocalDateTime expiryDate;
	String stateOFRegistration;
	boolean kyc;
	String telecomProvider;
	String fullname;

}
