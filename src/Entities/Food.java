package Entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Food extends Entity{
	
	private static Random casual=new Random();
	private static final int WIDTH=10;				//numero pixel della grandezza del cibo
	private static final int HEIGHT=10;		
	private static Color myColor = new Color(150,75,0); 	// marrone
	
	public  int getWidth() {
		return WIDTH;
	}

	public  int getHeight() {
		return HEIGHT;
	}

	public Food(int width_screen,int height_screen) {
		super(casual.nextInt(width_screen-WIDTH)+10, casual.nextInt(height_screen-HEIGHT)+10);  // assegna posizione random quando viene costruito l'oggetto		
	}

	@Override
	public void tick(int w,int h) {		
	}

	@Override
	public void render(Graphics g) {		//Disegna il cibo
		g.setColor(myColor);
		g.fillRect((int)x, (int)y, WIDTH, HEIGHT);
	}

}
