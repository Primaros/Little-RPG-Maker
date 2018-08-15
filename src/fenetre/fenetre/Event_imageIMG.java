package fenetre;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import evenement.EventImg2;
import evenement.EventX;
import evenement.Pos;

@SuppressWarnings("serial")
public class Event_imageIMG extends JPanel{
	public BufferedImage img;
	private Pos emplacement;
	private Pos taille;
	
	public Event_imageIMG(String chemin){
		try {
			this.img = ImageIO.read(new File(chemin));
		} catch (IOException e) {
			System.out.println("Event_imageIMG: Fichier introuvable ("+chemin+")");
			e.printStackTrace();
		}
		this.emplacement = new Pos();
		this.taille = new Pos(EventX.TAILLE,EventX.TAILLE);
		this.addMouseListener(new MouseAdapter() {
			
			public void mousePressed(MouseEvent arg0) {
				Event_imageIMG tmp = (Event_imageIMG)(arg0.getSource());
				if(!(arg0.getX()>=tmp.getWidth() || arg0.getX()<=0 || arg0.getY()<=0 || arg0.getY()>=tmp.getHeight()))
					tmp.setEmplacement(new Pos(arg0.getX()/(tmp.getTaille().getX()),arg0.getY()/(tmp.getTaille().getY())));
				tmp.repaint();
			}
			
		});
	}
	
	public int getWidth(){
		return this.img.getWidth();
	}
	
	public int getHeight(){
		return this.img.getHeight();
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(this.doubleBuffer(), 0, 0, null);
	}
	
	public Image doubleBuffer(){
		Graphics offgc;
	    Image buff = createImage(this.img.getWidth(), this.img.getHeight());
	    offgc = buff.getGraphics();
	    
	    offgc.drawImage(this.img, 0, 0, null);
	    offgc.setColor(Color.RED);
	    	offgc.drawRect(this.emplacement.getX()*this.taille.getX(), this.emplacement.getY()*this.taille.getY(), this.taille.getX(), this.taille.getY());
	    
	    return buff;
	}

	public Pos getEmplacement() {
		return emplacement;
	}

	public void setEmplacement(Pos emplacement) {
		this.emplacement = emplacement;
	}

	public Pos getTaille() {
		return taille;
	}

	public void setTaille(Pos taille) {
		this.taille = taille;
	}

}
