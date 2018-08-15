package interaction;

import java.io.File;

import javax.swing.JDialog;

import evenement.Perso;
import evenement.Pos;
import fenetre.Event_imageIMG;
import zone.Map;
import evenement.EventImg2;
import evenement.EventListe;

@SuppressWarnings("serial")
public class Edit_Image extends Interaction{
	
	private EventImg2 img = null;
	
	@Override
	public String getT() {
		return "Image";
	}

	@Override
	public boolean valide() {
		return this.img != null;
	}

	public void setImage(EventImg2 image) {
		this.img = image;
	}

	@Override
	public Interaction menuOpen(JDialog f) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean exec(Perso p, EventListe e) {
		p.setImg(img);
		return true;
	}

	@Override
	public String toString() {
		return "Modification de l'image en : " + img.getNomT();
	}

}
