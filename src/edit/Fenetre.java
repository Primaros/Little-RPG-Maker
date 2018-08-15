package edit;

import javax.swing.JDialog;

@SuppressWarnings("serial")
public class Fenetre extends JDialog {

	  public Fenetre(String nom,int x, int y){

	    this.setTitle(nom);

	    this.setSize(x, y);

	    this.setLocationRelativeTo(null);

	    this.setVisible(true);

	  }

}
