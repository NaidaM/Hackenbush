package application;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

public class Arete {
	private double posX1;
	private double posY1;
	private double posX2;
	private double posY2;
	protected Shape draw;
	private Sommet sommet1;
	private Sommet sommet2;
	private Color color;

	Arete(double x1, double y1, double x2, double y2, Color c, Sommet s1, Sommet s2) {
		posX1 = x1;
		posY1 = y1;
		posX2 = x2;
		posY2 = y2;
		sommet1 = s1;
		sommet2 = s2;
		color = c;
	}

	public void setColor(Color c) {
		color = c;
	}
	
	public Color getColor() {
		return color;
	}
	
	public Shape getDraw() {
		return draw;			
	}
	
	public Sommet getSommet1() {
		return sommet1;			
	}
	
	public Sommet getSommet2() {
		return sommet2;			
	}
	
	public double getPosX1() {
		return posX1;
	}
	
	public double getPosY1() {
		return posY1;
	}
	
	public double getPosX2() {
		return posX2;
	}
	
	public double getPosY2() {
		return posY2;
	}
	
	public void setPosX1(double x) {
		posX1 = x;
	}
	
	public void setPosY1(double y) {
		posY1 = y;
	}
	public void setPosX2(double x) {
		posX2 = x;
	}
	
	public void setPosY2(double y) {
		posY2 = y;
	}


}
