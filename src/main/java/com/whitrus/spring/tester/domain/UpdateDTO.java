package com.whitrus.spring.tester.domain;

public interface UpdateDTO<T> {
	public void applyUpdates(T bean);
}
