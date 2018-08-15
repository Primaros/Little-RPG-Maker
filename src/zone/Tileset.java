package zone;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.channels.FileChannel;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import evenement.EventX;


@SuppressWarnings("serial")
public class Tileset extends JPanel implements Serializable{
	private BufferedImage img;
	private String nom;

	public Tileset(){
		BufferedImage imageNoir = new BufferedImage(EventX.TAILLE*4, EventX.TAILLE*4, BufferedImage.TYPE_INT_ARGB);
		Graphics g = imageNoir.getGraphics();
		g.setColor(Color.WHITE);
		g.drawRect(0, 0, imageNoir.getWidth(null), imageNoir.getHeight(null));
		this.setImg(imageNoir);
		this.nom = "vide";
	}

	public Tileset(String fichier){
		this.nom = fichier.split(Pattern.quote(File.separator))[fichier.split(Pattern.quote(File.separator)).length-1];
		this.init();
	}
	
	public Tileset(String fichier, int i){
		this.nom = fichier.split(Pattern.quote(File.separator))[fichier.split(Pattern.quote(File.separator)).length-1];
		this.initChar();
	}

	public BufferedImage getImg() {
		return img;
	}

	public void setImgCharger(String img) {
		FileChannel in = null, out = null;
		try {
			this.nom = img.split(File.separator)[img.split(File.separator).length-1];
			FileInputStream fileInputStream = new FileInputStream(img);
			FileOutputStream fileOutputStream = new FileOutputStream("tileset"+File.separator+this.nom);
			in = fileInputStream.getChannel();
			out = fileOutputStream.getChannel();
			in.transferTo(0, in.size(), out);
			fileInputStream.close();
			fileOutputStream.close();
		} catch (FileNotFoundException e) {
			System.out.println("Tileset : Chargement du tilset impossible : le fichier est introuvable");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Tileset : Chargement du tilset impossible : la copie du fichier n'a pas march�e");
			e.printStackTrace();
		}
		
		
		this.init();
	}

	private void init(){
		this.setImg(null);
		try {
			this.setImg(ImageIO.read(new File("tileset"+File.separator+this.nom)));
		} catch (IOException e) {
			System.out.println("Tileset : Chargement du tilset impossible ("+this.nom+")");
		}
	}
	
	private void initChar(){
		this.setImg(null);
		try {
			this.setImg(ImageIO.read(new File("charset"+File.separator+this.nom)));
		} catch (IOException e) {
			System.out.println("Tileset : Chargement du tilset impossible ("+this.nom+")");
		}
	}

	public void setImg(BufferedImage img) {
		this.img = img;
	}

	public String getNom(){
		return this.nom;
	}
}
