package com.adidasChallenge.subscriptionService.SubscriptionService.dto;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

/**
 * DTO for presentation layer in inputs. SubscriptionInputDTO  does not extend RepresentationModel. It is a simple
 * POJO because we do not want HAL in swagger doc. Mandatory fields and other checks have annotations for validating
 * @author ernesto.romero
 *
 */


public class SubscriptionInputDTO  {
	
	@NotNull(message = "Email required")
	@Email(message = "Invalid email address format")
	private String email;
	

	private String firstName;
	
	private String gender;
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@NotNull(message = "Birt date required")
	private LocalDate birthDate;
	
	@NotNull(message = "Consent required")
	private Boolean consent;
	
	@NotNull(message = "Newsletter Id required")
	private Long newsletterId;
	
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

	public void setConsent(Boolean consent) {
		this.consent = consent;
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

	public boolean getConsent() {
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
