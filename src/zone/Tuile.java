package zone;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;


@SuppressWarnings("serial")
public class Tuile implements Serializable{
	private BufferedImage img = null;
	private int x,y;
	private int niveaux;
	private boolean solide;
	private String nomTilset;
	
	public Tuile (Tileset s, int x, int y, int h, int w){
		img = s.getImg().getSubimage(x,y,w,h);
		this.init(s.getNom(),x,y,false,1);
	}
	
	public Tuile (Tuile s){
		this.img = s.img;
		this.init(s.nomTilset,s.x,s.y,s.solide,s.niveaux);
	}
	
	public Tuile (Tileset s, int x, int y, int h, int w, int n, boolean so){
		img = s.getImg().getSubimage(x,y,w,h);
		this.init(s.getNom(),x,y,so,n);
	}
	
	public Tuile (String s, int x, int y,int n, boolean so){
		try {
			img = ImageIO.read(new File(s));
		} catch (IOException e) {
			System.out.println("Tile : Chargement du tile impossible");
		}
		this.init(s,x,y,so,n);
	}

	public void init(String s,int x,int y,boolean so,int n){
		this.setX(x);
		this.setY(y);
		this.setNiveaux(n);
		this.setSolide(so);
		this.setNomTilset(s);
	}
	
	public BufferedImage getImage() {
		return img;
	}

	public void setImage(BufferedImage img) {
		this.img = img;
	}

	public int getNiveaux() {
		return niveaux;
	}

	public void setNiveaux(int niveaux) {
		this.niveaux = niveaux;
	}

	public boolean isSolide() {
		return solide;
	}

	public void setSolide(boolean solide) {
		this.solide = solide;
	}

	public String getNomTilset() {
		return nomTilset;
	}

	public void setNomTilset(String nomTilset) {
		this.nomTilset = nomTilset;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}
	
}
