package application;

import java.util.ArrayList;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class Sommet {
	final int YSOL = 547;
	private double posX;
	private double posY;
	private Shape draw;
	private ArrayList<Arete> aretes;
	private int nbAretes;
	private boolean auSol;	//le sommet est-il placé sur le sol?
	
	Sommet(double x, double y) {
		posX = x;
		posY = y;
		
		draw = new Circle(posX, posY, 8.0);
		draw.setFill(Color.TRANSPARENT);
		draw.setStroke(Color.BLACK);
		draw.setStrokeWidth(5);
		
		aretes = new ArrayList<Arete>();
		nbAretes = 0;
		
		if(posY==YSOL) auSol= true;
		else auSol = false;
	}
	
	public boolean getAuSol() {
		return auSol;			
	}
	
	public Shape getDraw() {
		return draw;			
	}
	
	public void addArete(Arete a) {
		aretes.add(a);
		nbAretes++;
	}

	public void delArete(Arete a) {
		aretes.remove(a);
		nbAretes--;
	}
	
	public ArrayList<Arete> getAretes() {
		return aretes;
	}
	
	public int getNbAretes() {
		return nbAretes;
	}
	
	public double getPosX() {
		return posX;
	}
	
	public double getPosY() {
		return posY;
	}
	
	public void setPosX(double x) {
		posX = x;
	}
	
	public void setPosY(double y) {
		posY = y;
	}

}
