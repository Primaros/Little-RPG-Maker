package fenetre;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;
import java.util.ArrayList;

import interaction.AttendreMenu;
import interaction.Camera_Int;
import interaction.ChangeEtat;
import interaction.ChangeRegardMenu;
import interaction.DeplacementMenu;
import interaction.Edit_ImageMenu;
import interaction.Interaction;
import interaction.InterrupteurMenu;
import interaction.InterrupteurMenu_G;
import interaction.Vitesse;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;

import edit_variable.Condition;
import evenement.Factorie_Perso;

public class MenuInteraction {
	private Factorie_Perso p;
	private JDialog fenetre;

	public MenuInteraction(Factorie_Perso p){
		this.p = p;
	}
	
	private JPanel controlEtat(){
		JPanel panel = new JPanel();
		ChangeEtat tmp;
		@SuppressWarnings("serial")
		class JBoutton extends JButton{
			public JBoutton(ChangeEtat ce){
				super();
				if(ce.isEtat_bool())
					this.setBackground(Color.GREEN);
				else
					this.setBackground(Color.RED);
				
				this.setContentAreaFilled(false);
		        this.setOpaque(true);
		        this.setBorder(new BevelBorder(BevelBorder.RAISED));
				ChangeEtat t1 = new ChangeEtat();
				this.addMouseListener(new MouseAdapter() {
					public void mousePressed(MouseEvent e){
						JBoutton.this.setBorder(new BevelBorder(BevelBorder.LOWERED));
					}
					public void mouseReleased(MouseEvent e){
						JBoutton.this.setBorder(new BevelBorder(BevelBorder.RAISED));
						t1.set(ce.getEtat(),ce.isEtat_bool());
						MenuInteraction.this.addI(t1);
					}
				});
			}
		}
		
		JBoutton ao, ai, bo, bi, so, si;
		tmp = new ChangeEtat();
		tmp.set(tmp.ANIME, false);
		ao = new JBoutton(tmp);
		tmp = new ChangeEtat();
		tmp.set(tmp.ANIME, true);
		ai = new JBoutton(tmp);
		tmp = new ChangeEtat();
		tmp.set(tmp.BOUGE, false);
		bo = new JBoutton(tmp);
		tmp = new ChangeEtat();
		tmp.set(tmp.BOUGE, true);
		bi = new JBoutton(tmp);
		tmp = new ChangeEtat();
		tmp.set(tmp.SOLIDE, false);
		so = new JBoutton(tmp);
		tmp = new ChangeEtat();
		tmp.set(tmp.SOLIDE, true);
		si = new JBoutton(tmp);
		
		panel.setLayout(new GridLayout(3,3,2,2));

		panel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		panel.add(new JLabel("A",JLabel.CENTER));
		panel.add(new JLabel("B",JLabel.CENTER));
		panel.add(new JLabel("S",JLabel.CENTER));
		panel.add(ai);
		panel.add(bi);
		panel.add(si);
		panel.add(ao);
		panel.add(bo);
		panel.add(so);
		
		JLabel v = new JLabel("Vitesse",JLabel.CENTER);
		JFormattedTextField vitesseV = new JFormattedTextField(NumberFormat.getInstance());
		JButton vitesse_valid = new JButton("OK");
		vitesseV.setPreferredSize(new Dimension(25, 20));
		vitesseV.setText(Integer.toString(06));
		vitesseV.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent k){
				Vitesse v;
				if (vitesseV.getText().isEmpty())
					v = new Vitesse(0);
				else
					v = new Vitesse(Integer.parseInt(vitesseV.getText()));
				if (!v.valide()){
					if (v.getVitesse()>=Vitesse.MAX)
						vitesseV.setText(Integer.toString(Vitesse.MAX));
					else if (v.getVitesse()<=Vitesse.MIN)
						vitesseV.setText(Integer.toString(Vitesse.MIN));
				}
			}
		});
		JPanel panelV = new JPanel();
