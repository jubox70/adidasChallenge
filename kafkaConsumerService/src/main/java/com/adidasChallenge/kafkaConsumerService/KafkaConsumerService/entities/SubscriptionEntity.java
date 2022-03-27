package com.adidasChallenge.kafkaConsumerService.KafkaConsumerService.entities;

import java.io.Serializable;
import java.time.LocalDate;

/** 
 * 
 * Entity received by kafka listener
 * @author ernesto.romero
 *  
 */
public class SubscriptionEntity implements Serializable {	
	
	private static final long serialVersionUID = 2724125761982158905L;

	private Long id;
	
	private String email;
	
	private String firstName;
	
	private String gender;
	
	private LocalDate birthDate;
	
	private boolean consent;
	
	private Long newsletterId;
	
	public SubscriptionEntity() {
		
	}

	public SubscriptionEntity(Long id, String email, String firstName, 
			String gender, LocalDate birthDate,	boolean consent, Long newsletterId) {
		
		super();
		this.id = id;
		this.email = email;
		this.firstName = firstName;
		this.gender = gender;
		this.birthDate = birthDate;
		this.consent = consent;
		this.newsletterId = newsletterId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public boolean isConsent() {
		return consent;
	}

	public void setConsent(boolean consent) {
		this.consent = consent;
	}

	public Long getNewsletterId() {
		return newsletterId;
	}

	public void setNewsletterId(Long newsletterId) {
		this.newsletterId = newsletterId;
	}

	

}
