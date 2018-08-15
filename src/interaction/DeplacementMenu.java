package interaction;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EtchedBorder;

public class DeplacementMenu {
	
	private Deplacement fini = null;
	private JDialog fenetre;
	
	public DeplacementMenu(JDialog f){
		fenetre = new JDialog(f);
		fenetre.setTitle("Déplacement");
		fenetre.setMinimumSize(new Dimension(300,150));
		fenetre.setResizable(false);
		
	}
	
	public static Deplacement menuOpen(JDialog f){
		DeplacementMenu m = new DeplacementMenu(f);
		return DeplacementMenu.menu(m,new Deplacement(0, 0));
	}
	
	public static Deplacement menuOpen(JDialog f, Deplacement tmp){
		DeplacementMenu m = new DeplacementMenu(f);
		m.fini = tmp;
		Deplacement tmp2 = new Deplacement(tmp.getX(), tmp.getY(), tmp.getType());
		return DeplacementMenu.menu(m,tmp2);
	}
	
	public static Deplacement menu(DeplacementMenu m, Deplacement tmp) {
		
		JPanel panel = new JPanel(), p1 = new JPanel(), p2 = new JPanel(), p3 = new JPanel();
		
		JLabel txtIntro_1 = new JLabel("Référentiel : ");
		ButtonGroup groupB = new ButtonGroup();
		JRadioButton b_Map = new JRadioButton("Map");
		JRadioButton b_Perso = new JRadioButton("Event");
		
		b_Map.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				tmp.setType(Deplacement.MAP);
			}
		});
		b_Perso.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				tmp.setType(Deplacement.INCREMENT);
			}
		});
		
		groupB.add(b_Map);
		groupB.add(b_Perso);
		
		p1.setLayout(new GridLayout(1,3));
		p1.add(txtIntro_1);
		p1.add(b_Map);
		p1.add(b_Perso);
		if(tmp.getType() == Deplacement.MAP)
			b_Map.setSelected(true);
		else
			b_Perso.setSelected(true);
		
		JLabel txtIntro_2 = new JLabel("Position : "), l_x  = new JLabel("X : "), l_y  = new JLabel("Y : ");
		JPanel pp1 = new JPanel(), pp2 = new JPanel();
		NumberFormat formatPos = NumberFormat.getIntegerInstance();
		JFormattedTextField pos_x = new JFormattedTextField(formatPos);
		JFormattedTextField pos_y = new JFormattedTextField(formatPos);
		pos_x.setPreferredSize(new Dimension(10*4, 20));
		pos_x.setText(""+tmp.getX());
		pos_y.setPreferredSize(new Dimension(10*4, 20));
		pos_y.setText(""+tmp.getY());
		
		p2.setLayout(new GridLayout(1,3));
		p2.add(txtIntro_2);
		pp1.add(l_x);
		pp1.add(pos_x);
		pp2.add(l_y);
		pp2.add(pos_y);
		p2.add(pp1);
		p2.add(pp2);
		
		JButton b_accepter = new JButton("Accepter"), b_annuler = new JButton("Annuler");
		
		b_accepter.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				tmp.setX(Integer.parseInt(pos_x.getText()));
				tmp.setY(Integer.parseInt(pos_y.getText()));
				if (tmp.valide()){
					m.fini = tmp;
					m.fenetre.dispose();
				}
					
			}
		});
		
		b_annuler.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				m.fenetre.dispose();
			}
		});
		
		p3.setLayout(new GridLayout(1,2));
		p3.add(b_accepter);
		p3.add(b_annuler);
		
		panel.setLayout(new GridLayout(3,1));
		panel.add(p1);
		panel.add(p2);
		panel.add(p3);
		
		p1.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		p2.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
//		p3.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		
		m.fenetre.getContentPane().add(panel);
		m.fenetre.repaint();
		m.fenetre.setModal(true);
		m.fenetre.setVisible(true);
		return m.fini;
	}
}
