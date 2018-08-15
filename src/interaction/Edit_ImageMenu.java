package interaction;

import java.io.File;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import evenement.Factorie_Perso;
import evenement.Perso;
import fenetre.Event_imageBuild2;

public class Edit_ImageMenu {
	private Edit_Image fini = null;
	private JDialog fenetre;
	
	public Edit_ImageMenu(JDialog f){
		fenetre = f;
	}
	
	public static Edit_Image menuOpen(JDialog f){
		Edit_ImageMenu m = new Edit_ImageMenu(f);
		return Edit_ImageMenu.menu(m,new Edit_Image());
	}
	
	public static Edit_Image menu(Edit_ImageMenu m, Edit_Image tmp){
		
		Perso tmp2 = new Perso();
		m.fenetre.setVisible(true);
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("charset"));
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Images valide", "jpg", "png");
		chooser.setFileFilter(filter);
		m.fini = new Edit_Image();
		if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
			Event_imageBuild2.charSetParam(chooser.getSelectedFile().getPath(), tmp2, m.fenetre);
		}
		m.fini.setImage(tmp2.getImg());
		return m.fini;
	}
	
}
