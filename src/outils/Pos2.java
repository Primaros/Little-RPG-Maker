package outils;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Pos2 implements Serializable{
	public int x,y,w,h;
	public Pos2 (int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
}
