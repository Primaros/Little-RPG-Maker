package interaction;

import evenement.EventListe;
import evenement.Perso;

@SuppressWarnings("serial")
public class Interrupteurs_G extends Interrupteurs{
	
	@Override
	public String getT() {
		return "Interrupteur G�n�rale";
	}

	@Override
	public boolean exec(Perso p, EventListe e) {
		if (isInt_val())
			e.getSwitchG().get(getInt_num()-1).enable();
		else
			e.getSwitchG().get(getInt_num()-1).disable();
		
		return true;
	}

	@Override
	public String toString() {
		return "L'interrupteur g�n�rale "+getInt_num()+" est "+isInt_val();
	}
	
}
