package Flyweight_Pattern;

import java.util.Random;

public class FlyweightPatternDemo {
	public static void main(String[] args) {
		
		
		
		for(int i = 0; i<20; i++){
			Circle circle = (Circle) ShapeFactory.getCirlce(getRandomColor());
			circle.setRadius(100);
			circle.setX(getRandomX());
			circle.setY(getRandomY());
			circle.draw();
		}
	}
	
	private static String getRandomColor(){
		String[] color = {"white","red","blue","green","black"};
		Random random = new Random();
		
		return color[random.nextInt(5)];
	}
	
	private static int getRandomX(){
		
		Random random = new Random();
		
		return random.nextInt(100);
	}
	
	private static int getRandomY(){
		
		Random random = new Random();
		
		return random.nextInt(100);
	}
}
