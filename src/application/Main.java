package application;
	
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.Line;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurve;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import java.awt.MouseInfo;
import java.util.ArrayList;


public class Main extends Application {
	static Stage primStage;
	final int YSOL = 547;
	ArrayList<Sommet> sommets = new ArrayList<Sommet>();
	Sommet sommetPrec = null;	
	Boolean sommetExistant = false;

	ArrayList<Arete> aretes = new ArrayList<Arete>();
	
	Color rouge = Color.rgb(236,84,54);
	Color bleu = Color.rgb(0,128,255);
	Color vert = Color.rgb (43,210,13);

	Color activeColor = rouge;

    Boolean setCourbe = false;	
	@Override
	public void start(Stage primaryStage) {
		try {
			primStage=primaryStage;
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,700,600);
			Line sol = new Line(10, 560, 690, 560);
			sol.setStrokeWidth(10.0);
			root.getChildren().add(sol);
        	
			Button nim = new Button("Nim");
			
			Button edition = new Button("Editer");
			Button jouer = new Button("Jouer");
			edition.setDisable(true);
			
			final ToggleGroup grp = new ToggleGroup();
			RadioButton ajouter = new RadioButton("Ligne");
			RadioButton courbe = new RadioButton("Courbe");
			RadioButton modifier = new RadioButton("Modifier");
			RadioButton deplacer = new RadioButton("Déplacer");
			ajouter.setSelected(true);
			ajouter.setToggleGroup(grp);
			courbe.setToggleGroup(grp);
			modifier.setToggleGroup(grp);
			deplacer.setToggleGroup(grp);
			
			HBox hbEdition = new HBox(nim,edition, jouer, ajouter, courbe, modifier/*,deplacer*/);
			hbEdition.setSpacing(10.0);
			root.setTop(hbEdition);
			
			Button btnRouge = new Button();
			btnRouge.setId("btnRouge");
			Button btnBleu = new Button();
			btnBleu.setId("btnBleu");
			Button btnVert = new Button();
			btnVert.setId("btnVert");
			btnRouge.setDisable(true);	
			Button effacer = new Button("Effacer tout");
			
			EventHandler<ActionEvent> disableButton = new EventHandler<ActionEvent>()
	        {
	            @Override
	            public void handle(ActionEvent e) { 
	            	Button b = (Button)e.getTarget();
	            	if (b.equals(btnRouge)) {
	            		btnVert.setDisable(false);
	            		btnBleu.setDisable(false);
	        			btnRouge.setDisable(true);	
	        			activeColor = rouge;
	            	}
	            	if (b.equals(btnBleu)) {
	            		btnVert.setDisable(false);
	            		btnBleu.setDisable(true);
	        			btnRouge.setDisable(false);	 
	        			activeColor = bleu;           		
	            	}
	            	if (b.equals(btnVert)) {
	            		btnVert.setDisable(true);
	            		btnBleu.setDisable(false);
	        			btnRouge.setDisable(false);	  
	        			activeColor = vert;          		
	            	}
	            	if(sommetPrec != null) sommetPrec.getDraw().setStroke(activeColor);
	            }
	        };
			
	        btnRouge.setOnAction(disableButton);
	        btnBleu.setOnAction(disableButton);
	        btnVert.setOnAction(disableButton);
	        
			VBox vbColors = new VBox(btnRouge, btnBleu, btnVert);
			vbColors.setSpacing(8.0);
			vbColors.setAlignment(Pos.TOP_LEFT);
			root.setRight(vbColors);				 

			edition.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent e) { 
	            	jouer.setDisable(false); 
	            	edition.setDisable(true); 
	            	ajouter.setVisible(true);
	            	courbe.setVisible(true);
	            	modifier.setVisible(true);
	            	deplacer.setVisible(true);  
	            	vbColors.setVisible(true);
	            }
	         });
			jouer.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent e) { 
	            	edition.setDisable(false); 
	            	jouer.setDisable(true);
	            	ajouter.setVisible(false);
	            	courbe.setVisible(false);
	            	modifier.setVisible(false);
	            	deplacer.setVisible(false);
	            	vbColors.setVisible(false);
	            	Jeu j = new Jeu(sommets, aretes, rouge, bleu, vert, root);	            	
	            }
	         });
			
			nim.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent e) { 
	            	edition.setDisable(false);
	            	root.getChildren().remove(1,root.getChildren().size());
	            	
	    			Nim n = new Nim(root);	            	
	            }
	         });
			root.setOnMouseClicked(new EventHandler<MouseEvent>()
	        {	
				@Override
	            public void handle(MouseEvent e) { 
            		if (edition.isDisabled()) {
            			if (e.getButton() == MouseButton.SECONDARY) {
		            		if (sommetPrec != null) {
		        				sommetPrec.getDraw().setStroke(Color.BLACK);
		        				sommetPrec = null;
		        			}	                
		            	}
            			if (setCourbe) {
    						setCourbe = false;
    						sommets.get(sommets.size()-2).getDraw().setStroke(Color.BLACK);    						
    					}
    					
						else if ((courbe.isSelected() || ajouter.isSelected()) && e.getButton() == MouseButton.PRIMARY) {
		            		double x = e.getX();
		            		double y = e.getY();
		            		if(y >= 540) y = YSOL;
		            		else if(y <= 30) y = 31;	            		
		            		
		            		if (!sommetExistant) {			            		
			            		Sommet s = new Sommet(x, y);	//ajout d'un sommet
				            	sommets.add(s);
				            	root.getChildren().add(s.getDraw());
		            			
			        			if (sommetPrec != null) {	//ajout d'une arete
					            	Arete arete;
			        				if (courbe.isSelected()) {		//courbe		            			
			        					Courbe areteCourbe = new Courbe(sommetPrec.getPosX(), sommetPrec.getPosY(), x, y, activeColor, sommetPrec, s);	
				        				setCourbe = true;
				            			root.setOnMouseMoved(new EventHandler<MouseEvent>() {
									       @Override
									        public void handle(MouseEvent e) {
									    	   if(setCourbe) {
							            		    areteCourbe.setControlX1(e.getX()); 
							            		    areteCourbe.setControlY1(e.getY()); 
									            }
									        }							            	
									    });  
				            			arete = areteCourbe;
			        				}
			        				else {
			        					arete = new Ligne(sommetPrec.getPosX(), sommetPrec.getPosY(), x, y, activeColor, sommetPrec, s);	//ajout d'une ligne
			        					sommetPrec.getDraw().setStroke(Color.BLACK);	
				        			}	
			        				
			        				root.getChildren().add(arete.getDraw());
			        				arete.getDraw().setOnMouseClicked(new EventHandler<MouseEvent>() {
							            @Override
							            public void handle(MouseEvent e) {
							            	if (modifier.isSelected()) {
							            		if(e.getButton() == MouseButton.SECONDARY) {	//suppression d'une arete
								            		root.getChildren().remove(arete.getDraw());
								            		arete.getSommet1().delArete(arete);
								            		arete.getSommet2().delArete(arete);								            		
								            	}
							            		else {
							            			arete.getDraw().setStroke(activeColor);	
							            			arete.setColor(activeColor);
							            		}
							            	}
							            	else if (deplacer.isSelected()) {
							            	}
							            }						            
					            	});
			        				sommetPrec.addArete(arete);
			        				s.addArete(arete);
			        				aretes.add(arete);
			        			}	
		        				
		        				s.getDraw().setOnMouseClicked(new EventHandler<MouseEvent>() {
						            @Override
						            public void handle(MouseEvent e) {
						            	if (edition.isDisabled()) {
						            		if (modifier.isSelected()) {								            	
							            		if(e.getButton() == MouseButton.SECONDARY) {	//suppression d'un sommet
								            		if(s.getNbAretes()==0) root.getChildren().remove(s.getDraw());
							            		}
							            		else {
								            		if (sommetPrec != null) sommetPrec.getDraw().setStroke(Color.BLACK);
							        				sommetPrec = s;
							        				sommetPrec.getDraw().setStroke(activeColor);
							            		}
							            	}

					            			if (setCourbe) {
					    						setCourbe = false;
					    						sommets.get(sommets.size()-2).getDraw().setStroke(Color.BLACK);    						
					    					}
							            	else if ((courbe.isSelected() || ajouter.isSelected()) && e.getButton() == MouseButton.PRIMARY) {
							            		if (sommetPrec != null) {								        			
								        			Arete arete;
							        				if (courbe.isSelected()) {				            			
							        					Courbe areteCourbe = new Courbe(sommetPrec.getPosX(), sommetPrec.getPosY(), s.getPosX(), s.getPosY(), activeColor, sommetPrec, s);	
								        				setCourbe = true;
								            			root.setOnMouseMoved(new EventHandler<MouseEvent>() {
													       @Override
													        public void handle(MouseEvent e) {
													    	   if(setCourbe) {
											            		    areteCourbe.setControlX1(e.getX()); 
											            		    areteCourbe.setControlY1(e.getY()); 
													            }
													        }							            	
													    });  
								            			arete = areteCourbe;
							        				}
							        				else {
							        					arete = new Ligne(sommetPrec.getPosX(), sommetPrec.getPosY(),s.getPosX(), s.getPosY(),activeColor, sommetPrec, s);	//ajout d'une ligne
							        					sommetPrec.getDraw().setStroke(Color.BLACK);	
								        			}	   			
								        											        			
								        			arete.getDraw().setOnMouseClicked(new EventHandler<MouseEvent>() {
											            @Override
											            public void handle(MouseEvent e) {
											            	if (modifier.isSelected()) {
											            		if(e.getButton() == MouseButton.SECONDARY) {	//suppression d'une arete
												            		root.getChildren().remove(arete.getDraw());
												            		arete.getSommet1().delArete(arete);
												            		arete.getSommet2().delArete(arete);
												            	}
											            		else arete.getDraw().setStroke(activeColor);					        				
											            	}
											            	else if (deplacer.isSelected()) {
											            	}
											            }						            
									            	});
								        			sommetPrec.addArete(arete);
								        			s.addArete(arete);
								        			aretes.add(arete);
								        			root.getChildren().add(arete.getDraw());
							            		}
							            		sommetPrec = s;
							        			sommetPrec.getDraw().setStroke(activeColor);						        			
							        			sommetExistant = true;
							        		}
							            }
						            }
				            	});
			        			sommetPrec = s;
		        				sommetPrec.getDraw().setStroke(activeColor);
		        				
	        				} 
		            		else if (sommetExistant)sommetExistant=false;
		            	}
	            		
		            	else if (deplacer.isSelected()) {
		            		
		            	}
		            	
	            	}
	            }
	        });			
			
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	

    public static Stage getPrimaryStage() {
        return primStage;
    }
	
	public static void main(String[] args) {
		launch(args);
	}
}
