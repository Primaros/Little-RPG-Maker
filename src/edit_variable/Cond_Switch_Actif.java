package edit_variable;

import evenement.EventListe;

@SuppressWarnings("serial")
public class Cond_Switch_Actif extends Cond_Switch{
	
	public Cond_Switch_Actif(Switch selectedItem) {
		super(selectedItem);
	}

	@Override
	public boolean verif(EventListe l) {
		if (this.getI().isEnable())
			return true;
		else
			return false;
	}

	@Override
	public String toString() {
		return super.toString()+ " est activer";
	}

}
