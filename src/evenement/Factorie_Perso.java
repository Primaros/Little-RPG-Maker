package evenement;

import interaction.Interaction;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import tableau.ButtonEditorInteraction;
import tableau.ButtonEditorSup;
import tableau.ButtonRenderer;
import tableau.CellEditorCondition;
import tableau.ComboRenderer;
import tableau.ZModel;
import zone.Map;
import edit_variable.Condition;
import edit_variable.Switch;
import fenetre.Event_imageBuild2;
import fenetre.MenuCondition;
import fenetre.MenuInteraction;
import fenetre.MenuInterupteur2;

public class Factorie_Perso {

	@SuppressWarnings("serial")
	private class Factorie_Perso_Pic extends JPanel implements Observer {
		
		public Factorie_Perso_Pic(Perso p){
			p.addObserver(this);
		}

		@Override
		public void update(Observable o, Object arg) {
			this.repaint();
		}
		
		public void paintComponent(Graphics g){
			g.clearRect(0, 0, this.getWidth(), this.getHeight());
			BufferedImage tmp = p.getImg().getThumb();
			if(p.getImg().getThumb()!=null){
				int x,y;
				if(tmp.getHeight()>=tmp.getWidth()){
					y = this.getHeight()/2;
					x = tmp.getWidth()*y/tmp.getHeight();
				}
				else {
					x = this.getWidth()/2;
					y = tmp.getHeight()*x/tmp.getWidth();
				}
				g.drawImage(tmp, this.getWidth()/2-x/2, this.getHeight()/2-y/2, x, y, null);
			}
		}
		
	}

	private JDialog f;
	MenuInteraction mi;
	public JTable t;
	private Perso p = null;
	JTextField pos_x,pos_y,nom;
	private EventListe el;
	ArrayList<Object> original = null;
	
	public MenuInteraction getMi() {
		return mi;
	}
	
	public JPanel panel;

	public Factorie_Perso(EventListe t){
		this.setEl(t);
		this.init();
	}
	
	public Factorie_Perso(Perso p){
		this.p = p;
		this.init();
	}

