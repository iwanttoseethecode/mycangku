package com.example.Factory_Pattern;

public class ShapeFatory {
	
	public static final int RECTANGLE = 1;
	public static final int SQUARE = 2;
	public static final int CIRCLE = 3;
	
	public static Shape createShape(int type){
		switch(type){
		case RECTANGLE:
			return new Rectangle();
		case SQUARE:
			return new Square();
		case CIRCLE:
			return new Circle();
		}
		return null;
	}
	

}
