package com.whitrus.spring.tester.util.querytester;

import java.util.HashMap;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Profile("dev")
@Service
@RequiredArgsConstructor
public class QueryTesterRepository {

	private final EntityManager entityManager;

	@Transactional(readOnly = false)
	public QueryResultDTO executeQuery(QueryDTO queryDTO)
	{
		Query query;
		QueryResultDTO queryResult = new QueryResultDTO();
		
		if (queryDTO.isNative()) {
			query = entityManager.createNativeQuery(queryDTO.getQuery(), HashMap.class);
		}
		else {
			query = entityManager.createQuery(queryDTO.getQuery());
		}
		
		if (queryDTO.getParameters() != null) {
			queryDTO.getParameters().forEach(param -> query.setParameter(param.getName(), param.getValue()));
		}
		
		String queryText = queryDTO.getQuery().toLowerCase().trim();
		
		if (queryText.startsWith("update ") || queryText.startsWith("delete ")) {
			int updatedRows = query.executeUpdate();
			queryResult.setUpdatedRows(updatedRows);
		}
		
		queryResult.setQueryResult(query.getResultList());
		
		return queryResult;
	}
}
