package zone_edit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Observable;


public class Edit_GestionTouches extends Observable implements KeyListener{

	public ArrayList<Integer> keys;

	public Edit_GestionTouches(){
		this.keys = new ArrayList<Integer>();
	}

	public void keyTyped (KeyEvent e){

	}

	public void keyReleased (KeyEvent e){
		if (keys.contains(e.getKeyCode())){
			keys.remove(keys.indexOf(e.getKeyCode()));
			this.setChanged();
			this.notifyObservers();
		}
	}

	public void keyPressed (KeyEvent e){
		if (!keys.contains(e.getKeyCode())){
			System.out.println(e.getKeyChar());
			keys.add(e.getKeyCode());
			this.setChanged();
			this.notifyObservers();
		}
	}

}
