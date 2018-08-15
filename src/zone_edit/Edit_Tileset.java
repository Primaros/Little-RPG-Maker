package zone_edit;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Observable;
import java.util.Observer;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import zone.Map;
import zone.Tileset;
import zone.Tuile;

public class Edit_Tileset extends Observable implements Observer{

	protected int nb_tuile_hauteur,nb_tuile_largeur;
	protected Tuile[][] tab_tuile;
	protected int[] selection_emp;
	private Tuile selected;
	private Tileset t;
	private panelPerso panel = new panelPerso();
	private Edit_GestionTouches clavier;

	@SuppressWarnings("serial")
	public class panelPerso extends JPanel{
		
		public void paintComponent(Graphics g){
			int espace_x = 2;
			int espace_y = 2;
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			for (int y = 0; y<Edit_Tileset.this.nb_tuile_hauteur; y++){
				for (int x=0; x <Edit_Tileset.this.nb_tuile_largeur; x++){
					try {
						int taille_y =(this.getHeight()/(Edit_Tileset.this.nb_tuile_hauteur)-2); int taille_x =(this.getWidth()/(Edit_Tileset.this.nb_tuile_largeur)-2);int posY = ((2+y*taille_y)+(y*espace_y))+(((2+x*taille_x)+(x*espace_x))/(this.getWidth())); int posX = ((2+x*taille_x)+(x*espace_x))%(this.getWidth());
						if (y == Edit_Tileset.this.selection_emp[0] && x == Edit_Tileset.this.selection_emp[1] ){
							g.setColor(Color.GREEN);
							g.fillRect(posX-2, posY-2, taille_x+4, taille_y+4);
						}
						g.drawImage(Edit_Tileset.this.tab_tuile[y][x].getImage(), posX, posY, taille_x, taille_y, null);	
						if (clavier.keys.contains(KeyEvent.VK_CONTROL) && Edit_Tileset.this.tab_tuile[y][x].isSolide()){
							g.setColor(new Color(255,0,0,120));
							g.fillRect(posX-1, posY-1, taille_x+2, taille_y+2);
						}
						
						else if (clavier.keys.contains(KeyEvent.VK_SHIFT)){
							g.setColor(new Color(0,0,0,120));
							g.fillRect(posX-1, posY-1, taille_x+2, taille_y+2);
							g.setColor(Color.WHITE);
							g.drawString(""+Edit_Tileset.this.tab_tuile[y][x].getNiveaux(), posX+Edit_Tileset.this.tab_tuile[y][x].getImage().getWidth()/2-g.getFont().getSize()/3, posY+Edit_Tileset.this.tab_tuile[y][x].getImage().getHeight()/2+g.getFont().getSize()/3);
						}
					} catch (Exception e) {
						// Comme on charge une tile en plus, aux bord du tableau on bugg. Suffit de l'ignorer car elle ne sera jamais vue
					}
				}
			}
		}
	}

	public Edit_Tileset(String fichier, Edit_GestionTouches keysL) {
		this.clavier = keysL;
		this.init(fichier);
		keysL.addObserver(this);
		this.panel.addKeyListener(keysL);
		this.panel.requestFocusInWindow();
	}

	public void chargerTiles(){
		for (int y=0; y<this.getT().getImg().getHeight()/Map.TAILLE_TUILE;y++){
			for (int x=0; x<this.getT().getImg().getWidth()/Map.TAILLE_TUILE;x++){
				this.tab_tuile[y][x] = new Tuile(this.getT(),x*Map.TAILLE_TUILE,y*Map.TAILLE_TUILE,Map.TAILLE_TUILE,Map.TAILLE_TUILE,1,false);
			}
		}
	}


	public Tuile getTile(int x, int y){
		return this.tab_tuile[x][y];
	}

