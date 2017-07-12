package com.example.State_Pattern;

public class StatePatternDemo {
	
	public static void main(String[] args) {
		Context context = new Context();
		
		context.study();
		context.doAction();
		
		context.amusement();
		context.doAction();
		
		context.sleep();
		context.doAction();
	}
	
}
