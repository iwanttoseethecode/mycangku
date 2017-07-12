package Decorator_Pattern;

public class RedShapeDecorator extends ShapeDecorator {

	public RedShapeDecorator(Shape decoratorShape) {
		super(decoratorShape);
	}
	
	@Override
	public void draw() {
		// TODO Auto-generated method stub
		super.draw();
		setRedBorder(decoratorShape);
	}
	
	private void setRedBorder(Shape decoratorShape){
		System.out.println("Border Color:Red");
	}
	
}
