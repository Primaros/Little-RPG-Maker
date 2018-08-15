package interaction;

import javax.swing.JDialog;

import evenement.EventListe;
import evenement.Perso;

@SuppressWarnings("serial")
public class Interrupteurs extends Interaction{
	
	private int int_num = -1;
	private boolean int_val = true;
	
	@Override
	public String getT() {
		return "Interrupteur";
	}

	public void set(int i, boolean b){
		this.int_num = i;
		this.int_val = b;
	}
	
	@Override
	public boolean valide() {
		return (int_num >= 0);
	}

	@Override
	public Interaction menuOpen(JDialog f) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean exec(Perso p, EventListe e) {
		if (int_val)
			p.getInterupteurs().get(int_num-1).enable();
		else
			p.getInterupteurs().get(int_num-1).disable();
		return true;
	}

	@Override
	public String toString() {
		return "L'interrupteur "+int_num+" est "+int_val;
	}

	public int getInt_num() {
		return int_num;
	}

	public boolean isInt_val() {
		return int_val;
	}
	
}
