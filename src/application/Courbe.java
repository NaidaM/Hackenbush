package application;

import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.Line;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

public class Courbe extends Arete {
	private Path path;
	private CubicCurveTo courbe;

	Courbe(double x1, double y1, double x2, double y2, Color c, Sommet sx, Sommet sy) {
		super(x1, y1, x2, y2, c, sx, sy);

		path = new Path();  	
		path.setStrokeWidth(5);
		path.setStroke(c);
	    MoveTo moveTo = new MoveTo(); 
	    moveTo.setX(x1); 	//x1
	    moveTo.setY(y1);		//y1
	    draw = path;
	    courbe = new CubicCurveTo(x2,y2,x2,y2,x2,y2); 
	    path.getElements().add(moveTo);       
	    path.getElements().add(courbe); 
	}
	
	public Path getPath() {
		return path;
	}
	
	public CubicCurveTo getCurve() {
		return courbe;
	}
	
	public void setControlX1(double x) {
		courbe.setControlX1(x);
	}
	
	public void setControlY1(double y) {
		courbe.setControlY1(y);		
	}
	
	public void setControlX2(double x) {
		courbe.setControlX2(x);		
	}
	
	public void setControlY2(double y) {
		courbe.setControlY2(y);		
	}
}
