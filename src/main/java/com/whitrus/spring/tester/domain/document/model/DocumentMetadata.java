package com.whitrus.spring.tester.domain.document.model;

import static com.whitrus.spring.tester.domain.document.model.DocumentStatus.UNDER_REVIEW;

public final class DocumentMetadata
{
	public static final class Type
	{
		public static final int MAX_LENGTH = 16;
	}
	
	public static final class Data
	{
		public static final int MAX_LENGTH = 4096;
	}
	
	public static final class Status
	{
		public static final int MAX_LENGTH = 16;
		public static final DocumentStatus DEFAULT = UNDER_REVIEW;
	}
	
	public static final class Origin
	{
		public static final int MAX_LENGTH = 8;
	}
}
