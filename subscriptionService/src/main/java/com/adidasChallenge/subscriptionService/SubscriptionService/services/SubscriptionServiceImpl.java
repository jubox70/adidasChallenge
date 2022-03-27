package com.adidasChallenge.subscriptionService.SubscriptionService.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adidasChallenge.subscriptionService.SubscriptionService.entities.SubscriptionEntity;
import com.adidasChallenge.subscriptionService.SubscriptionService.repositories.ISubscriptionRepository;

/**
 * Implementation for business logic service. This class has to implement ISubscriptionService
 * 
 * @author ernesto.romero
 *
 */

@Service
public class SubscriptionServiceImpl implements ISubscriptionService {

	private final static Logger logger = LoggerFactory.getLogger(SubscriptionServiceImpl.class);

	@Autowired
	private ISubscriptionRepository subscriptionRepository;
	
	public Optional<SubscriptionEntity> findById(Long id) {
		return subscriptionRepository.findById(id);

	}

	public List<SubscriptionEntity> findAll() {
		return subscriptionRepository.findAll();
	}

	public SubscriptionEntity insert(SubscriptionEntity subscriptionEntity) {

		SubscriptionEntity subscriptionSaved = subscriptionRepository.save(subscriptionEntity);

		return subscriptionSaved;

	}

	public SubscriptionEntity update(SubscriptionEntity subscriptionEntity) {

		return subscriptionRepository.save(subscriptionEntity);

	}

	public void delete(Long id) {

		subscriptionRepository.deleteById(id);

	}

}
