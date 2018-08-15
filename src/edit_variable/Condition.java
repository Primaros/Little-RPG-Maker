package edit_variable;

import java.io.Serializable;

import javax.swing.JDialog;

import evenement.EventListe;
import evenement.Perso;
import interaction.Interaction;

@SuppressWarnings("serial")
public abstract class Condition extends Interaction implements Serializable{
	public static int NB_CONDITION = 0;
	private int num;
	protected boolean validate;
	
	public Condition(){
		Condition.NB_CONDITION+=1;
		this.setNum(Condition.NB_CONDITION);
		this.validate = false;
	}
	
	public boolean isValidate(EventListe l){
		boolean tmp = false;
		if ((this.getCondition()==null || this.getCondition().isValidate(l)) && this.verif(l)){
			tmp=true;
		}
		return tmp;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}
	
	public abstract boolean verif(EventListe l);
	
	public String toString(){
		return "[" + this.getNum() + "] ";
	}

	@Override
	public boolean valide() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Interaction menuOpen(JDialog f) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean exec(Perso p, EventListe e) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getT() {
		return "Condition";
	}
}
