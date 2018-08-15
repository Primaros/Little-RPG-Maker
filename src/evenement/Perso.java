package evenement;

import interaction.*;
import outils.InputFiles;

import java.awt.Graphics;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import camera.Camera;
import edit_variable.Switch;


public class Perso extends EventX{
	protected int vitesse;
	protected ArrayList<Pos> deplacements;
	protected ArrayList<Interaction> actions;
	protected ArrayList<Switch> interupteurs;
	
	public void setInterupteurs(ArrayList<Switch> interupteurs) {
		this.interupteurs = interupteurs;
		this.maj();
	}

	public Perso(){
		super();
		this.init();
	}

	public ArrayList<Interaction> getInteraction(){
		ArrayList<Interaction> tmp = new ArrayList<Interaction>();
		for(int i=0; i<actions.size();i++){
			if (!actions.get(i).getT().equals("Condition"))
				tmp.add(actions.get(i));
		}
		return tmp;
	}
	
	public ArrayList<Object> getPersoList(){
		ArrayList<Object> tmp = new ArrayList<Object>();
		tmp.add(vitesse);
		tmp.add(new ArrayList<Pos>(deplacements));
		tmp.add(new ArrayList<Interaction>(actions));
		tmp.add(new ArrayList<Switch>(interupteurs));
		tmp.add(new Pos(getPos().getX(),getPos().getY()));
		tmp.add(isBouge());
		tmp.add(isAnime());
		tmp.add(isSolide());
		tmp.add(isVisible());
		tmp.add(getNom());
		tmp.add(getNumerot());
		tmp.add(this.getImg());
		return tmp;
	}
	
	@SuppressWarnings({ "unchecked" })
	public void setPersoList(ArrayList<Object> tmp){
		setVitesse((int)tmp.get(1));
		deplacements = (ArrayList<Pos>)tmp.get(2);
		actions = (ArrayList<Interaction>)tmp.get(3);
		interupteurs = (ArrayList<Switch>)tmp.get(4);
		setPos((Pos)tmp.get(5));
		setBouge((boolean)tmp.get(6));
		setAnime((boolean)tmp.get(7));
		setSolide((boolean)tmp.get(8));
		setVisible((boolean)tmp.get(9));
		setNom((String)tmp.get(10));
		this.setNumerot((String)tmp.get(11));
		this.setImg((EventImg2)tmp.get(12));
		this.maj();
	}

	public void init(){
		this.vitesse = 4;
		this.deplacements = new ArrayList<Pos>();
		this.actions = new ArrayList<Interaction>();
		this.interupteurs = new ArrayList<Switch>();
		this.setSolide(true);
		this.setBouge(true);
		this.setAnime(true);
		this.maj();
	}
	
	public ArrayList<Switch> getInterupteurs() {
		return interupteurs;
	}

	public void addInteraction(Interaction i){
		if (i != null){
			this.actions.add(i);
			this.maj();
		}
	}
	
	public void addMove(int x, int y){
		x = x*32;
		y = y*32;
		this.deplacements.add(new Pos(x,y));
		this.maj();
	}
	
	public void addMoveM(int x, int y){
		x = this.getPos().getX()-(x*32);
		y = this.getPos().getY()-(y*32);
		this.deplacements.add(new Pos(x,y));
		this.maj();
	}

	public void paintComponent(Graphics g){
		g.drawImage(this.getImg().getImg(), this.getPos().getX()-Camera.getPos().getX(), this.getPos().getY()-Camera.getPos().getY(), null);
	}

	public ArrayList<Interaction> getActions() {
		return actions;
	}

	public void write(ObjectOutputStream file)throws IOException{
		file.writeInt(vitesse);
		file.writeBoolean(isBouge());
		file.writeBoolean(isAnime());
		file.writeBoolean(isSolide());
		file.writeBoolean(isVisible());
		file.writeUTF(getNom());
		file.writeUTF(getNumerot());
		InputFiles.writeArrayPos(file,deplacements);
		file.writeInt(actions.size());
		for(int i=0;i<actions.size();i++)
			file.writeObject(actions.get(i));
		file.writeInt(interupteurs.size());
		for(int i=0;i<interupteurs.size();i++)
			file.writeObject(interupteurs.get(i));
		file.writeObject(new Pos(getPos().getX(),getPos().getY()));
		this.getImg().write(file);
	}
	
	public static Perso read(ObjectInputStream file) throws IOException {
		Perso tmp = new Perso();
		try{
			tmp.setVitesse(file.readInt());
			tmp.setBouge(file.readBoolean());
			tmp.setAnime(file.readBoolean());
			tmp.setSolide(file.readBoolean());
			tmp.setVisible(file.readBoolean());
			tmp.setNom(file.readUTF());
			tmp.setNumerot(file.readUTF());
			tmp.deplacements = InputFiles.readArrayPos(file,file.readInt());
			int nb = file.readInt();
			for(int i=0;i<nb;i++){
				Interaction y = (Interaction)file.readObject();
				tmp.addInteraction(y);
			}
			nb = file.readInt();
			for(int i=0;i<nb;i++)
				tmp.interupteurs.add((Switch)file.readObject());
			tmp.setPos((Pos)file.readObject());
			EventImg2 t = new EventImg2("empty");
			t.read(file);
			tmp.setImg(t);
		} catch (ClassCastException e){
			System.out.println("Perso: read error");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tmp;
	}
	
	public int getVitesse() {
		return vitesse;
	}

	public void setVitesse(int vitesse) {
		this.vitesse = vitesse;
		this.maj();
	}

}