package com.whitrus.spring.tester.domain;

public interface PersistentEntityUpdateDTO<T> {
	public void applyUpdates(T bean);
}
