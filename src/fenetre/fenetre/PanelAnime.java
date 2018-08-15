package fenetre;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import edit.Game;
import evenement.EventImg2;
import zone.Map;

@SuppressWarnings("serial")
public class PanelAnime extends JPanel{
	EventImg2 image;
	dessinerPersoActu j1;
	actualiserPerso t1;
	persoFil p1;
	
	public class actualiserPerso extends Thread {
		double time = 0;
		public void run(){
				while(!Thread.currentThread().isInterrupted()){
					image.actualiser();
				try {
					Thread.sleep(Game.UPDATE);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
		}
	}
	
	public class dessinerPersoActu extends JPanel implements Observer{
		public dessinerPersoActu (){
			this.setPreferredSize(new Dimension(Map.TAILLE_TUILE+2, Map.TAILLE_TUILE+2));
			image.addObserver(this);
		}
		public void paintComponent(Graphics g){
			g.clearRect(0, 0, this.getWidth(), this.getHeight());
			g.drawLine(0, 0, this.getWidth()-1, 0);
			g.drawLine(0, this.getHeight()-1, this.getWidth()-1, this.getHeight()-1);
			g.drawLine(0, 0, 0, this.getHeight()-1);
			g.drawLine(this.getWidth()-1, 0, this.getWidth()-1, this.getHeight()-1);
			g.drawImage(image.getImg(), 1, 1, null);
		}
		@Override
		public void update(Observable o, Object arg) {
			repaint();
		}
	}
	
	public class persoFil extends JPanel implements Observer{
		public persoFil(){
			this.setPreferredSize(new Dimension((Map.TAILLE_TUILE+1)*10+1, Map.TAILLE_TUILE+2));
			image.addObserver(this);
		}
		
		public void paintComponent(Graphics g){
			g.clearRect(0, 0, this.getWidth()-1, this.getHeight()-1);
			g.setColor(Color.BLACK);
			g.drawLine(0, 0, this.getWidth()-1, 0);
			g.drawLine(0, this.getHeight()-1, this.getWidth()-1, this.getHeight()-1);
			g.drawLine(0, 0, 0, this.getHeight()-1);
			g.drawLine(this.getWidth()-1, 0, this.getWidth()-1, this.getHeight()-1);
			int x = 1;
			for (int i = 0; i < image.getSize(image.lookWhere());i++){
				int w = image.getImg(image.lookWhere(),i).getWidth();
				int h = image.getImg().getHeight();
				if (h > 32){
					w = (int)(w * h/32.0);
					h = 32;
				}
				g.drawImage(image.getImg(image.lookWhere(),i), x, 1, w, h, null);
				x += image.getImg(image.lookWhere(),i).getWidth();
				g.drawLine(x, 1, x, this.getHeight()-2);
				x++;
			}
		}

		@Override
		public void update(Observable arg0, Object arg1) {
			repaint();
		}
	}
	
	public PanelAnime(EventImg2 e){
		this.image = e;
		
		j1 = new dessinerPersoActu();
		t1 = new actualiserPerso();
		p1 = new persoFil();
		
		t1.start();
		
		JPanel tmp = new JPanel();
		tmp.add(p1);
		JPanel tmp2 = new JPanel();
		tmp2.add(j1);
		this.setPreferredSize(new Dimension(10, 50));
		this.setLayout(new BorderLayout());
		this.add(tmp2, BorderLayout.EAST);
		this.add(tmp, BorderLayout.CENTER);
	}
	
	public void stop(){
		actualiserPerso.interrupted();
	}
	
}
