package zone;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;


@SuppressWarnings("serial")
public class GestionTouches extends Observable implements KeyListener , Serializable{
	private ArrayList<Integer> key;
	
	public GestionTouches(){
		super();
		this.key = new ArrayList<Integer>();
	}
	
	public void keyTyped (KeyEvent e){
		
	}
	
	public void keyReleased (KeyEvent e){
		this.key.remove(e.getKeyCode());
	}
	
	public void keyPressed (KeyEvent e){
		this.key.add(e.getKeyCode());
	}
}
