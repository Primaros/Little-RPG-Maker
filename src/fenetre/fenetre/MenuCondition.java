package fenetre;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import edit_variable.Cond_Position_Menu;
import edit_variable.Cond_Switch_Menu;

public class MenuCondition {
	
	MenuInteraction inter;
	
	public MenuCondition(MenuInteraction p) {
		inter = p;
	}

	public static void openMenu(MenuInteraction p){
		JDialog fenetre = new JDialog();
		JPanel panel = new JPanel();
		
		JButton interupteur = new JButton("Interupteur"), position = new JButton("Position");
		interupteur.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Cond_Switch_Menu.openMenu(fenetre,p);
				}
		});
		position.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Cond_Position_Menu.openMenu(fenetre,p.getP().getEl(),p);
				}
		});
		
		panel.setLayout(new GridLayout());
		panel.add(interupteur);
		panel.add(position);

		System.out.println(p.getP().getEl().size());
		fenetre.getContentPane().add(panel);
		fenetre.setTitle("Conditions");
		fenetre.setMinimumSize(new Dimension(500,200));
		fenetre.setResizable(false);
		fenetre.setModal(true);
		fenetre.setVisible(true);
	}
	
}
