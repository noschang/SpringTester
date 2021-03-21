package com.whitrus.spring.tester.domain;

public interface PersistenEntityInsertDTO<T, D> {
	public T createNew();

	default public void setDependencies(D dependencies) {
		
	}
}
