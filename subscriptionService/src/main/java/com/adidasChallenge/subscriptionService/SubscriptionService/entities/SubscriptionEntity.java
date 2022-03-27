package com.adidasChallenge.subscriptionService.SubscriptionService.entities;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity represents a database table in data layer
 * @author ernesto.romero
 *
 */

@Entity
@Table(name = "subscriptions")
public class SubscriptionEntity implements Serializable {	
	
	private static final long serialVersionUID = 3808025765229844779L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String email;
	
	@Column(name = "first_name")
	private String firstName;
	
	private String gender;
	
	@Column(name = "birth_date")
	private LocalDate birthDate;
	
	private boolean consent;
	
	@Column(name = "newsletter_id")
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
