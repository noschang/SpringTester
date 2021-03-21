package com.whitrus.spring.tester.config.debug;

import java.util.Arrays;
import java.util.Map;

import javax.persistence.EntityManager;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.engine.spi.EntityEntry;
import org.hibernate.engine.spi.EntityKey;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import lombok.RequiredArgsConstructor;

@Aspect
@Component
@Profile("profiling")
@RequiredArgsConstructor
public class PersistenceProfiler {

	private final Logger logger;
	
	private final EntityManager entityManager;	

	@Pointcut("execution(public * org.springframework.data.repository.Repository+.*(..))")
	public void intercept() {
	}

	@Around("intercept()")
	public Object profile(ProceedingJoinPoint joinPoint) {

		Object result = null;
		StopWatch stopWatch = new StopWatch();

		try {

			logPersistenceContext(joinPoint, "before");

			stopWatch.start();
			result = joinPoint.proceed();
			stopWatch.stop();

			logExecutionTime(joinPoint, stopWatch.getLastTaskTimeMillis());
			logPersistenceContext(joinPoint, "after");

		} catch (Throwable e) {

			stopWatch.stop();

			logExecutionTime(joinPoint, stopWatch.getLastTaskTimeMillis());
			logPersistenceContext(joinPoint, "after");

			logger.error(e.getMessage(), e);
		}

		return result;
	}

	private void logExecutionTime(ProceedingJoinPoint joinPoint, long elapsedMs) {
		logger.debug("{} : Execution time: {} ms", joinPoint.getSignature(), elapsedMs);
	}

	private org.hibernate.engine.spi.PersistenceContext getPersistenceContext() {
		SharedSessionContractImplementor sharedSession = entityManager.unwrap(SharedSessionContractImplementor.class);
		return sharedSession.getPersistenceContext();
	}

	@SuppressWarnings("unchecked")
	public void logPersistenceContext(ProceedingJoinPoint joinPoint, String when) {

		logger.debug("Persistence Context {} {}", when, joinPoint.getSignature());

		org.hibernate.engine.spi.PersistenceContext persistenceContext = getPersistenceContext();

		int managedEntities = persistenceContext.getNumberOfManagedEntities();
		int collectionEntriesSize = persistenceContext.getCollectionEntriesSize();

		logger.debug("Total number of managed entities: {}", managedEntities);
		logger.debug("Total number of collection entries: {}", collectionEntriesSize);

		// getEntitiesByKey() will be removed and probably replaced
		// with #iterateEntities()
		@SuppressWarnings("deprecation")
		Map<EntityKey, Object> entitiesByKey = persistenceContext.getEntitiesByKey();

		if (!entitiesByKey.isEmpty()) {
			logger.trace("Entities by key:");

			entitiesByKey.forEach((key, value) -> logger.trace("{}: {}", key, value));

			logger.trace("Status and hydrated state:");

			for (Object entry : entitiesByKey.values()) {
				EntityEntry ee = persistenceContext.getEntry(entry);
				logger.trace("Entity name: {} | Status: {} | State: {}", ee.getEntityName(), ee.getStatus(),
						Arrays.toString(ee.getLoadedState()));
			}
		}

		if (collectionEntriesSize > 0) {
			logger.trace("Collection entries:");
			persistenceContext.forEachCollectionEntry(
					(k, v) -> logger.trace("Key: {}, Value: {}", k, (v.getRole() == null ? "" : v)), false);
		}
	}
}