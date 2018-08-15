package interaction;

import java.io.Serializable;

import javax.swing.JDialog;

import evenement.EventImg2;
import evenement.EventListe;
import evenement.Perso;

@SuppressWarnings("serial")
public class ChangeRegard extends Interaction{
	
	private Regard etat;
	
	public final Regard HAUT = new Haut(), BAS = new Bas(), GAUCHE = new Gauche(), DROITE = new Droite();
	
	@Override
	public String getT() {
		return "ChangeRegard";
	}

	public void set(Regard e){
		this.etat = e;
	}

	public Regard get() {
		return etat;
	}

	@Override
	public boolean valide() {
		return etat != null;
	}

	@Override
	public Interaction menuOpen(JDialog f) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean exec(Perso p, EventListe e) {
		etat.exec(p);
		return true;
	}

	@Override
	public String toString() {
		return " Regarder vers "+etat;
	}
	
	
	private abstract class Regard implements Serializable{
		public abstract void exec(Perso p);
		public abstract String toString();
	}
	
	private class Haut extends Regard{
		@Override
		public void exec(Perso p) {
			p.getImg().lookAt(EventImg2.HAUT);
		}
		public String toString(){
			return "le Haut";
		}
	}
	
	private class Bas extends Regard{
		@Override
		public void exec(Perso p) {
			p.getImg().lookAt(EventImg2.BAS);
		}
		public String toString(){
			return "le Bas";
		}
	}
	
	private class Gauche extends Regard{
		@Override
		public void exec(Perso p) {
			p.getImg().lookAt(EventImg2.GAUCHE);
		}
		public String toString(){
			return "la Gauche";
		}
	}
	
	private class Droite extends Regard{
		@Override
		public void exec(Perso p) {
			p.getImg().lookAt(EventImg2.DROITE);
		}
		public String toString(){
			return "la Droite";
		}
	}

}
