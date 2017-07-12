package com.example.AbstractFactory_Pattern;

public abstract class AbstractFactory {
	public abstract Color getColor(ColorType color);
	public abstract Shape getShape(ShapeType shape);
	
	public enum ColorType{
		RED,GREEN,BLUE
	}
	
	public enum ShapeType{
		RECTANGLE,SQUARE,CIRCLE
	}
}
