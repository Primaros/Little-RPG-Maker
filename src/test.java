
import java.io.File;

import javax.swing.JDialog;

import evenement.EventListe;
import evenement.Factorie_Perso;
import evenement.Perso;
import fenetre.Event_imageBuild2;
import fenetre.MenuInteraction;




public class test {
	public static void main(String[] args){
		
//            try {
//				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
//				e.printStackTrace();
//			}
            EventListe tmp = new EventListe();
            tmp.createPerso();
            tmp.createPerso();
            MenuInteraction m = new MenuInteraction(new Factorie_Perso(tmp));
//            m.openMenu();
//	        Event_imageBuild2 f = new Event_imageBuild2("C:"+File.separator+"Users"+File.separator+"Tony"+File.separator+"workspace"+File.separator+"RPG Maker"+File.separator+"charset"+File.separator+"$Actor4.png", (Perso)(tmp.get(0)));
//	        f.showWindow(new JDialog());
//	        Cond_Position test = Cond_Position_Menu.openMenu(t,tmp);
           
//            System.out.println(test.isValidate());
	}
}
