package interaction;

import javax.swing.JDialog;

import evenement.EventListe;
import evenement.Perso;

@SuppressWarnings("serial")
public class Deplacement extends Interaction {

	public static final int INCREMENT = 0;
	public static final int MAP = 1;

	private int type;
	private int x;
	private int y;
	
	public Deplacement(int x, int y){
		this.type = Deplacement.INCREMENT;
		this.x = x;
		this.y = y;
	}
	
	public Deplacement(int x, int y, int type){
		if (type == Deplacement.MAP)
			this.type = Deplacement.MAP;
		else 
			this.type = Deplacement.INCREMENT;
		this.x = x;
		this.y = y;
	}
	
	public boolean valide(){
		if(this.x!=0||this.y!=0)
			return true;
		else
			return false;
	}
	
	public boolean exec(Perso p, EventListe e) {
		if (this.type == Deplacement.INCREMENT)
			p.addMove(this.x, this.y);
		else if (this.type == Deplacement.MAP)
			p.addMoveM(this.x, this.y);
		
		return true;
	}

	public String toString(){
		String tmp = "Déplacement ";
		if(this.type == Deplacement.MAP)
			tmp += "vers la case ";
		else
			tmp+= "de ";
		tmp+="("+this.x+";"+this.y+")";
		return tmp;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	@Override
	public Interaction menuOpen(JDialog f) {
		return DeplacementMenu.menuOpen(f,this);
	}

	@Override
	public String getT() {
		return "Deplacement";
	}

	
}
