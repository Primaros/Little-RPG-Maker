package edit_variable;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import fenetre.MenuInteraction;

public class Cond_Switch_Menu {	
	
	public static void openMenu(JDialog f, MenuInteraction c) {
		
		JDialog fenetre;
		fenetre = new JDialog(f);
		fenetre.setTitle("Condition Interupteur");
		fenetre.setMinimumSize(new Dimension(420,126));
		fenetre.setResizable(false);
		JPanel panel = new JPanel(), p1 = new JPanel(), p2 = new JPanel(), p3 = new JPanel(), p4 = new JPanel();
		
		JLabel txt_1 = new JLabel("Si l'interupteur");
		JComboBox<Switch> interupteur = new JComboBox<Switch>();
		for(int i=0;i<c.getP().getInterupteurs().size();i++){
			interupteur.addItem(c.getP().getInterupteurs().get(i));
		}
		p3.add(txt_1);
		p3.add(interupteur);
		interupteur.setPreferredSize(new Dimension(160,25));
		JLabel txt_2 = new JLabel("est ");
		String[] val = {"activer","désactiver"};
		JComboBox<String> valeur = new JComboBox<String>(val);
		p4.add(txt_2);
		p4.add(valeur);
		
//		p1.setLayout(new GridLayout(1,2));
		p1.add(p3);
		p1.add(p4);
		
		JButton b_accepter = new JButton("Accepter"), b_annuler = new JButton("Annuler");
		
		b_accepter.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Cond_Switch cond;
				if((Switch)interupteur.getSelectedItem() != null){
					if(((String)valeur.getSelectedItem()).equals("activer"))
						cond = new Cond_Switch_Actif((Switch)interupteur.getSelectedItem());
					else
						cond = new Cond_Switch_InActif((Switch)interupteur.getSelectedItem());
					
					c.addI(cond);
					fenetre.dispose();
				}
			}
		});
		
		b_annuler.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				fenetre.dispose();
			}
		});
		
		p2.add(b_accepter);
		p2.add(b_annuler);
		p1.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
//		p2.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		panel.setLayout(new GridLayout(2,1));
		panel.add(p1);
		panel.add(p2);
		
		fenetre.getContentPane().add(panel);
		fenetre.setModal(true);
		fenetre.setVisible(true);
	}
	
}
