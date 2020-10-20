package Entities;
import java.awt.Graphics;

public abstract class Entity {			//Classe entità che sarà la classe padre di cell e food e all'interno ha le coordinate per il disegno

	protected double x,y;

	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}

	public Entity(double x, double y) {
		this.x = x;
		this.y = y;

	}
	
	
	public abstract void tick(int w,int h);
	public abstract void render(Graphics g);
}
