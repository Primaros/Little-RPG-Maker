package edit;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JViewport;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import zone.GestionTouches;
import zone.Map;
import zone.Zone;
import zone_edit.Edit_Event;
import zone_edit.Edit_GestionTouches;
import zone_edit.Edit_Tileset;
import zone_edit.Edit_Zone;
import zone_edit.TileInfo;

public class tmp implements Observer{

	private JFrame fenetre;
	
	private JPanel map_panel = new JPanel();
	private JTabbedPane onglet = new JTabbedPane();
	private Edit_Event e_event;
	
	private TileInfo tuile_info = new TileInfo();

	private ArrayList<Edit_Tileset> tileset = new ArrayList<Edit_Tileset>();
	private ArrayList<Edit_Zone> zones = new ArrayList<Edit_Zone>();

	public Edit_GestionTouches keyboard = new Edit_GestionTouches();
	
 	public tmp(){
		onglet.setBackground(new Color(245,240,255));
		onglet.addChangeListener(new ChangeListener() {
	        public void stateChanged(ChangeEvent e) {
	        	e_event.setEvent(zones.get(onglet.getSelectedIndex()).getZone().getEvent());
	        }
	    });
		this.fenetre = new JFrame("RPG Maker");
		this.fenetre.setPreferredSize(new Dimension(800,600));
		this.fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.fenetre.setLayout(new BorderLayout());
		this.initMenu(this.fenetre);
		this.e_event = new Edit_Event();
		JSplitPane splitLeftPart = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tuile_info.showMenu(), e_event);
		
