package interaction;

import javax.swing.JDialog;

import evenement.EventListe;
import evenement.Perso;

@SuppressWarnings("serial")
public class Attendre extends Interaction{
	
	private double seconde = 0;
	private double time = 0;
	
	@Override
	public String getT() {
		return "Attendre";
	}

	@Override
	public boolean valide() {
		return seconde != 0;
	}

	@Override
	public Interaction menuOpen(JDialog f) {
		// TODO Auto-generated method stub
		return null;
	}

	public double getSeconde() {
		return seconde;
	}

	public void setSeconde(double seconde) {
		this.seconde = seconde;
	}

	@Override
	public boolean exec(Perso p, EventListe e) {
		boolean fini = false;
		if (this.time == 0)
			this.time = System.currentTimeMillis();
		else if (System.currentTimeMillis() - this.time > getSeconde()*1000) {
			fini = true;
		}
		
		return fini;
	}

	@Override
	public String toString() {
		return "Attendre "+this.seconde+" secondes";
	}

}
