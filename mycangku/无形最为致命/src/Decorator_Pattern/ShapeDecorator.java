package Decorator_Pattern;

public class ShapeDecorator implements Shape {
	
	protected Shape decoratorShape;
	
	
	
	public ShapeDecorator(Shape decoratorShape) {
		super();
		this.decoratorShape = decoratorShape;
	}



	@Override
	public void draw() {
		decoratorShape.draw();
	}

}
