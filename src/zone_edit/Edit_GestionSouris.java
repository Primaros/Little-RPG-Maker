package zone_edit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import zone.Map;
import zone_edit.Edit_Tileset.panelPerso;


public class Edit_GestionSouris extends MouseAdapter{

	Edit_Tileset t;
	
	public Edit_GestionSouris(Edit_Tileset t){
		this.t = t;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		panelPerso tmp = (panelPerso)(e.getSource());
		int x=(e.getX()/(tmp.getWidth()/(t.nb_tuile_largeur))),y=(e.getY()/(tmp.getHeight()/(t.nb_tuile_hauteur)));
		
		if (y > t.nb_tuile_hauteur-1 || y < 0) y = -1;
		if (x > t.nb_tuile_largeur-1 || x < 0) x = -1;
		
		if (y == -1 || x == -1) 
			t.setSelected(Map.tuile_vide);
		
		else {
			if (t.getKeyListener().keys.contains(KeyEvent.VK_CONTROL)){
				if (t.tab_tuile[y][x].isSolide())
					t.tab_tuile[y][x].setSolide(false);
				else
					t.tab_tuile[y][x].setSolide(true);
				if (t.selection_emp[0]==y && t.selection_emp[1]==x)
					t.setSelected(t.tab_tuile[y][x]);
			}
			else if (t.getKeyListener().keys.contains(KeyEvent.VK_SHIFT)){
				t.tab_tuile[y][x].setNiveaux(t.tab_tuile[y][x].getNiveaux()+1);
				if (t.tab_tuile[y][x].getNiveaux() > 3){
					t.tab_tuile[y][x].setNiveaux(0);
				}
				if (t.selection_emp[0]==y && t.selection_emp[1]==x)
					t.setSelected(t.tab_tuile[y][x]);
			}
			else{
				t.selection_emp[0]=y;
				t.selection_emp[1]=x;
				t.setSelected(t.tab_tuile[y][x]);
			}
			
		}
			
		
		tmp.repaint();
	}

}