	public void init(){
		if(this.p != null){
			this.original = p.getPersoList();	
			}
		else
			this.p = this.getEl().createPerso();
		this.f = new JDialog(new JFrame(),"Evenement");
		this.panel = new JPanel();
		this.mi = new MenuInteraction(this);
		Switch.NB = p.getInterupteurs().size();

		JPanel j_h = new JPanel(), j_b = new JPanel(), j_c = new JPanel(), j_f = new JPanel();
		// Initialisation de la partie haute et basse de l'interface
		j_h.setLayout(new BoxLayout(j_h, BoxLayout.LINE_AXIS));
		j_h.add(menu_param_graphic(210));
		j_b.setLayout(new BoxLayout(j_b, BoxLayout.LINE_AXIS));
		JPanel tableau = menu_param_actions(650-250);
		tableau.setBorder(BorderFactory.createTitledBorder("Actions"));
		j_b.add(tableau);

		JButton b_accepter = new JButton("Accepter"), b_annuler = new JButton("Annuler");

		j_f.setLayout(new GridLayout(1,2));

		j_f.add(b_accepter);
		j_f.add(b_annuler);
		b_accepter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Factorie_Perso.this.f.dispose();
			}
		});
		b_annuler.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(original != null){
					p.setPersoList(original);
				}
				else
					Factorie_Perso.this.getEl().remove(p);
				Factorie_Perso.this.f.dispose();
			}
		});

		JLabel c = new JLabel("Créer votre évent");
		j_c.add(c);

		panel.setLayout(new BoxLayout( panel, BoxLayout.PAGE_AXIS));

		panel.add(j_h);
		panel.add(j_b);
		panel.add(j_c);
		panel.add(j_f);
		this.f.getContentPane().add(panel);
		this.f.setPreferredSize(new Dimension(400,600));
		this.f.setMinimumSize(new Dimension(400,410));
		this.f.pack();
		this.f.setVisible(true);
	}

	public ArrayList<Interaction> getInteractions(){
		return getP().getActions();
	}

	public ArrayList<Condition> getCondition(){
		ArrayList<Interaction> tmp = getInteractions();
		ArrayList<Condition> result = new ArrayList<Condition>();

		for (int i =0; i < result.size(); i++){
			if (tmp.get(i).getClass().equals(Condition.class))
				result.add((Condition)tmp.get(i));
		}

		return result;
	}

	public ArrayList<Switch> getInterupteurs(){
		return getP().getInterupteurs();
	}

	public static void perso_create(EventListe t){
		new Factorie_Perso(t);
	}
	
	public static void perso_edit(Perso p){
		new Factorie_Perso(p);
	}

	public void addInteraction(Interaction i){
		if (i!= null)
			this.p.addInteraction(i);
	}

	public Perso getP() {
		return p;
	}

	private JPanel menu_param_graphic_img(){
		JPanel p = new JPanel();
		p.setBorder(BorderFactory.createTitledBorder("Image"));
		p.setLayout(new BorderLayout());
		Factorie_Perso_Pic p_image = new Factorie_Perso_Pic(getP());

		JButton boutton = new JButton("Charger");
		boutton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new File("charset"));
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Images valide", "jpg", "png");
				chooser.setFileFilter(filter);
				if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
					Event_imageBuild2.charSetParam(chooser.getSelectedFile().getPath(), getP(), Factorie_Perso.this.f);
				}
			}
		});
		p.setLayout(new BorderLayout());
		p.add(boutton,BorderLayout.SOUTH);
		p.add(p_image,BorderLayout.CENTER);
		return p;
	}

	private JPanel menu_param_graphic_param(){
		JPanel p = new JPanel();
		p.setBorder(BorderFactory.createTitledBorder("Param"));

		p.setLayout(new GridLayout(6,1));
		JPanel d_1 = new JPanel();
		JLabel l_nom = new JLabel("NOM : ");
		nom = new JFormattedTextField(""+getP().getNom());
		nom.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) { 
				if(nom.getText().isEmpty())
					getP().setNom(Factorie_Perso.this.p.getNumerot());
				else{
					if (nom.getText().length() >= 20 ) 
						nom.setText(nom.getText().substring(0, 20));
				
					getP().setNom(nom.getText());
				}
			}  
		});
		d_1.setLayout(new BorderLayout());
		d_1.add(l_nom,BorderLayout.WEST);
		d_1.add(nom,BorderLayout.CENTER);

		JPanel d_2 = new JPanel();
		d_2.setLayout(new GridLayout(1,3));
		JLabel l_x = new JLabel("X :"), l_y = new JLabel("Y :");
		NumberFormat format = NumberFormat.getInstance();
		pos_x = new JFormattedTextField(format);
		pos_y = new JFormattedTextField(format);
		JButton b = new JButton();
		format.setMaximumFractionDigits(3);
		pos_x.setPreferredSize(new Dimension(13*3, 20));
		pos_x.setText(""+getP().getPos().x/Map.TAILLE_TUILE);
		pos_y.setPreferredSize(new Dimension(13*3, 20));
		pos_y.setText(""+getP().getPos().y/Map.TAILLE_TUILE);
		b.setPreferredSize(new Dimension(10*7, 20));

		pos_x.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) { 
				if (pos_x.getText().isEmpty())
					pos_x.setText("0");
				else if(Integer.parseInt(pos_x.getText()) >= Map.NB_TUILE_TOTAL_X){
					pos_x.setText(Integer.toString(Map.NB_TUILE_TOTAL_X));
				}
				getP().setPos(new Pos(Integer.parseInt(pos_x.getText())*Map.TAILLE_TUILE,Integer.parseInt(pos_y.getText())*Map.TAILLE_TUILE));
			}  
		});
		pos_y.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) { 
				if (pos_y.getText().isEmpty())
					pos_y.setText("0");
				else if(Integer.parseInt(pos_y.getText()) >= Map.NB_TUILE_TOTAL_Y){
					pos_y.setText(Integer.toString(Map.NB_TUILE_TOTAL_Y));
				}
				getP().setPos(new Pos(Integer.parseInt(pos_x.getText())*Map.TAILLE_TUILE,Integer.parseInt(pos_y.getText())*Map.TAILLE_TUILE));
			}  
		});

		JPanel d_2_x = new JPanel(), d_2_y = new JPanel(), d_2_v = new JPanel();
		d_2_x.add(l_x);
		d_2_x.add(pos_x);
		d_2.add(d_2_x);
		d_2_y.add(l_y);
		d_2_y.add(pos_y);
		d_2.add(d_2_y);
		d_2_v.add(b);
		d_2.add(d_2_v);

		JPanel d_3 = new JPanel();
		d_3.setLayout(new GridLayout(1,3));

		JCheckBox b_bou = new JCheckBox("Bouge");
		JCheckBox b_ani = new JCheckBox("Animé");
		JCheckBox b_sol = new JCheckBox("Solide");

		b_bou.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.DESELECTED)
					getP().setBouge(false);
				else if(e.getStateChange() == ItemEvent.SELECTED)
					getP().setBouge(true);
			}
		});
		b_ani.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.DESELECTED)
					getP().setAnime(false);
				else if(e.getStateChange() == ItemEvent.SELECTED)
					getP().setAnime(true);
			}
		});
		b_sol.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.DESELECTED)
					getP().setSolide(false);
				else if(e.getStateChange() == ItemEvent.SELECTED)
					getP().setSolide(true);
			}
		});

		if(getP().isBouge())
			b_bou.setSelected(true);
		if(getP().isAnime())
			b_ani.setSelected(true);
		if(getP().isSolide())
			b_sol.setSelected(true);
		d_3.add(b_bou);
		d_3.add(b_ani);
		d_3.add(b_sol);

		JPanel d_4 = new JPanel();
		d_4.setLayout(new BorderLayout());
		JLabel l_vit = new JLabel("Vitesse :  ");
		int i[] = {2,4,5,6,8};
		JComboBox<String> cb_vit = new JComboBox<String>(new String[] {"TrÃ¨s lent ("+i[0]+")", "Lent ("+i[1]+")", "Normal ("+i[2]+")", "Rapide ("+i[3]+")", "TrÃ¨s Rapide ("+i[4]+")"});
		cb_vit.addActionListener(new ActionListener() {

			@SuppressWarnings("unchecked")
			@Override
			public void actionPerformed(ActionEvent e) {
				int tmp = Factorie_Perso.this.vitesseConv(((JComboBox<String>) e.getSource()).getSelectedIndex(), 0);
				getP().setVitesse(tmp);
			}
		});
		cb_vit.setSelectedIndex(this.vitesseConv(getP().getVitesse(), 1));
		d_4.add(l_vit,BorderLayout.WEST);
		d_4.add(cb_vit,BorderLayout.CENTER);

		p.add(d_1);
		p.add(d_2);
		p.add(d_3);
		p.add(d_4);
		return p;
	}

	private int vitesseConv(int i, int type){
		int tmp = 0;
		if(type==0){
			switch(i){
			case(0):
				tmp=2;
			break;
			case(1):
				tmp=4;
			break;
			case(2):
				tmp=5;
			break;
			case(3):
				tmp=6;
			break;
			case(4):
				tmp=8;
			break;
			}
		} else {
			switch(i){
			case(2):
				tmp=0;
			break;
			case(4):
				tmp=1;
			break;
			case(5):
				tmp=2;
			break;
			case(6):
				tmp=3;
			break;
			case(8):
				tmp=4;
			break;
			}
		}

		return tmp;
	}

	private JPanel menu_param_graphic(int y){
		JPanel j_h = new JPanel();
		j_h.setLayout(new BoxLayout(j_h, BoxLayout.LINE_AXIS));
		j_h.setPreferredSize(new Dimension(450,y));
		JPanel j_g = menu_param_graphic_img(), j_d = menu_param_graphic_param();
		j_h.add(j_g);
		j_g.setPreferredSize(new Dimension(150,y));
		j_d.setPreferredSize(new Dimension(450-150,y));
		j_h.add(j_g);
		j_h.add(j_d);
		return j_h;
	}

	private JPanel menu_param_actions(int y){
		MenuInterupteur2 mi = new MenuInterupteur2(this.f, this.p);
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout());
		p.setPreferredSize(new Dimension(450,y));

		JPanel p_1 = new JPanel(), p_2 = new JPanel(),p_3 = new JPanel();
		p_1.setLayout(new GridLayout(1,2));

		JButton b_action = new JButton("Nouvelle Action"), b_cond = new JButton("Nouvelle Condition");
		b_action.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Factorie_Perso.this.mi.openMenu();
			}
		});
		b_cond.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MenuCondition.openMenu(Factorie_Perso.this.mi);
			}
		});
		p_1.add(b_action);
		p_1.add(b_cond);

		this.t = menu_param_actions_tab();
		this.t.setMaximumSize(new Dimension(200,y-p_1.getHeight()-100));
		p_2.setLayout(new BorderLayout());
		p_2.add(new JScrollPane(this.t));
		p_2.setMaximumSize(new Dimension(200,y-p_1.getHeight()-100));

		JButton b_interupteur = new JButton("Interupteurs");
		b_interupteur.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mi.openMenu();
			}
		});
		p_3.setLayout(new GridLayout(1,1));
		p_3.add(b_interupteur);


		p.add(p_1,BorderLayout.NORTH);
		p.add(p_2,BorderLayout.CENTER);
		p.add(p_3,BorderLayout.SOUTH);

		return p;
	}

	private JTable menu_param_actions_tab(){

		int taille_x = 360, tailleMin = 30;
		Object[][] data = new Object[getP().getActions().size()][3];
		JComboBox<Integer> b =new JComboBox<Integer>();
		b.addItem(0);
		for (int i=0;i<getP().getActions().size();i++){
			if(getP().getActions().get(i).getClass() == Condition.class){
				b.addItem(((Condition)getP().getActions().get(i)).getNum());
			}
		}
		for(int i=0;i<getP().getActions().size();i++){
			data[i][0] = getP().getActions().get(i).toString();
			data[i][1] = b;
			data[i][2] = "X";
		}
		String  title[] = {"Action", "Cond", "Del"};
		JTable tableau=new JTable(new ZModel(data, title, this.mi));
		tableau.setRowHeight(25);


		tableau.getColumn("Cond").setPreferredWidth(tailleMin);
		tableau.getColumn("Del").setPreferredWidth(tailleMin);
		tableau.getColumn("Action").setPreferredWidth(taille_x-tailleMin*2);
		for(int i=0;i<3;i++){
			tableau.getColumnModel().getColumn(i).setMaxWidth(taille_x-tailleMin*2);
			tableau.getColumnModel().getColumn(i).setMinWidth(tailleMin);
		}

		tableau.getColumn("Action").setCellRenderer(new ButtonRenderer());
		tableau.getColumn("Action").setCellEditor(new ButtonEditorInteraction(this.mi));
		tableau.getColumn("Cond").setCellRenderer(new ComboRenderer(this.mi));
		tableau.getColumn("Cond").setCellEditor(new CellEditorCondition(new JCheckBox(),this.mi));
		tableau.getColumn("Del").setCellRenderer(new ButtonRenderer());
		tableau.getColumn("Del").setCellEditor(new ButtonEditorSup(new JCheckBox(),this));

		return tableau;
	}

	public JDialog getFenetre(){
		return this.f;
	}

	public EventListe getEl() {
		return el;
	}

	public void setEl(EventListe el) {
		this.el = el;
	}


}
