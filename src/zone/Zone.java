package zone;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JPanel;

import camera.Camera;
import evenement.EventListe;
import evenement.PersoUpdater;
import zone.Map;

@SuppressWarnings("serial")
public class Zone extends JPanel implements Serializable{
 private Map map;
 private EventListe events;
 private String nom;
 private int ecranr_x;
 private int ecranr_y;
 private GestionTouches gTouches;
 private boolean fullMap;
	
 public Zone(){
		this.init();
}
 
 public Zone(String n){
		this.init();
		this.charger(n);
}
 
 public Map getMap(){
	 return this.map;
 }
 
 public EventListe getEvent(){
	 return this.events;
 }
 
 private void init(){
	 	this.setFullMap(false);
	    this.ecranr_x = 0;
		this.ecranr_y = 0;
		this.map = new Map();
	 	this.events = new EventListe();
		this.nom = "Nouvelle Zone";
		this.gTouches = new GestionTouches();
		this.addKeyListener(gTouches);
		this.setPreferredSize(new Dimension(Map.NB_TUILE_X*Map.TAILLE_TUILE,Map.NB_TUILE_Y*Map.TAILLE_TUILE));
		this.requestFocusInWindow();
		this.setDoubleBuffered(true);
 }
 
 public String getNom() {
	 	return nom;
 }

 public void setNom(String nom) {
	 	this.nom = nom;
 }
	
 public String toString(){
		return this.getNom();
}
 
 public void setPositionX(int x){
		if (x>=0&&(x + this.getPositionX() + Map.NB_TUILE_X<= Map.NB_TUILE_TOTAL_X))
			this.ecranr_x = x;
 }

 public void setPositionY(int y){
		if (y>=0&&(y + this.getPositionY() + Map.NB_TUILE_Y<= Map.NB_TUILE_TOTAL_Y))
			this.ecranr_y = y;
 }

 public int getPositionX(){
//		return this.ecranr_x;
	 return Camera.getPos().getX();
 }

 public int getPositionY(){
//		return this.ecranr_y;
	 return Camera.getPos().getY();
 }
 
 public void setTile(int x, int y,Tuile t){
		this.getMap().setTile(x,y,t);
 }

public Tuile getTile(int y, int x,int z){
		return this.getMap().getTile(y, x, z);
 }
 
 public void actualiser(){
		for (int i = 0; i<events.size();i++){
			PersoUpdater.update(events.get(i), this);
		}
 }

 public void charger(String n){
	 this.removeKeyListener(gTouches);
	 this.init();
	 String tmp = File.separator;
	 this.nom = n.split("\\"+tmp)[n.split("\\"+tmp).length-1];
	 this.nom = this.nom.split("\\.")[0];
	 this.setNom(this.nom);
	 try {
			ObjectInputStream file= new ObjectInputStream( new FileInputStream(n));
			this.ecranr_x = file.readInt();
			this.ecranr_y = file.readInt();
			this.map.charger(file);
			file.close();
			ObjectInputStream file2= new ObjectInputStream( new FileInputStream("save"+File.separator+"event"+File.separator+this.getNom()+".zone"));
			this.events.charger(file2);
			file2.close();
	 } catch (IOException e1) {
			System.out.println("Zone : Le chargement de la zone n'a pu s'effectuer");
			e1.printStackTrace();
	 }
 }
 
 public void sauvgarder(String n){
	 try {
			ObjectOutputStream file;
			file = new ObjectOutputStream( new FileOutputStream("save"+File.separator+"map"+File.separator+n+".zone"));
			ObjectOutputStream file2;
			file2 = new ObjectOutputStream( new FileOutputStream("save"+File.separator+"event"+File.separator+n+".zone"));
			file.writeInt(this.ecranr_x);
			file.writeInt(this.ecranr_y);
			this.map.sauvgarder(file);
			this.events.save(file2);
			file.writeUTF(this.nom);
			file.close();
			file2.close();
	 } catch (IOException e) {
		 System.out.println("Zone : La sauvegarde de la zone n'a pu s'effectuer");
			e.printStackTrace();
		}
 }
 
 public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(this.doubleBuffer(), 0, 0, null);
	}

public Image doubleBuffer(){
		Graphics offgc;
		Image buff = createImage(this.getWidth(), this.getHeight());
		offgc = buff.getGraphics();
		// clear the exposed area
		offgc.setColor(Color.WHITE);
		offgc.clearRect(0, 0, this.getWidth(), this.getHeight());

		int a=0, b=0, tailleY=Map.NB_TUILE_TOTAL_Y, tailleX=Map.NB_TUILE_TOTAL_X;
		
		if(!isFullMap()){
			tailleY = Map.NB_TUILE_Y+1;
			tailleX = Map.NB_TUILE_X+1;
			a = this.getPositionX()/Map.TAILLE_TUILE;
			b = this.getPositionY()/Map.TAILLE_TUILE;
		}
		
		for (int y = 0; y < tailleY; y++){
			for (int x=0; x < tailleX; x++){
				offgc.setColor(Color.BLUE);
				try {
					for(int i=0;i<2;i++){
						if (this.map.getTile(b+y, a+x, i) != null)
							offgc.drawImage(this.map.getTile(b+y, a+x, i).getImage(), x*(Map.TAILLE_TUILE)-this.getPositionX()%Map.TAILLE_TUILE, y*(Map.TAILLE_TUILE)-this.getPositionY()%Map.TAILLE_TUILE, this);
//						offgc.drawImage(this.map.getTile(y, x, i).getImage(), posX, posY, this.getWidth()/Map.NB_TUILE_X, this.getHeight()/Map.NB_TUILE_Y, this);
					}
					
				} catch (Exception e) {
					// Comme on charge une tile en plus, aux bord du tableau on bugg. Suffit de l'ignorer car elle ne sera jamais vue
				}
			}
		}
		
		for (int i = 0; i<events.size();i++){
			if (events.get(i).isVisible())
				events.get(i).paintComponent(offgc);
		}
		
		for (int y = 0; y<tailleY ; y++){
			for (int x=0; x <tailleX; x++){
				offgc.setColor(Color.BLUE);
				try {
					for(int i=2;i<4;i++){
						if (this.map.getTile(b+y, a+x, i) != null)
							offgc.drawImage(this.map.getTile(b+y, a+x, i).getImage(), x*(Map.TAILLE_TUILE)-this.getPositionX()%Map.TAILLE_TUILE, y*(Map.TAILLE_TUILE)-this.getPositionY()%Map.TAILLE_TUILE, this);
					}
					
				} catch (Exception e) {
					// Comme on charge une tile en plus, aux bord du tableau on bugg. Suffit de l'ignorer car elle ne sera jamais vue
				}
			}
		}

		return buff;
	}

public boolean isFullMap() {
	return fullMap;
}

public void setFullMap(boolean fullMap) {
	this.fullMap = fullMap;
}

	
}
