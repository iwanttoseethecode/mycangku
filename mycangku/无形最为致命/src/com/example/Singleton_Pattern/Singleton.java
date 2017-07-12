package com.example.Singleton_Pattern;

public class Singleton {
	
	private Singleton(){
		
	}
	
	private static class SingletionHolder{
		private static final Singleton singleton = new Singleton();
	}
	
	public static final Singleton getInstance(){
		return SingletionHolder.singleton;
	}
}
