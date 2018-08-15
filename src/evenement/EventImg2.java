package evenement;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;

import javax.imageio.ImageIO;

import edit.Game;
import outils.InputFiles;
import outils.Pos2;
import zone.Map;

@SuppressWarnings("serial")
public class EventImg2 extends Observable implements Serializable{
	
	public class imageMove implements Serializable {
		private ArrayList<Pos2> posT;
		private ArrayList<String> nomT;
		private ArrayList<Boolean> adapT;
		private transient ArrayList<BufferedImage> img;
		private Pos2 size;
		boolean tac;

		public imageMove(){
			this.size = new Pos2(0,0,Map.TAILLE_TUILE,Map.TAILLE_TUILE);
			this.tac = true;
			posT = new ArrayList<Pos2>();
			nomT = new ArrayList<String>();
			adapT = new ArrayList<Boolean>();
			img = new ArrayList<BufferedImage>();
		}

		public void add(String nom,Pos2 p, boolean adapt){
			this.nomT.add(nom);
			this.posT.add(p);
			this.adapT.add(adapt);
			try {
				addImage(ImageIO.read(new File(nom)).getSubimage(p.x, p.y, p.w, p.h),adapt);
			} catch (IOException e) {
				System.out.println("EventImg : imageMove : ajout image non trouvée (" + nom + ")");
			}
		}

		private void addImage(BufferedImage i,boolean adapt){
			int w = i.getWidth(), h = i.getHeight();
			if (adapt){
				w = size.w;
				h = size.h;
			}
			BufferedImage newI = new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
			Graphics g = newI.getGraphics();
			g.drawImage(i, 0, 0, w, h, null);
			this.img.add(newI);
		}

		public BufferedImage getImg(int num){
			BufferedImage tmp = null;
			if (getSize()>0 && num >= 0){
				if (reversed && getSize() == 3){
					if (num%2!=0)
						tmp = img.get(1);
					else{
						if ((num/2)%2 != 0)
							tmp = img.get(2);
						else
							tmp = img.get(0);
					}
				}else{
					setReversed(false);
					tmp = img.get(num%getSize());
				}
			}

			return tmp;
		}

		public int getSize(){
			return nomT.size();
		}
		
		private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
			stream.defaultReadObject();
			img = new ArrayList<BufferedImage>();
			int nb = posT.size();
			for (int i=0;i<nb;i++){
				this.add(nomT.get(i),posT.get(i), adapT.get(i));
			}
		}

		public void write(ObjectOutputStream f){
			try {
				InputFiles.writeArrayString(f, nomT);
				if(nomT.size()>0){
				InputFiles.writeArrayPos2(f, posT);
				f.writeObject(size);
				f.writeObject(adapT);
				}
			} catch (IOException e1) {
				System.out.println("EventImg : écriture impossible");
				e1.printStackTrace();
			}

		}

