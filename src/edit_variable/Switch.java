package edit_variable;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Switch implements Serializable{
	private int id;
	private boolean activate;
	private String nom;
	
	public static int NB = 0;
	
	public Switch(String nom){
		this.init();
		this.setNom(nom);
	}
	
	public Switch(){
		this.init();
	} 
	
	private void init(){
		Switch.NB += 1;
		this.setId(Switch.NB);
		this.disable();
		this.setNom("Interupteur "+Switch.NB);
	}
	
	public int getId(){
		return this.id;
	}
	
	public void setId(int tmp){
		this.id = tmp;
	}
	
	public void enable(){
		this.activate = true;
	}
	
	public void disable(){
		this.activate = false;
	}
	
	public boolean isEnable(){
		return this.activate;
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
	
}
