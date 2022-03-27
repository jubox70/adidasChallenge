package com.adidasChallenge.subscriptionService.SubscriptionService.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adidasChallenge.subscriptionService.SubscriptionService.entities.SubscriptionEntity;

/**
 * Interface for database operations. Extends JpaRepository 
 * @author ernesto.romero
 *
 */

@Repository
public interface ISubscriptionRepository extends JpaRepository<SubscriptionEntity, Long> {


}
