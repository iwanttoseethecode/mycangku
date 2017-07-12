package com.example.AbstractFactory_Pattern;

public class ColorFactory extends AbstractFactory {

	@Override
	public Color getColor(ColorType color) {
		
		if(color==ColorType.RED){
			return new Red();
		}else if(color==ColorType.GREEN){
			return new Green();
		}else if(color==ColorType.BLUE){
			return new Blue();
		}
		
		return null;
	}

	@Override
	public Shape getShape(ShapeType shape) {
		
		try {
			throw new Exception("��ɫ��������������״");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
