package evenement;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Pos implements Serializable{
	public int x;
	public int y;
	
	public Pos(){
		this.setX(0);
		this.setY(0);
	}
	
	public Pos(int x, int y){
		this.setX(x);
		this.setY(y);
	}

	public int getX() {
		return (int)x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return (int)y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
