package com.whitrus.spring.tester.domain.speech.model;

public final class LinearToLogarithmicScaleConverter {
	
	private final double linMin;
	private final double linMax;
	
	private final double logMin;
	private final double logMid;
	private final double logMax;
	
	public LinearToLogarithmicScaleConverter(double linearMin, double linearMax, double logarithmicMin, double logarithmicMiddle, double logarithmicMax) {
		this.linMin = linearMin;
		this.linMax = linearMax;
		
		this.logMin = logarithmicMin;
		this.logMid = logarithmicMiddle;
		this.logMax = logarithmicMax;
	}
	
	public double getValue(double linearValue) {		
		throw new UnsupportedOperationException("I don't know how to implement this");
	}
}
