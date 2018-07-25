package application;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class Nim {

	ArrayList<Sommet> sommets = new ArrayList<>();
	ArrayList<Arete> aretes = new ArrayList<>();
	ArrayList<ArrayList<Arete>> tigesNim = new ArrayList<>();
	BorderPane root;
	HBox hb1;
	ArrayList<Integer> tiges = new ArrayList<>();
	final int YSOL = 547;
	
	public Nim(BorderPane r) {
		root = r;
		lancerNim();
	}
	
	private void lancerNim() {
		Label nbTige = new Label("Nombre de tiges (max. 12) :");
		TextField tfNbTige = new TextField();
		tfNbTige.setPrefSize(40, 8);
		Button btnNbTige = new Button("Valider");
		hb1 = new HBox(nbTige, tfNbTige, btnNbTige);
		hb1.setSpacing(10);
		root.setCenter(hb1);
		
		btnNbTige.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				if (!btnNbTige.getText().equals("")) {
					int nbTige = Integer.parseInt(tfNbTige.getText());
					if (nbTige > 0 && nbTige <13) {
						Label info = new Label ("Indiquez le nombre d'aretes pour chaque tige (max. 12) : ");
						FlowPane pane = new FlowPane (info);
						for (int i=1; i<=nbTige; i++) {
							Label l = new Label ("Tige "+i+" ");
							TextField tf = new TextField("0");
							tf.setMaxSize(40, 8);
							tf.setId(""+(i-1));
							VBox vb = new VBox (l,tf);
							vb.setId("vb");
							pane.getChildren().add(vb);
						}
						Button btnNbArete = new Button("Valider");
						btnNbArete.setOnAction(new EventHandler<ActionEvent>() {
							
							@Override
							public void handle(ActionEvent event) {
								for (int i= 0; i< pane.getChildren().size();i++) {
									//System.out.println(pane.getChildren().get(i).getId());
									if(pane.getChildren().get(i).getId()!=null && pane.getChildren().get(i).getId()=="vb") {
										VBox v =  (VBox) pane.getChildren().get(i);
										String txt = ((TextField) v.getChildren().get(1)).getText();
										System.out.println(txt);
										tiges.add(Integer.parseInt(txt));
									}
								}
								root.setCenter(null);
								for (int i=1; i<=nbTige; i++) {
									int x = 20+46*i;
									Sommet s = new Sommet(x, YSOL);	//ajout d'un sommet
					            	sommets.add(s);
					            	Sommet prec = s;
					            	root.getChildren().add(s.getDraw());
					            	int y = YSOL;
					            	ArrayList<Arete> al = new ArrayList<>();
					            	for (int j=0; j<tiges.get(i-1); j++) {
					            		y = y-60+3*j;
					            		Sommet s2 = new Sommet (x,y) ;
					            		sommets.add(s2);
								        root.getChildren().add(s2.getDraw());
								        Arete a = new Ligne (prec.getPosX(), prec.getPosY(), x,y,Color.FORESTGREEN,prec,s2);
								        aretes.add(a);
								        s.addArete(a); s2.addArete(a);
								        al.add(a);
								        prec=s2;
								        root.getChildren().add(a.getDraw());
					            	}
					            	tigesNim.add(al);
								}
								
								Jeu n = new Jeu(sommets, aretes, tigesNim, Color.FORESTGREEN,root);
								
							}
						});
						pane.getChildren().add(btnNbArete);
						root.setCenter(pane);
					}
				}
				
			}
		});
		root.setCenter(hb1);
		
	}
}
