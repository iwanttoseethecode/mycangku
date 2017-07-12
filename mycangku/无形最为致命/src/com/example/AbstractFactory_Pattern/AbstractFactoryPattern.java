package com.example.AbstractFactory_Pattern;

import java.awt.MultipleGradientPaint.ColorSpaceType;

import com.example.AbstractFactory_Pattern.AbstractFactory.ColorType;
import com.example.AbstractFactory_Pattern.AbstractFactory.ShapeType;
import com.example.AbstractFactory_Pattern.FactoryProducer.FactoryType;

public class AbstractFactoryPattern {
	
	public static void main(String[] args) {
		ColorFactory colorFactory = (ColorFactory) FactoryProducer.getFactory(FactoryType.COLOR);
		
		Blue blue = (Blue) colorFactory.getColor(ColorType.BLUE);
		blue.fill();
		
		Green green = (Green) colorFactory.getColor(ColorType.GREEN);
		green.fill();
		
		Red red = (Red) colorFactory.getColor(ColorType.RED);
		red.fill();
		
		ShapeFactory shapeFactory = (ShapeFactory) FactoryProducer.getFactory(FactoryType.SHAPE);
		
		Circle circle = (Circle) shapeFactory.getShape(ShapeType.CIRCLE);
		circle.draw();
		
		Rectangle rectangle = (Rectangle) shapeFactory.getShape(ShapeType.RECTANGLE);
		rectangle.draw();
		
		Square square = (Square) shapeFactory.getShape(ShapeType.SQUARE);
		square.draw();
		
	}
	
}
