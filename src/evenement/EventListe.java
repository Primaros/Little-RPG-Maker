package evenement;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import edit_variable.Switch_G;

public class EventListe extends Observable implements Observer {
	private ArrayList<EventX> event;
	private ArrayList<Switch_G> switchG;
	public int nb_event = 0;
	
	public EventListe(){
		this.event = new ArrayList<EventX>();
		this.setSwitchG(new ArrayList<Switch_G>());
		this.getSwitchG().add(new Switch_G());
		this.getSwitchG().add(new Switch_G());
		this.getSwitchG().add(new Switch_G());
		this.getSwitchG().add(new Switch_G());
		this.getSwitchG().add(new Switch_G());
		this.getSwitchG().add(new Switch_G());
		this.getSwitchG().add(new Switch_G());
		this.getSwitchG().add(new Switch_G());
		this.getSwitchG().add(new Switch_G());
		this.getSwitchG().add(new Switch_G());
	}
	
	public void add(EventX e){
		this.nb_event+=1;
		this.event.add(e);
		e.addObserver(this);
		this.maj();
	}
	
	public void remove(EventX e){
		e.deleteObserver(this);
		this.event.remove(e);
		this.maj();
	}
	
	public void maj(){
		this.setChanged();
		this.notifyObservers();
	}
	
	public Perso createPerso(){
		Perso p = new Perso();
		p.setNom("Event_"+(this.nb_event+1));
		this.add(p);
		p.addObserver(this);
		this.maj();
		return p;
	}
	
	public EventX get(int e){
		return this.event.get(e);
	}
	
	public int size(){
		return this.event.size();
	}
	
	public void save(ObjectOutputStream file){
		try {
			file.writeInt(this.event.size());
			for(int i=0;i<this.event.size();i++){
				this.event.get(i).write(file);
			}
		} catch (IOException e) {
			System.out.println("EventListe : Sauvgarde des events impossible");
			e.printStackTrace();
		}
		
	}
	
	public void charger(ObjectInputStream file){
		try {
			this.event = new ArrayList<EventX>();
			int nbEvents = file.readInt();
			
			for(int i=0;i<nbEvents;i++){
				Perso p = Perso.read(file);
					p.addObserver(this);
					this.event.add(p);
			}
			
		} catch (IOException e) {
			System.out.println("EventListe : Chargement des events impossible");
			e.printStackTrace();
		}
		this.maj();
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		this.maj();
	}

	public ArrayList<Switch_G> getSwitchG() {
		return switchG;
	}

	public void setSwitchG(ArrayList<Switch_G> switchG) {
		this.switchG = switchG;
	}
}
