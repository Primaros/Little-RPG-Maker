package edit;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.*;

import zone.Zone;


public class Game implements Runnable{
	
	JFrame fenetre;
	Zone carte;
	boolean running = true;
	public final static int UPDATE = 40;
	
	public static void launch(){
		Game g = new Game();
		g.init();
		new Thread(g).start();
	}
	
	public void init(){
		carte = new Zone();
		carte.charger("C:"+File.separator+"Users"+File.separator+"Tony"+File.separator+"workspace"+File.separator+"RPG Maker"+File.separator+"save"+File.separator+"map"+File.separator+"Nouvelle Zone.zone");
		
		fenetre = new JFrame("Game");
		fenetre.setAlwaysOnTop(true);
//		fenetre.setLocationRelativeTo(null);
		fenetre.setContentPane(carte);
	}

	@Override
	public void run() {
		fenetre.pack();
		fenetre.setVisible(true);
		fenetre.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				running = false;
		    }
		});
		carte.requestFocusInWindow();
		
		while(running){
			carte.repaint();
			carte.actualiser();
			try {
				Thread.sleep(Game.UPDATE);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
