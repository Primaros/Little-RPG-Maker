package interaction;

import javax.swing.JDialog;

import camera.Camera;
import evenement.EventListe;
import evenement.Perso;

@SuppressWarnings("serial")
public class Camera_Int extends Interaction{
	
	@Override
	public String getT() {
		return "Caméra";
	}

	@Override
	public boolean valide() {
		return true;
	}

	@Override
	public Interaction menuOpen(JDialog f) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean exec(Perso p, EventListe e) {
		Camera.setPerso(p);
		return true;
	}

	@Override
	public String toString() {
		return "Caméra focus";
	}

}
