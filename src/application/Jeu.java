package application;

import java.awt.Window;
import java.util.ArrayList;
import java.util.Optional;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Jeu {
	ArrayList<Sommet> sommets=new ArrayList<>();
	ArrayList<Arete> aretes=new ArrayList<>();
	ArrayList<ArrayList<Arete>> aretesNim=new ArrayList<>();
	Color rouge, vert, bleu;
	BorderPane root;	
	int joueurTour = 1;
	Label labTour = new Label ();
	Label labJoueur = new Label();
	boolean nim;
	boolean misere;
	
	public Jeu (ArrayList<Sommet> s, ArrayList<Arete> a, Color r, Color b, Color v, BorderPane p) {
		sommets = s;
		aretes = a;
		rouge = r;
		vert = v;
		bleu = b;
		root = p;
		nim=false;
		lancerJeu();
	}
	
	public Jeu (ArrayList<Sommet> s, ArrayList<Arete> a, ArrayList<ArrayList<Arete>> al, Color v, BorderPane p) { //Nim
		sommets = s;
		aretes = a;
		aretesNim = al;
		vert = v;
		root = p;
		nim=true;
		lancerJeu();
	}
	
	public void tour() {
		int nbRouge=0, nbVert=0, nbBleu=0;
		for (int i = 0; i< aretes.size(); i++) {
			if(!nim) {
				if (aretes.get(i).getColor() == rouge) nbRouge++;
				if (aretes.get(i).getColor() == bleu) nbBleu++;
			}
			if (aretes.get(i).getColor() == vert) nbVert++;
		}
		if(!nim) {
			if(nbVert == 0 && nbRouge == 0) {	
				if(!misere)quitterJeu("Bleu");
				else quitterJeu("Rouge");
			}
			else if(nbVert == 0 && nbBleu == 0) {
				if(!misere)quitterJeu("Rouge");	
				else quitterJeu("Bleu");
			}
		}
		else if (nim){
			if (joueurTour%2==1 && nbVert==0) {	//tour rouge
				if (!misere)quitterJeu("Bleu");
				else quitterJeu("Rouge");
			}
			else if(joueurTour%2==0 && nbVert==0) {	//tour bleu
				if (misere)quitterJeu("Bleu");
				else quitterJeu("Rouge");
			}
		}
		if (aretes.size()<1) {
			quitterJeu(null);
		}
		else {
			if(misere)labTour.setText("Misère - Tour "+ joueurTour+" : ");	
			else if(!misere)labTour.setText("Normal - Tour "+ joueurTour+" : ");
			if (joueurTour%2 == 1) {
				labJoueur.setText("Rouge");
				labJoueur.setTextFill(Color.RED);
			}
			else {
				labJoueur.setText("Bleu");
				labJoueur.setTextFill(Color.DEEPSKYBLUE);
			}
			for(int i=0; i<sommets.size(); i++) { //suppression des sommets sans aretes	
				if (sommets.get(i).getNbAretes()==0) {
					sommets.get(i).getDraw().setVisible(false);
					sommets.remove(i);
				}
			}
			
			/*for(int i=0; i<aretes.size(); i++) { //suppression des aretes non liées au sol
				System.out.println("tour"+joueurTour+" "+sommets.get(i).getNbAretes());
	
				if (!aretes.get(i).getSommet1().getAuSol() && !aretes.get(i).getSommet2().getAuSol()) {
					
					for (int a=0; a<aretes.get(i).getSommet1().getNbAretes(); a++) {
						
					}
					for (int a=0; a<aretes.get(i).getSommet2().getNbAretes(); a++) {
						
					}
					
				}
			}*/
		}
	}
	
	public void lancerJeu(){
		ButtonType normalbtn = new ButtonType("Normal");
		ButtonType miserebtn = new ButtonType("Misère");
		Alert alert = new Alert(AlertType.INFORMATION,"Voulez-vous jouer en mode normal ou misère ?",normalbtn,miserebtn);
	
		alert.setTitle("Mode de jeu");
		Optional<ButtonType> result= alert.showAndWait();
		if (result.get() == normalbtn)misere=false;
		else misere = true;
		
		labTour.setFont(new Font("Calibri", 28));
		labJoueur.setFont(new Font("Calibri", 28));
		HBox hb = new HBox(labTour,labJoueur);
		hb.setAlignment(Pos.CENTER);
		root.setBottom(hb);
		
		if(aretes.size() <2) {
			alert = new Alert(AlertType.WARNING, "Pas assez d'aretes pour jouer", ButtonType.OK);
			alert.showAndWait();
			quitterJeu(null);
		}
		else tour();
		for(int i=0; i<aretes.size(); i++) {
			final Arete arete = aretes.get(i);
			arete.getDraw().setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
		            public void handle(MouseEvent e) {
		            	if (joueurTour%2 == 1) { //joueur Rouge
		            		if (arete.getColor() == rouge || arete.getColor() == vert) {
		            			if(nim) {
		            				for (ArrayList<Arete> al: aretesNim) {
		            					if (al.contains(arete)) {
		            						int ind = al.indexOf(arete);
		            						for (int j = ind; j<al.size(); j++) {
			            						al.get(j).getDraw().setVisible(false);
			            						aretes.remove(al.get(j));
			            						for(int i=0; i<sommets.size(); i++) { //suppression de l'arete dans sommets
			    		            				if (sommets.get(i).getAretes().contains(al.get(j))) {
			    		            					sommets.get(i).delArete(al.get(j));
			    		            				}
			    		            				if (sommets.get(i).getNbAretes() <1) {
			    		            					sommets.get(i).getDraw().setVisible(false);
			    		            				}
			    		            			}
		            						}
		            					}
		            				}
		            			}
		            			else if (!nim){
		            				arete.getDraw().setVisible(false);
			            			aretes.remove(arete);
			            			for(int i=0; i<sommets.size(); i++) { //suppression de l'arete dans sommets
    		            				if (sommets.get(i).getAretes().contains(arete)) {
    		            					sommets.get(i).delArete(arete);
    		            				}
    		            				if (sommets.get(i).getNbAretes() <1) {
    		            					sommets.get(i).getDraw().setVisible(false);
    		            				}
    		            			}
		            			}
		            			for(int i=0; i<sommets.size(); i++) { //suppression de l'arete dans sommets
		            				if (sommets.get(i).getAretes().contains(arete)) {
		            					sommets.get(i).delArete(arete);
		            				}
		            				if (sommets.get(i).getNbAretes() <1) {
		            					sommets.get(i).getDraw().setVisible(false);		            					
		            				}
		            			}
		            			joueurTour++;
		            			tour();
		            		}
		            	}
		            	else if (joueurTour%2 == 0) { //joueur Bleu
		            		if (arete.getColor() == bleu || arete.getColor() == vert) {
		            			if(nim) {
		            				for (ArrayList<Arete> al: aretesNim) {
		            					if (al.contains(arete)) {
		            						int ind = al.indexOf(arete);
		            						for (int j = ind; j<al.size(); j++) {
			            						al.get(j).getDraw().setVisible(false);
			            						aretes.remove(al.get(j));
			            						for(int i=0; i<sommets.size(); i++) { //suppression de l'arete dans sommets
			    		            				if (sommets.get(i).getAretes().contains(al.get(j))) {
			    		            					sommets.get(i).delArete(al.get(j));
			    		            				}
			    		            				if (sommets.get(i).getNbAretes() <1) {
			    		            					sommets.get(i).getDraw().setVisible(false);
			    		            				}
			    		            			}
		            						}
		            					}
		            				}
		            			}
		            			else if (!nim){
		            				arete.getDraw().setVisible(false);
			            			aretes.remove(arete);
			            			for(int i=0; i<sommets.size(); i++) { //suppression de l'arete dans sommets
    		            				if (sommets.get(i).getAretes().contains(arete)) {
    		            					sommets.get(i).delArete(arete);
    		            				}
    		            				if (sommets.get(i).getNbAretes() <1) {
    		            					sommets.get(i).getDraw().setVisible(false);
    		            				}
    		            			}
		            			}
		            			
		            			joueurTour++;
		            			tour();            			
		            		}
		            	}		            	
		            }
			});
		}
		
	}
	
	public void quitterJeu(String gagnant) {
		Alert alert;
		if (gagnant!=null) {
			if(nim) {
				alert = new Alert(AlertType.INFORMATION, "Jeu fini. Gagnant : joueur "+gagnant+". Voulez-vous rejouer ?", ButtonType.YES,ButtonType.NO);
				Optional<ButtonType> result= alert.showAndWait();
				if (result.get() == ButtonType.YES)new Nim(root);
				else Main.getPrimaryStage().close();
			}
			else if (!nim){
				alert = new Alert(AlertType.INFORMATION, "Jeu fini. Gagnant : joueur "+gagnant+".", ButtonType.OK);
				Optional<ButtonType> result= alert.showAndWait();
				if (result.get()==ButtonType.OK)Main.getPrimaryStage().close(); 
			}
		}		
	}
}
