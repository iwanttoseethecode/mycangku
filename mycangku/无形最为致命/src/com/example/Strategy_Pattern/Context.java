package com.example.Strategy_Pattern;

public class Context {
	private Strategy mStrategy;

	public Context(Strategy mStrategy) {
		super();
		this.mStrategy = mStrategy;
	}
	
	public int executeStrategy(int num1,int num2){
		return mStrategy.doOperation(num1, num2);
	}
}
