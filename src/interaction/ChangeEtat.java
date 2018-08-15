package interaction;

import java.io.Serializable;

import javax.swing.JDialog;

import evenement.EventListe;
import evenement.Perso;

@SuppressWarnings("serial")
public class ChangeEtat extends Interaction{
	
	private boolean etat_bool;
	private Etat etat;
	
	public final Etat ANIME = new Anime(), BOUGE = new Bouge(), SOLIDE = new Solide(), VISIBLE = new Visible();
	
	@Override
	public String getT() {
		return "ChangeEtat";
	}

	public void set(Etat e, boolean b){
		this.etat = e;
		this.etat_bool = b;
	}
	
	public boolean isEtat_bool() {
		return etat_bool;
	}

	public Etat getEtat() {
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
		etat.exec(p, etat_bool);
		return true;
	}

	@Override
	public String toString() {
		return etat+" est "+etat_bool;
	}
	
	
	private abstract class Etat implements Serializable{
		public abstract void exec(Perso p, boolean e);
		public abstract String toString();
	}
	
	private class Anime extends Etat{
		@Override
		public void exec(Perso p, boolean e) {
			p.setAnime(e);
		}
		public String toString(){
			return "Animé";
		}
	}
	
	private class Bouge extends Etat{
		@Override
		public void exec(Perso p, boolean e) {
			p.setBouge(e);
		}
		public String toString(){
			return "Bouge";
		}
	}
	
	private class Solide extends Etat{
		@Override
		public void exec(Perso p, boolean e) {
			p.setSolide(e);
		}
		public String toString(){
			return "Solide";
		}
	}
	
	private class Visible extends Etat{
		@Override
		public void exec(Perso p, boolean e) {
			p.setVisible(e);
		}
		public String toString(){
			return "Visible";
		}
	}

}
