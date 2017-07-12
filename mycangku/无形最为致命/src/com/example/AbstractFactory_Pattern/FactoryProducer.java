package com.example.AbstractFactory_Pattern;

public class FactoryProducer {
	public static AbstractFactory getFactory(FactoryType type){
		if(type == FactoryType.COLOR){
			return new ColorFactory();
		}else if(type == FactoryType.SHAPE){
			return new ShapeFactory();
		}
		return null;
	}
	
	public static enum FactoryType{
		COLOR,SHAPE
	}
	
}
