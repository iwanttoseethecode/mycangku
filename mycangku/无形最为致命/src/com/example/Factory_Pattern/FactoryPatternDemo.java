package com.example.Factory_Pattern;

public class FactoryPatternDemo {
	
	public static void main(String[] args) {
		Shape mShape = ShapeFatory.createShape(ShapeFatory.RECTANGLE);
		mShape.draw();
		
		Shape mShape2 = ShapeFatory.createShape(ShapeFatory.SQUARE);
		mShape2.draw();
		
		Shape mShape3 = ShapeFatory.createShape(ShapeFatory.CIRCLE);
		mShape3.draw();
	}
	
}