		map_panel.setLayout(new BorderLayout());
		map_panel.add(onglet,BorderLayout.CENTER);
		JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, splitLeftPart, map_panel);
		split.setDividerLocation(240);
		split.setResizeWeight(0);
		split.setEnabled(false);
		
		this.fenetre.add(split,BorderLayout.CENTER);
		this.fenetre.setMinimumSize(new Dimension(800, 600));
		
		
		this.fenetre.requestFocusInWindow();
		this.fenetre.pack();
	}

	public void show(){
		this.fenetre.setVisible(true);
	}
	
	public void initMenu(JFrame f){
		
		JMenuBar menuBar = new JMenuBar();
		JMenu m_fichier = new JMenu("Fichier");
		JMenu m_map = new JMenu("Map");
		JMenu m_tileset = new JMenu("Tileset");

		JMenuItem m_fichier_fermer = new JMenuItem("Fermer");
		JMenuItem m_fichier_play = new JMenuItem("Play");

		JMenuItem m_map_nouveau = new JMenuItem("Nouveau");
		JMenuItem m_map_charger = new JMenuItem("Charger");
		JMenuItem m_map_sauvegarder = new JMenuItem("Sauvegarder");

		JMenuItem m_tileset_nouveau = new JMenuItem("Nouveau");
		JMenuItem m_tileset_charger = new JMenuItem("Charger");
		JMenuItem m_tileset_sauvegarder = new JMenuItem("Sauvegarder");
		
		m_fichier_fermer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				tmp.this.fenetre.dispose();
				System.exit(0);
			}
		});
		
		m_fichier_play.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Game.launch();
			}
		});

		m_map_nouveau.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Edit_Zone i = new Edit_Zone(keyboard);
				i.show(tmp.this.fenetre);
				zones.add(i);
				i.addObserver(tuile_info);
				tuile_info.addObserver(i);
				i.setSelected(tuile_info.getSelected());
				JScrollPane tmp = (new JScrollPane(i.getZone(),JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS));
				onglet.add(tmp);
				i.getZone().setPreferredSize(new Dimension(Map.NB_TUILE_TOTAL_X*Map.TAILLE_TUILE,Map.NB_TUILE_TOTAL_Y*Map.TAILLE_TUILE));
				tmp.setPreferredSize(new Dimension(map_panel.getWidth(), map_panel.getHeight()));
				i.getZone().setLayout(new BorderLayout());
				onglet.setTabComponentAt(onglet.indexOfComponent(tmp), new titreTab(i.getZone().getNom(),onglet));
				tmp.addKeyListener(keyboard);
				tmp.requestFocusInWindow();
			}
		});

		m_map_charger.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new File("save"+File.separator+"map"));
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Fichier valide", "zone");
				chooser.setFileFilter(filter);
				if( chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					Edit_Zone i = new Edit_Zone(chooser.getSelectedFile().getPath(),keyboard);
					zones.add(i);
					JScrollPane tmp = (new JScrollPane(i.getZone(),JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS));
					onglet.add(tmp);
					i.getZone().setPreferredSize(new Dimension(Map.NB_TUILE_TOTAL_X*Map.TAILLE_TUILE,Map.NB_TUILE_TOTAL_Y*Map.TAILLE_TUILE));
					tmp.setPreferredSize(new Dimension(map_panel.getWidth(), map_panel.getHeight()));
					i.addObserver(tuile_info);
					i.setSelected(tuile_info.getSelected());
					i.getZone().setFullMap(true);
					tuile_info.addObserver(i);
					onglet.setTabComponentAt(onglet.indexOfComponent(tmp), new titreTab(i.getZone().getNom(),onglet));
				}
			}
		});

		m_map_sauvegarder.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JScrollPane y = (JScrollPane)onglet.getComponentAt(onglet.getSelectedIndex());
				JViewport viewport = y.getViewport(); 
				Zone i = (Zone)viewport.getView();
				int choix = JOptionPane.showConfirmDialog(null, "Enregistrer sous: "+i.getNom(), "Sauvegarde", JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);
				if (choix == JOptionPane.YES_OPTION){
					i.sauvgarder(i.getNom());
				} else if (choix == JOptionPane.NO_OPTION) {
					String nom = JOptionPane.showInputDialog(null, "Enregistrer sous:", "Sauvegarde", JOptionPane.QUESTION_MESSAGE);
					if (nom != null && !nom.equals("null"))
						i.sauvgarder(nom);
				}

			}
		});

		m_tileset_nouveau.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new File("tileset"));
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Fichier valide", "png");
				chooser.setFileFilter(filter);
				if( chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					Edit_Tileset i = new Edit_Tileset(chooser.getSelectedFile().getPath(), keyboard);
					tmp.this.tileset.add(i);
					i.show();
					i.addObserver(tuile_info);
					i.getPanel().requestFocusInWindow();
				}

			}
		});

		m_tileset_sauvegarder.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				boolean sav = false;
				for (int i = 0; i < tmp.this.tileset.size(); i++){
					sav = tmp.this.tileset.get(i).sauvgarder(tmp.this.tileset.get(i).getT().getNom());
					if(!sav)
						break;
				}
				if (sav)
					JOptionPane.showMessageDialog(null, "Tileset Sauvegarder !", "Tileset", JOptionPane.INFORMATION_MESSAGE);
				else
					JOptionPane.showMessageDialog(null, "Impossible de Sauvegarder", "Tileset", JOptionPane.INFORMATION_MESSAGE);
			}
		});

		m_tileset_charger.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new File("save"+File.separator+"tileset"));
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Fichier valide", "tileset");
				chooser.setFileFilter(filter);
				if( chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					Edit_Tileset i = new Edit_Tileset(chooser.getSelectedFile().getPath(), keyboard);
					i.charger(chooser.getSelectedFile().getPath());
					tmp.this.tileset.add(i);
					i.show();
					i.addObserver(tuile_info);
					i.getPanel().requestFocusInWindow();
				}

			}

		});


		m_fichier.add(m_fichier_fermer);
		m_fichier.add(m_fichier_play);

		m_map.add(m_map_nouveau);
		m_map.add(m_map_charger);
		m_map.add(m_map_sauvegarder);

		m_tileset.add(m_tileset_nouveau);
		m_tileset.add(m_tileset_charger);
		m_tileset.add(m_tileset_sauvegarder);
		
		menuBar.add(m_fichier);
		menuBar.add(m_map);
		menuBar.add(m_tileset);
		
		f.setJMenuBar(menuBar);
	}

	@SuppressWarnings("serial")
	private class titreTab extends JPanel{
		public titreTab(String titre, JTabbedPane pane){;
		this.setOpaque(false);
		JLabel texte=new JLabel(titre);
		BoutonIcon bouton=new BoutonIcon("Fermer",Icone.croix);
		texte.setFont(new Font("Calibri", Font.PLAIN, 17));
		bouton.setOveredImg(Icone.croix_selected);
		bouton.setPreferredSize(new Dimension(25,25));
		bouton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int index =pane.indexOfTabComponent(titreTab.this);
				if (index != -1) {
					pane.remove(index);
				}
			}
		});
		this.setPreferredSize(new Dimension(100,25));
		this.setLayout(new BorderLayout());
		this.add(texte, BorderLayout.WEST);
		this.add(bouton, BorderLayout.EAST);
		}
	}

	@SuppressWarnings("serial")
	private class BoutonIcon extends JButton{
		Icon img;

		public BoutonIcon(String overedT, Icon img){
			super(img);
			this.img = img;
			this.setToolTipText(overedT);
			this.setOpaque(false);
			this.setContentAreaFilled(false);
			this.setBorderPainted(false);
			this.setPreferredSize(new Dimension(32,25));
		}

		public void setOveredImg(Icon img){
			this.addMouseListener(new MouseListener() {

				@Override
				public void mouseReleased(MouseEvent e) {

				}

				@Override
				public void mousePressed(MouseEvent e) {

				}

				@Override
				public void mouseExited(MouseEvent e) {
					BoutonIcon.this.setIcon(BoutonIcon.this.img);
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					BoutonIcon.this.setIcon(img);
				}

				@Override
				public void mouseClicked(MouseEvent e) {

				}
			});
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}


}
