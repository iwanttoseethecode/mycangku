package com.example.AbstractFactory_Pattern;

public class ShapeFactory extends AbstractFactory {

	@Override
	public Color getColor(ColorType color) {
		
		try {
			throw new Exception("形状工厂不能生产颜色");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public Shape getShape(ShapeType shape) {
		
		if(shape == ShapeType.CIRCLE){
			return new Circle();
		}else if(shape == ShapeType.RECTANGLE){
			return new Rectangle();
		}else if(shape == ShapeType.SQUARE){
			return new Square();
		}
		
		return null;
	}



}
