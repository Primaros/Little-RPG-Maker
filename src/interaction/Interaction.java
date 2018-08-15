package interaction;

import java.io.Serializable;

import javax.swing.JDialog;

import edit_variable.Condition;
import evenement.EventListe;
import evenement.Perso;

@SuppressWarnings("serial")
public abstract class Interaction implements Serializable{
	private Condition cond;
	
	public Interaction(){
		this.cond = null;
	}
	
	public abstract String getT();

	public void setCondition(Condition c){
		this.cond = c;
	}
	
	public Condition getCondition(){
		return this.cond;
	}

	public abstract boolean valide();
	
	public abstract Interaction menuOpen(JDialog f);
	
	public abstract boolean exec(Perso p, EventListe e);
	
	public abstract String toString();
	
}
