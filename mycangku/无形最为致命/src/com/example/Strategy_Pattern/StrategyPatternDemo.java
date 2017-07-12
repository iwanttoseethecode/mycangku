package com.example.Strategy_Pattern;

public class StrategyPatternDemo {

	public static void main(String[] args) {
		
		Context context = new Context(new OperationAdd());
		System.out.println(" 6 + 6 = "+context.executeStrategy(6, 6));
		
		context = new Context(new OperationSubstract());
		System.out.println(" 6 - 6 = "+context.executeStrategy(6, 6));
		
		context = new Context(new OperationMultiply());
		System.out.println(" 6 * 6 = "+context.executeStrategy(6, 6));
		
	}

}
