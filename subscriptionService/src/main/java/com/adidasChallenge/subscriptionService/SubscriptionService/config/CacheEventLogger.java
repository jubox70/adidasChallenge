package com.adidasChallenge.subscriptionService.SubscriptionService.config;

import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CacheEventLogger implements CacheEventListener. The method onEvent prints a log when some event happens.
 * Events are configured in ehcache.xml 
 * @author ernesto.romero
 *
 */
public class CacheEventLogger implements CacheEventListener<Object, Object> {

    private final static Logger logger = LoggerFactory.getLogger(CacheEventLogger.class);

    @Override
    public void onEvent(CacheEvent<? extends Object, ? extends Object> cacheEvent) {
        
    	logger.info("Key cache: " + cacheEvent.getKey() + " -- Old value: " + cacheEvent.getOldValue() + " -- New value: " + cacheEvent.getNewValue());

    }
}