		@SuppressWarnings("unchecked")
		public void read(ObjectInputStream f){
			try {
				int nb = f.readInt();
				this.nomT = InputFiles.readArrayString(f,nb);
				if(this.nomT.size()>0){
				nb = f.readInt();
				this.posT = InputFiles.readArrayPos2(f,nb);
				this.size = (Pos2)(f.readObject());
				this.adapT = (ArrayList<Boolean>)f.readObject();
				File file = new File(nomT.get(0));
				this.img.add(ImageIO.read(file).getSubimage(posT.get(0).x, posT.get(0).y, posT.get(0).w, posT.get(0).h));
				for (int i=1;i<posT.size();i++){
					if (nomT.get(i) != file.getName())
						file = new File(nomT.get(i));
					this.img.add(ImageIO.read(file).getSubimage(posT.get(i).x, posT.get(i).y, posT.get(i).w, posT.get(i).h));
				}}
			} catch (IOException e) {
				System.out.println("EventImg2 : lecture impossible");
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				System.out.println("EventImg2 : lecture impossible (size non trouvé)");
				e.printStackTrace();
			}

		}

	}

	private imageMove img_d,img_g,img_h,img_b;
	private boolean stop;
	private Pos actuelle;
	private String nomT;
	private double time;
	protected boolean reversed;

	public static final int HAUT = 3, BAS = 0, GAUCHE = 1, DROITE = 2;
	
	public EventImg2(String n){
		this.init();
		this.nomT = n;
		this.stop = false;
		this.reversed = false;
		img_d = new imageMove();
		img_g = new imageMove();
		img_h = new imageMove();
		img_b = new imageMove();
	}

	public void init(){
		actuelle = new Pos();
		this.time = 0;
	}

	public void actualiser(){
		if(!this.stop && System.currentTimeMillis() - time >= Game.UPDATE*10 ){
			actuelle.x ++;
			setChanged();
			notifyObservers();
			time = System.currentTimeMillis();
		}
	}

	public void lookAt(int direction){
		if(this.lookWhere()!=direction){
			actuelle.y = direction;
			actuelle.x = 0;
			setChanged();
			notifyObservers();
		}
	}

	public int lookWhere(){
		return actuelle.getY();
	}

	public void stop(){
		actuelle.x = 0;
		this.stop =true;
		setChanged();
		notifyObservers();
	}

	public void play(){
		this.stop =false;
		setChanged();
		notifyObservers();
	}

	public boolean isReversed() {
		return reversed;
	}

	public void setReversed(boolean reversed) {
		this.reversed = reversed;
		setChanged();
		notifyObservers();
	}

	public BufferedImage getImg(int d,int p) {
		BufferedImage result = null;
		switch (d){
		case HAUT:
			result = img_h.getImg(p);
			break;
		case BAS:
			result = img_b.getImg(p);
			break;
		case GAUCHE:
			result = img_g.getImg(p);
			break;
		case DROITE:
			result = img_d.getImg(p);
			break;
		}
		if (result == null){
			result = new BufferedImage(img_h.size.w, img_h.size.h, BufferedImage.TYPE_INT_ARGB);
			Graphics g = result.getGraphics();
			g.drawRect(10, 10, 10, 10);
		}
		return result;
	}

	public BufferedImage getImg() {
		return getImg(lookWhere(),actuelle.getX());
	}

	public void addImg(int direction, Pos2 img, boolean redim) {
		switch (direction){
		case HAUT:
			img_h.add(getNomT(),img,redim);
			break;
		case BAS:
			img_b.add(getNomT(),img,redim);
			break;
		case GAUCHE:
			img_g.add(getNomT(),img,redim);
			break;
		case DROITE:
			img_d.add(getNomT(),img,redim);
			break;
		}
		setChanged();
		notifyObservers();
	}

	public String getNomT(){
		return this.nomT;
	}

	public void setNomT( String n){
		this.nomT = n;
		setChanged();
		notifyObservers();
	}

	public int getSize(int d){
		int tmp = 0;
		switch (d){
		case HAUT:
			tmp = img_h.getSize();
			break;
		case BAS:
			tmp = img_b.getSize();
			break;
		case GAUCHE:
			tmp = img_g.getSize();
			break;
		case DROITE:
			tmp = img_d.getSize();
			break;
		}
		return tmp;
	}

	public void write(ObjectOutputStream f){
		try {
			f.writeUTF(nomT);
			if(nomT != "empty"){
			f.writeBoolean(reversed);
			img_d.write(f);
			img_g.write(f);
			img_h.write(f);
			img_b.write(f);
			}
		} catch (IOException e) {
			System.out.println("EventImg: write error");
			e.printStackTrace();
		}

	}

	public void read(ObjectInputStream f){
		try {
			this.nomT=(String)f.readUTF();
			if(nomT != "empty"){
				this.reversed=(Boolean)f.readBoolean();
				img_d.read(f);
				img_g.read(f);
				img_h.read(f);
				img_b.read(f);
			}
		} catch (IOException e) {
			System.out.println("EventImg: read error");
			e.printStackTrace();
		}

	}

	public BufferedImage getThumb(){
		BufferedImage tmp = null;
		if (this.img_b.getImg(0) != null)
			tmp = this.img_b.getImg(0);
		else if (this.img_g.getImg(0) != null)
			tmp = this.img_g.getImg(0);
		else if (this.img_d.getImg(0) != null)
			tmp = this.img_d.getImg(0);
		else if (this.img_h.getImg(0) != null)
			tmp = this.img_h.getImg(0);

		return tmp;
	}
	
}
