package com.adidasChallenge.subscriptionService.SubscriptionService.services;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.transaction.annotation.Transactional;

import com.adidasChallenge.subscriptionService.SubscriptionService.entities.SubscriptionEntity;

/**
 * Interface that represents a Service. The interface has the method definitions for
 * business logic operations. Some methods have cache storage to save data queries and
 * make database access faster
 * @author ernesto.romero
 *
 */

public interface ISubscriptionService {
			
	@Cacheable(value = "subscription", unless = "#result == null")
	@Transactional(readOnly = true)
	public Optional<SubscriptionEntity> findById(Long id);
	
	@Cacheable("subscriptions")
	@Transactional(readOnly = true)
	public List<SubscriptionEntity> findAll();
	
	
	@CachePut(value = "subscription", key = "#result.id", unless = "#result == null || #result.id == null")
	@CacheEvict(value = "subscriptions", allEntries = true)
	@Transactional
	public SubscriptionEntity insert(SubscriptionEntity subscriptionEntity);
	
	@CachePut(value = "subscription", key = "#result.id", unless = "#result == null || #result.id == null")
	@CacheEvict(value = "subscriptions", allEntries = true, condition = "#result != null &&  #result.id != null")
	@Transactional
	public SubscriptionEntity update(SubscriptionEntity subscriptionEntity);
	
	@Caching(evict = {
			@CacheEvict(value = "subscription", key = "#id"),
		    @CacheEvict(value = "subscriptions", allEntries = true)
		})
	@Transactional
	public void delete(Long id);

}
