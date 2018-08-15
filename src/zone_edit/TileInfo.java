package zone_edit;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.SoftBevelBorder;

import zone.Map;
import zone.Tuile;

public class TileInfo extends Observable implements Observer{
	private TuilePerso selected = new TuilePerso(Map.tuile_vide);
	private TuilePerso overed = new TuilePerso(Map.tuile_vide);
	private TuilePerso selected_inf = new TuilePerso(selected.getT());

	@SuppressWarnings("serial")
	private class TuilePerso extends JPanel{
		private boolean affInfo = false;
		private Tuile t;

		public TuilePerso(Tuile t){
			this.t = t;
		}

		public Tuile getT() {
			return t;
		}

		public void switchAffInfo(){
			affInfo = !affInfo;
		}

		public void setT(Tuile t) {
			this.t = t;
			this.repaint();
		}

		public void paintComponent(Graphics g){
			super.paintComponent(g);
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, Map.TAILLE_TUILE+4, Map.TAILLE_TUILE+4);
			g.drawImage(t.getImage(), 2, 2, Map.TAILLE_TUILE, Map.TAILLE_TUILE, null);
			if(this.affInfo){
				if (this.t.isSolide())
					g.setColor(new Color(255,0,0,120));
				else
					g.setColor(new Color(0,0,0,120));
				g.fillRect(2, 2, Map.TAILLE_TUILE, Map.TAILLE_TUILE);
				g.setColor(Color.WHITE);
				g.drawString(""+this.t.getNiveaux(), (Map.TAILLE_TUILE+4)/2-g.getFont().getSize()/3, (Map.TAILLE_TUILE+4)/2+g.getFont().getSize()/3);
			}
			
		}

	}

	public JPanel showMenu(){
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(0, selected.getT().getImage().getHeight()+8));
		panel.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
		panel.setLayout(new GridLayout(1,4));
		
		panel.add(new JLabel("Tuile :"));
		panel.add(selected);
		panel.add(selected_inf);
		panel.add(overed);
		
		selected_inf.switchAffInfo();
		overed.switchAffInfo();
		
		return panel;
	}

	public void setSelected(Tuile t){
		this.selected.setT(t);
		this.selected_inf.setT(t);
		this.setChanged();
		this.notifyObservers();
	}
	
	public void setOvered(Tuile t){
		this.overed.setT(t);
		this.setChanged();
		this.notifyObservers();
	}
	
	public Tuile getSelected(){
		return this.selected.getT();
	}

	public void update(Observable o, Object arg) {
		if(o.getClass() == Edit_Tileset.class)
			setSelected(((Edit_Tileset)o).getSelected());
		else if (o.getClass() == Edit_Zone.class){
			setOvered(Map.tuile_vide);
			Edit_Zone tmp = (Edit_Zone)o;
			if (tmp.getTileSurvole() != null)
				setOvered(tmp.getTileSurvole());
		}
	}
	
}
