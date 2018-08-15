package camera;

import edit.Game;
import evenement.Perso;
import evenement.Pos;
import zone.Map;

public final class Camera {
	private int offsetX, offsetY;
	private int offsetXr, offsetYr;
	private Perso perso;
	private static Camera camera;
	private double decompte;
	private double time;
	
	public class Pos2 {
		double x = 0, y = 0;
		
		public Pos2(double x,double y){
			this.x=x;
			this.y=y;
		}
	}
	
	Pos2 v ;
	Pos2 acc ;
	Pos2 Dinit = new Pos2(0,0);
	
	private Camera(){
		offsetX = 0;
		offsetY = 0;
		offsetXr = - (Map.NB_TUILE_X*Map.TAILLE_TUILE)/2;
		offsetYr = - (Map.NB_TUILE_Y*Map.TAILLE_TUILE)/2;
		decompte = 0;
	}
	
	public static Pos getPos(){
		Camera.init();
		camera.update();
		return new Pos(camera.offsetX,camera.offsetY);
	}
	
	public static void setPos(Pos p){
		Camera.init();
		camera.perso = null;
		camera.offsetX = p.getX();
		camera.offsetY = p.getY();
	}
	
	public static void setPerso(Perso p){
		Camera.init();
		camera.perso = p;
		if (!(camera.offsetXr == camera.perso.getPos().getX() - (Map.NB_TUILE_X*Map.TAILLE_TUILE)/2 && camera.offsetYr == camera.perso.getPos().getY() - (Map.NB_TUILE_Y*Map.TAILLE_TUILE)/2))
			camera.upInit();
	}
	
	private void upInit(){
		if (offsetYr < 0)
			offsetYr = 0;
		if (offsetXr < 0)
			offsetXr = 0;
		v = new Pos2(0,0);
		acc = new Pos2(0.002,0.002);
		Dinit = new Pos2(perso.getPos().getX() - (Map.NB_TUILE_X*Map.TAILLE_TUILE)/2-offsetXr,perso.getPos().getY() - (Map.NB_TUILE_Y*Map.TAILLE_TUILE)/2-offsetYr);
		if (Dinit.x>Dinit.y)
			acc.x*=Math.abs(Dinit.x)/Math.abs(Dinit.y);
		else if (Dinit.y>Dinit.x)
			acc.y*=Math.abs(Dinit.y)/Math.abs(Dinit.x);
		
		camera.decompte = 50;
		time = System.currentTimeMillis();
		
	}
	
	private void update(){
		if (perso != null) {
			if (offsetXr == perso.getPos().getX() - (Map.NB_TUILE_X*Map.TAILLE_TUILE)/2 && offsetYr == perso.getPos().getY() - (Map.NB_TUILE_Y*Map.TAILLE_TUILE)/2)
				decompte = 0;
			if (decompte > 0){
				int tmp = (int)(System.currentTimeMillis()-time);
				if (tmp >= Game.UPDATE*1.1){
					
					Pos2 D = new Pos2(perso.getPos().getX() - (Map.NB_TUILE_X*Map.TAILLE_TUILE)/2-offsetXr,perso.getPos().getY() - (Map.NB_TUILE_Y*Map.TAILLE_TUILE)/2-offsetYr);
					int signeX = 1,signeY = 1;
					if (D.x < 0)
						signeX = -1;
					else if (D.x == 0){
						signeX = 0;
						v.x = 0;
					}

					if (D.y < 0)
						signeY = -1;
					else if (D.y == 0){
						signeY = 0;
						v.y = 0;
					}
					System.out.println(offsetXr+ " " + offsetYr);
					if(D.x >= -5 && D.x <= 5){
						offsetXr = perso.getPos().getX() - (Map.NB_TUILE_X*Map.TAILLE_TUILE)/2;
					}
					else
						offsetXr = (int)(tmp*v.x)*signeX+offsetXr;
					
					if(D.y >= -10 && D.y <= 10)
						offsetYr = perso.getPos().getY() - (Map.NB_TUILE_Y*Map.TAILLE_TUILE)/2;
					else
						offsetYr = (int)(tmp*v.y)*signeY+offsetYr;
					
					
					if (Math.abs(D.x)>Math.abs(Dinit.x)/2){
						v.x = v.x + acc.x * tmp;
					} else if (D.x == 0){
						v.x = 0;
					} else {
						v.x = v.x - acc.x * tmp;
					}
					
					if (Math.abs(D.y)>Math.abs(Dinit.y)/2){
						v.y = v.y + acc.y * tmp;
					} else if (D.y == 0){
						v.y = 0;
					} else {
						v.y = v.y  - acc.y * tmp;
					}
					
					
						
						
					time = System.currentTimeMillis();
				}
				
			} else {
				offsetXr = perso.getPos().getX() - (Map.NB_TUILE_X*Map.TAILLE_TUILE)/2;
				offsetYr = perso.getPos().getY() - (Map.NB_TUILE_Y*Map.TAILLE_TUILE)/2;
			}
			offsetX = offsetXr;
			offsetY = offsetYr;
		}
		
		if (offsetX < 0)
			offsetX = 0;
		else if (offsetX + Map.NB_TUILE_X*Map.TAILLE_TUILE> Map.NB_TUILE_TOTAL_X*Map.TAILLE_TUILE)
			offsetX = (Map.NB_TUILE_TOTAL_X - Map.NB_TUILE_X)*Map.TAILLE_TUILE;
		
		if (offsetY < 0)
			offsetY = 0;
		else if (offsetY + Map.NB_TUILE_Y*Map.TAILLE_TUILE> Map.NB_TUILE_TOTAL_Y*Map.TAILLE_TUILE)
			offsetY = (Map.NB_TUILE_TOTAL_Y - Map.NB_TUILE_Y)*Map.TAILLE_TUILE;
	}
	
	private static void init(){
		if (camera == null)
			camera = new Camera();
	}
	
}
