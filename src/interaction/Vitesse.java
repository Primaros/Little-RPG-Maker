package interaction;

import javax.swing.JDialog;

import evenement.EventListe;
import evenement.Perso;

@SuppressWarnings("serial")
public class Vitesse extends Interaction{
	
	private int vitesse;
	public final static int MAX = 12, MIN = 0;
	
	public Vitesse(int v){
		setVitesse(v);
	}
	
	@Override
	public String getT() {
		return "Vitesse";
	}
	
	@Override
	public boolean valide() {
		return (getVitesse() >= MIN && getVitesse() <= MAX);
	}

	@Override
	public Interaction menuOpen(JDialog f) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean exec(Perso p, EventListe e) {
		p.setVitesse(getVitesse());
		return true;
	}

	@Override
	public String toString() {
		return "La vitesse passe ра "+getVitesse();
	}

	public int getVitesse() {
		return vitesse;
	}

	public void setVitesse(int vitesse) {
		this.vitesse = vitesse;
	}

}
