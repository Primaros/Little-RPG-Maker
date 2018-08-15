package edit_variable;

import evenement.EventListe;

@SuppressWarnings("serial")
public class Cond_Switch_InActif extends Cond_Switch{
	
	public Cond_Switch_InActif(Switch selectedItem) {
		super(selectedItem);
	}
	
	@Override
	public boolean verif(EventListe l) {
		if (this.getI().isEnable())
			return false;
		else
			return true;
	}

	@Override
	public String toString() {
		return super.toString()+ " est d�sactiver";
	}
}