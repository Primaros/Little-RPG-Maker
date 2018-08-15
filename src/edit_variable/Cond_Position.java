package edit_variable;

import java.io.Serializable;

import evenement.EventListe;
import evenement.Perso;
import evenement.Pos;
import zone.Map;

@SuppressWarnings("serial")
public class Cond_Position extends Condition implements Serializable{

	protected int num_pers;
	protected String nom_pers;
	protected Pos posi;
	
	public Cond_Position(Perso p, int num, Pos posi){
		this.nom_pers = p.getNom();
		this.num_pers = num;
		this.posi = posi;
	}
	
	@Override
	public boolean verif(EventListe l) {
		if (l.get(this.num_pers).getPos().getX() == posi.getX() && l.get(this.num_pers).getPos().getY() == posi.getY())
			return true;
		return false;
	}
	
	public String toString(){
		return super.toString() + "L'event " + this.nom_pers + " est en (" + posi.getX()/Map.TAILLE_TUILE+ "," + posi.getY()/Map.TAILLE_TUILE + ")";
	}
	
}