	private void init(String fichier){
		this.selected = Map.tuile_vide;
		this.t = new Tileset(fichier.split("\\.tileset")[0]);
		this.selection_emp = new int[2];this.selection_emp[0] = -1;this.selection_emp[1] = -1;
		this.nb_tuile_hauteur = this.getT().getImg().getHeight()/Map.TAILLE_TUILE; this.nb_tuile_largeur = this.getT().getImg().getWidth()/Map.TAILLE_TUILE;
		this.tab_tuile = new Tuile[this.nb_tuile_hauteur][this.nb_tuile_largeur];
		this.chargerTiles();
		
		this.panel.setPreferredSize(new Dimension((this.nb_tuile_largeur*Map.TAILLE_TUILE)+(this.nb_tuile_largeur*2) + 2,(this.nb_tuile_hauteur*Map.TAILLE_TUILE)+(this.nb_tuile_hauteur*2) + 2));
		this.panel.addMouseListener(new Edit_GestionSouris(this));
		this.panel.setFont(new Font("TimesRoman",Font.PLAIN ,20));
		
	}
	
	public boolean charger(String n){
		boolean charg = true;
		if(new File(n).exists()){
			for(int i=0;i<this.panel.getKeyListeners().length;i++)
				this.panel.removeKeyListener(this.panel.getKeyListeners()[i]);
			
			for(int i=0;i<this.panel.getMouseListeners().length;i++)
				this.panel.removeMouseListener(this.panel.getMouseListeners()[i]);
			
			this.init(n);

		// On recuperre les donner precedement sauver pour les appliquer aux tuiles
		ObjectInputStream file = null;
		try {
			
				file= new ObjectInputStream( new FileInputStream(n));
				for(int y=0;y<this.tab_tuile.length;y++){
					for(int x=0;x<this.tab_tuile[y].length;x++){
						if (file.readBoolean()){
							this.tab_tuile[y][x].setNiveaux(file.readInt());
							this.tab_tuile[y][x].setSolide(file.readBoolean());
						}
					}
				}

				file.close();
			
		} catch (IOException e1) {
			System.out.println("Edit_Tilest : Le chargement des donnees du tileset n'a pu s'effectuer");
			e1.printStackTrace();
			charg = false;
		} 
		}
		this.setChanged();
		this.notifyObservers();
		return charg;
	}

	public boolean sauvgarder(String nom){
		boolean sav = true;
		ObjectOutputStream file = null;
		try {
			if(new File("save"+File.separator+"tileset"+File.separator+nom+".tileset").exists())
				new File("save"+File.separator+"tileset"+File.separator+nom+".tileset").delete();
			file= new ObjectOutputStream( new FileOutputStream("save"+File.separator+"tileset"+File.separator+nom+".tileset"));
			for(int y=0;y<this.tab_tuile.length;y++){
				for(int x=0;x<this.tab_tuile[y].length;x++){
					if (this.tab_tuile[y][x] != null){
						file.writeBoolean(true);
						file.writeInt(this.tab_tuile[y][x].getNiveaux());
						file.writeBoolean(this.tab_tuile[y][x].isSolide());
					} else
						file.writeBoolean(false);
				}
			}
			file.close();
		} catch (IOException e) {
			System.out.println("Edit_Tilest : La sauvegarde des donnees du tileset n'a pu s'effectuer");
			e.printStackTrace();
			sav = false;
		}

		return sav;

	}

	public Tileset getT() {
		return t;
	}
	
	public Tuile getSelected() {
		return selected;
	}

	public void setSelected(Tuile selected) {
		this.selected = selected;
		this.setChanged();
		this.notifyObservers();
	}

	public panelPerso getPanel() {
		return panel;
	}
	
	public Edit_GestionTouches getKeyListener(){
		return this.clavier;
	}

	public void show(){
		JFrame f_tileset = new JFrame();
		f_tileset.setTitle(this.getT().getNom());
		f_tileset.setContentPane(this.getPanel());
		f_tileset.pack();
		f_tileset.setVisible(true);
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		panel.repaint();
	}

}
