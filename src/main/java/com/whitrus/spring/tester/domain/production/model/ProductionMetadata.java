package com.whitrus.spring.tester.domain.production.model;

//import br.univali.sapi.domain.qualis.model.QualisStratum;

public final class ProductionMetadata
{
	public static final class LattesId 
	{
		public static final int MIN_LENGTH = 32;
		public static final int MAX_LENGTH = 32;
		public static final String DEFAULT = "NONE";
	}
	
	public static final class Year
	{
		public static final int MIN_VALUE = 1950;
	}
	
	public static final class Title
	{
		public static final int MAX_LENGTH = 2040;
	}
	
	public static final class Reference
	{
		public static final int MAX_LENGTH = 2040;
	}
	
	public static final class Category
	{
		public static final int MAX_LENGTH = 16;
	}
	
	public static final class Status
	{
		public static final int MAX_LENGTH = 16;
	}
	
	public static final class Attributes
	{
		public static final int MAX_LENGTH = 8192;
	}
	
	public static final class Data
	{
		public static final int MAX_LENGTH = 8192;
	}
	
	public static final class Qualis
	{
		public static final int MAX_LENGTH = 8;
//		public static final QualisStratum DEFAULT = QualisStratum.NONE;
	}
}
