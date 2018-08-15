package evenement;

import java.awt.Graphics;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Observable;

public abstract class EventX extends Observable{
	
	private Pos pos;
	private boolean solide, bouge, anime, visible;
	private EventImg2 img;
	private String nom,numerot;
	
	public static final int TAILLE = 32;
	
	public static final int HAUT = 3;
	public static final int BAS = 0;
	public static final int GAUCHE = 1;
	public static final int DROITE = 2;
	
	public static final int PLAN = 1;

	protected EventX(){
		this.pos = new Pos(0,0);
		this.solide = true;
		this.visible = true;
		this.setImg(new EventImg2("empty"));
		this.setBouge(true);
		this.setAnime(true);
	}
	
	public void maj(){
		this.setChanged();
		this.notifyObservers();
	}

	public Pos getPos() {
		return this.pos;
	}
	
	public void setPos(Pos i) {
		this.pos = i;
		this.maj();
	}

	public boolean isSolide() {
		return solide;
	}

	public void setSolide(boolean solide) {
		this.solide = solide;
		this.maj();
	}
	
	public abstract void paintComponent(Graphics g);
	public abstract void write(ObjectOutputStream file)throws IOException;
	
	public EventImg2 getImg() {
		return img;
	}

	public void setImg(EventImg2 img) {
		this.img = img;
		this.maj();
	}

	public boolean isAnime() {
		return anime;
	}

	public void setAnime(boolean anime) {
		this.anime = anime;
		this.maj();
	}

	public boolean isBouge() {
		return bouge;
	}

	public void setBouge(boolean bouge) {
		this.bouge = bouge;
		this.maj();
	}

	public String getNom() {
		return nom;
	}
	
	public String getNumerot(){
		return numerot;
	}
	
	public void setNumerot(String n){
		numerot = n;
	}

	public void setNom(String nom) {
		this.nom = nom;
		if(this.numerot == null)
			this.numerot = nom;
		this.maj();
	}
	
	public String toString() {
		return this.nom;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
}