//		panelV.setLayout(new GridLayout(2,1));
	    panelV.add(v);
		panelV.add(vitesseV);
		JPanel panelVF = new JPanel();
		panelVF.setLayout(new BorderLayout());
		panelVF.add(panelV,BorderLayout.CENTER);
		panelVF.add(vitesse_valid, BorderLayout.EAST);
		vitesse_valid.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MenuInteraction.this.addI(new Vitesse(Integer.parseInt(vitesseV.getText())));
			}
		});
		panelVF.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		JPanel panelVF1 = new JPanel(), panelVF1_1 = new JPanel();
		panelVF1.add(new JLabel("Visible", JLabel.CENTER));
		panelVF1.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		panelVF1.setLayout(new GridLayout(1, 2));
		panelVF1_1.setLayout(new GridLayout(1, 2,2,2));
		JBoutton vo,vi;
		tmp = new ChangeEtat();
		tmp.set(tmp.VISIBLE, false);
		vo = new JBoutton(tmp);
		tmp = new ChangeEtat();
		tmp.set(tmp.VISIBLE, true);
		vi = new JBoutton(tmp);
		panelVF1_1.add(vi);
		panelVF1_1.add(vo);
		panelVF1.add(panelVF1_1);
		JPanel panelVF2 = new JPanel();
		panelVF2.setLayout(new GridLayout(2, 1));
		panelVF2.add(panelVF);
		panelVF2.add(panelVF1);
		JPanel pfinal = new JPanel();
		pfinal.setLayout(new GridLayout(2, 1));
		pfinal.add(panel);
		pfinal.add(panelVF2);
		JPanel pf = new JPanel();
		pf.setLayout(new BorderLayout());
		pf.add(pfinal,BorderLayout.CENTER);
		return pf;
	}
	
	public void openMenu(){
		this.fenetre = new JDialog(this.getP().getFenetre());
		JPanel panel = new JPanel(), panel_haut_droite = new JPanel(), panel_bas_gauche = new JPanel();

		JButton deplacement = new JButton("D�placement");
		deplacement.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MenuInteraction.this.addI(DeplacementMenu.menuOpen(fenetre));
			}
		});
		
		JButton edit_image = new JButton("Image");
		edit_image.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MenuInteraction.this.addI(Edit_ImageMenu.menuOpen(fenetre));
			}
		});
		
		JButton wait = new JButton("Attendre");
		wait.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MenuInteraction.this.addI(AttendreMenu.menuOpen(fenetre));
			}
		});
		
		JButton interrupteurs = new JButton("Interupteurs");
		interrupteurs.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MenuInteraction.this.addI(InterrupteurMenu.menuOpen(fenetre,p.getP()));
			}
		});
		
		JButton interrupteurs_G = new JButton("Interupteurs G�n�ral");
		interrupteurs_G.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MenuInteraction.this.addI(InterrupteurMenu_G.menuOpen(fenetre,p.getEl()));
			}
		});
		
		JButton camera = new JButton("Cam�ra");
		camera.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MenuInteraction.this.addI(new Camera_Int());
			}
		});
		
		JButton regard = new JButton("Direction");
		regard.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MenuInteraction.this.addI(ChangeRegardMenu.menuOpen(fenetre));
			}
		});
		
		panel_haut_droite.setLayout(new GridLayout(3, 1, 0, 2));
		panel_haut_droite.add(edit_image);
		panel_haut_droite.add(camera);
		panel_haut_droite.add(interrupteurs);
		panel_bas_gauche.setLayout(new GridLayout(3, 1, 0, 2));
		panel_bas_gauche.add(wait);
		panel_bas_gauche.add(regard);
		panel_bas_gauche.add(interrupteurs_G);
		panel.setLayout(new GridLayout(2,2,2,2));
		panel.add(deplacement);
		panel.add(panel_haut_droite);
		panel.add(panel_bas_gauche);
		panel.add(controlEtat());

		this.fenetre.getContentPane().add(panel);
		this.fenetre.setTitle("Actions");
		this.fenetre.setMinimumSize(new Dimension(300,300));
		this.fenetre.setResizable(false);
		this.fenetre.setModal(true);
		this.fenetre.setVisible(true);
	}

	public void addI(Interaction i){
		if(i!=null){
			getP().addInteraction(i); 
			Object[] data = new Object[3];
			data[0] = getP();
			data[1] = this.getCondCombo();
			data[2] = "X";
			((DefaultTableModel)getP().t.getModel()).addRow(data);
		}
	}

	public void modI(int j,Interaction i){
		getP().getInteractions().set(j,i);
		Object[] data = new Object[3];
		data[0] = getP();
		data[1] = this.getCondCombo();
		data[2] = "X";
		((DefaultTableModel)getP().t.getModel()).setValueAt(data[0], j, 0);
		((DefaultTableModel)getP().t.getModel()).setValueAt(data[1], j, 1);
		((DefaultTableModel)getP().t.getModel()).setValueAt(data[2], j, 2);

	}

	public void supI(int i){
		getP().getInteractions().remove(i);
		((DefaultTableModel)getP().t.getModel()).removeRow(i);
	}

	public JComboBox<Condition> getCondCombo(){
		JComboBox<Condition> b =new JComboBox<Condition>();
		b.addItem(null);
		for (int i=0;i<this.getP().getInteractions().size();i++){
			if(this.getP().getInteractions().get(i).getT().equals("Condition")){
				b.addItem(((Condition)this.getP().getInteractions().get(i)));
			}
		}
		return b;
	}
	
	public Condition getCond(int tmp){
		for (int i=0;i<this.getP().getInteractions().size();i++){
			if(this.getP().getInteractions().get(i).getT().equals("Condition")){
				if(((Condition)this.getP().getInteractions().get(i)).getNum() == tmp){
					return ((Condition)this.getP().getInteractions().get(i));
				}
			}
		}
		return null;
	}
	
	public ArrayList<Condition> getCondListe(){
		ArrayList<Condition> tmp = new ArrayList<Condition>();
		for (int i=0;i<this.getP().getInteractions().size();i++){
			if(this.getP().getInteractions().get(i).getT().equals("Condition")){
					tmp.add((Condition)this.getP().getInteractions().get(i));
			}
		}
		return tmp;
	}
	
	public Factorie_Perso getP() {
		return p;
	}

}
