package evenement;

import zone.Map;
import zone.Zone;

public class PersoUpdater extends Perso{

	private Perso p;
	private Zone z;
	
	public static void update(EventX eventX, Zone z){
		PersoUpdater tmp = new PersoUpdater();
		tmp.z = z;
		tmp.p = (Perso) eventX;
		tmp.actualiser();
	}
	
	public void mv(int direction){
		p.getImg().lookAt(direction);
		int tmp = p.vitesse, y=p.deplacements.get(p.deplacements.size()-1).getY(), x=p.deplacements.get(p.deplacements.size()-1).getX();
		
			if(direction==EventX.DROITE||direction==EventX.GAUCHE){
				if(x - tmp < 0)
					tmp = x;
				if(p.isBouge() && p.getPos().x + tmp + p.getImg().getImg().getWidth() <= Map.NB_TUILE_TOTAL_X*Map.TAILLE_TUILE &&(z.getMap().getTile(p.getPos().getY()/Map.TAILLE_TUILE, (p.getPos().getX() + Map.TAILLE_TUILE)/Map.TAILLE_TUILE, 2) == null || !z.getMap().getTile(p.getPos().getY()/Map.TAILLE_TUILE, (p.getPos().getX() + Map.TAILLE_TUILE)/Map.TAILLE_TUILE, 2).isSolide()))
					p.setPos(new Pos (p.getPos().getX() + tmp,p.getPos().getY()));	
				p.deplacements.get(p.deplacements.size()-1).x -= tmp;
			}else{
				if(y - tmp < 0)
					tmp = y;
				if(p.isBouge() && p.getPos().y + tmp + p.getImg().getImg().getHeight() <= Map.NB_TUILE_TOTAL_Y*Map.TAILLE_TUILE && (z.getMap().getTile((p.getPos().getY()+Map.TAILLE_TUILE)/Map.TAILLE_TUILE, p.getPos().getX()/Map.TAILLE_TUILE, 2) == null || !z.getMap().getTile((p.getPos().getY()+Map.TAILLE_TUILE)/Map.TAILLE_TUILE, p.getPos().getX()/Map.TAILLE_TUILE, 2).isSolide()))
					p.setPos(new Pos (p.getPos().getX(),p.getPos().getY() + tmp));
				p.deplacements.get(p.deplacements.size()-1).y -= tmp;
			}
		
		if (p.deplacements.get(p.deplacements.size()-1).x == 0 && p.deplacements.get(p.deplacements.size()-1).y == 0)
			p.deplacements.remove(p.deplacements.size()-1);
		p.maj();
	}

	public void bouger(){
		if (p.isAnime())
			p.getImg().play();
		
		if(p.deplacements.get(p.deplacements.size()-1).x > 0){
			this.mv(EventX.DROITE);
		} else if(p.deplacements.get(p.deplacements.size()-1).x < 0){
			this.mv(EventX.GAUCHE);
		} else if(p.deplacements.get(p.deplacements.size()-1).y > 0){
			this.mv(EventX.BAS);
		} else if(p.deplacements.get(p.deplacements.size()-1).y  < 0){
			this.mv(EventX.HAUT);
		}
		
	}

	public boolean enMouvement(){
		boolean enM = true;
		if (p.deplacements.size() <= 0)
			enM = false;

		return enM;
	}

	public void actualiser(){
		
		if (this.enMouvement()){
			this.bouger();
		} else if(p.getInteraction().size() != 0) {
			for(int i=0;i<p.getActions().size();i++){
				if (p.actions.get(i).getCondition()==null || p.actions.get(i).getCondition().isValidate(z.getEvent())){
					if (p.actions.get(i).exec(p,z.getEvent()))
						p.actions.remove(i);	
					break;
				}
			}
			
		} else {
			p.getImg().stop();
		}
		
		if(p.isAnime())
			p.getImg().actualiser();
		
	}
	
}
