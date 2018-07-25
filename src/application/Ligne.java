package application;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Ligne extends Arete {

	Ligne(double x1, double y1, double x2, double y2, Color c, Sommet sx, Sommet sy) {
		super(x1, y1, x2, y2, c, sx, sy);
		draw = new Line(x1, y1, x2, y2);
		draw.setStrokeWidth(5.0);	
		draw.setStroke(c);
	}

}
