package zone_edit;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import zone.Map;
import zone.Tuile;
import zone.Zone;

public class Edit_Zone extends Observable implements Observer{
	private Zone zone;
	private Tuile overed;
	private Tuile selected;

	public Edit_Zone(Edit_GestionTouches t){
		this.zone = new Zone();
		this.zone.setFullMap(true);
		this.init(t);
	}

	public Edit_Zone(String nom, Edit_GestionTouches t){
		this.zone = new Zone(nom);
		this.init(t);
	}

	public void init(Edit_GestionTouches t){
		this.overed = Map.tuile_vide;
		this.selected = Map.tuile_vide;

		MouseMotionListener mouse_2 = new MouseMotionListener(){

			@Override
			public void mouseDragged(MouseEvent arg0) {
				Zone zone = Edit_Zone.this.zone;
				//int x=arg0.getX()/(zone.getWidth()/Map.NB_TUILE_X)+zone.getPositionX(), y=arg0.getY()/(zone.getHeight()/Map.NB_TUILE_Y)+zone.getPositionY();
				int x=arg0.getX()/Map.TAILLE_TUILE, y=arg0.getY()/Map.TAILLE_TUILE;
				if (x>=0 && x< Map.NB_TUILE_TOTAL_X && y>=0 && y< Map.NB_TUILE_TOTAL_Y){
					if (SwingUtilities.isLeftMouseButton(arg0))
						zone.setTile(x,y,Edit_Zone.this.getSelected());
					else if (SwingUtilities.isRightMouseButton(arg0)){
						zone.setTile(x,y,Map.tuile_vide);
						zone.getMap().setTileNull(x,y,1);
						zone.getMap().setTileNull(x,y,2);
						zone.getMap().setTileNull(x,y,3);
					}
					
				}
				zone.repaint();
			}

			@Override
			public void mouseMoved(MouseEvent arg0) {
				Zone zone = Edit_Zone.this.zone;
				int x=arg0.getX()/Map.TAILLE_TUILE, y=arg0.getY()/Map.TAILLE_TUILE;
				for(int z=3;z>=0;z--){
					if (zone.getTile(y, x, z)!=null){
						Edit_Zone.this.setTileSurvole(zone.getTile(y, x, z));
						break;
					}
				}
			}

		};

		zone.addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent arg0) {
				Zone zone = Edit_Zone.this.zone;
				//				int x=arg0.getX()/(zone.getWidth()/Map.NB_TUILE_X)+zone.getPositionX(), y=arg0.getY()/(zone.getHeight()/Map.NB_TUILE_Y)+zone.getPositionY();
				int x=arg0.getX()/Map.TAILLE_TUILE, y=arg0.getY()/Map.TAILLE_TUILE;
					if (SwingUtilities.isLeftMouseButton(arg0)){
						zone.setTile(x,y,Edit_Zone.this.getSelected());
					}
					else if (SwingUtilities.isRightMouseButton(arg0)){
						zone.setTile(x,y,Map.tuile_vide);
						zone.getMap().setTileNull(x,y,1);
						zone.getMap().setTileNull(x,y,2);
						zone.getMap().setTileNull(x,y,3);
					}

				for(int z=3;z>=0;z--){
					if (zone.getTile(y, x, z)!=null){
						Edit_Zone.this.setTileSurvole(zone.getTile(y, x, z));
						break;
					}
				}
				Edit_Zone.this.zone.repaint();
			}

		});
		
		this.zone.addMouseMotionListener(mouse_2);
		this.setSelected(Map.tuile_vide);
		this.setTileSurvole(Map.tuile_vide);
		this.zone.getEvent().addObserver(this);
		this.zone.addKeyListener(t);
		this.zone.requestFocusInWindow();
	}

	public Zone getZone(){
		return this.zone;
	}

	public void setTileSurvole(Tuile t){
		this.overed = t;
		this.setChanged();
		this.notifyObservers();
	}

	public Tuile getTileSurvole(){
		return this.overed;
	}

	public void setSelected(Tuile selected) {
		this.selected = selected;
	}

	public Tuile getSelected(){
		return this.selected;
	}

	public void show(JFrame f){
		JDialog fenetre = new JDialog(f,"Map: Création");
		fenetre.setModal(true);
		JTextField nom = new JTextField(this.zone.getNom());
		JLabel intro = new JLabel("Nom : ");
		JButton valider = new JButton("Valider"), annuler  = new JButton("Annuler");

		valider.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Edit_Zone.this.zone.setNom(nom.getText());
				fenetre.dispose();
			}
		});

		annuler.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				fenetre.dispose();
			}
		});

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2,2));
		panel.add(intro);
		panel.add(nom);
		panel.add(valider);
		panel.add(annuler);

		fenetre.setSize(200, 100);
		fenetre.getContentPane().add(panel);
		fenetre.setVisible(true);

	}

	@Override
	public void update(Observable o, Object arg) {
		if(o.getClass() == TileInfo.class)
			this.setSelected(((TileInfo)o).getSelected());
		this.zone.revalidate();
		this.zone.repaint();
	}

}
