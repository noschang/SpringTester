package com.whitrus.spring.tester.domain;

public interface PersistentEntityUpdateDTO<T,D> {

	public void applyUpdates(T entity);

	default public void setDependencies(D dependencies) {
		
	}
}
