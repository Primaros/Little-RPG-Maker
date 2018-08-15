package interaction;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

public class ChangeRegardMenu {
	private ChangeRegard fini = null;
	private JDialog fenetre;
	
	public ChangeRegardMenu(JDialog f){
		fenetre = new JDialog(f);
		fenetre.setTitle("Changer la direction");
		fenetre.setMinimumSize(new Dimension(200,170));
		fenetre.setResizable(false);
	}
	
	public static ChangeRegard menuOpen(JDialog f){
		ChangeRegardMenu m = new ChangeRegardMenu(f);
		return ChangeRegardMenu.menu(m,new ChangeRegard());
	}
	
	public static ChangeRegard menu(ChangeRegardMenu m, ChangeRegard tmp){
		JPanel panel = new JPanel(), p1 = new JPanel(), p2 = new JPanel();
		JButton haut = new JButton("HAUT"), bas = new JButton("BAS"), gauche = new JButton("GAUCHE"), droite = new JButton("DROITE");
		
		haut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tmp.set(tmp.HAUT);
			}
		});
		bas.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tmp.set(tmp.BAS);
			}
		});
		gauche.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tmp.set(tmp.GAUCHE);
			}
		});
		droite.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tmp.set(tmp.DROITE);
			}
		});
		
		
		p1.setLayout(new BorderLayout());
		p1.add(haut,BorderLayout.NORTH);
		p1.add(bas,BorderLayout.SOUTH);
//		p1.add(new JButton(), BorderLayout.CENTER);
		p1.add(gauche,BorderLayout.WEST);
		p1.add(droite,BorderLayout.EAST);
		
		
		JButton b_accepter = new JButton("Accepter"), b_annuler = new JButton("Annuler");
		b_accepter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				m.fini = new ChangeRegard();
				m.fini.set(tmp.get());
				m.fenetre.dispose();
			}
		});
		b_annuler.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				m.fenetre.dispose();
			}
		});
		JPanel p2_1 = new JPanel();
		p2_1.setLayout(new GridLayout(1, 2));
		p2_1.add(b_accepter);
		p2_1.add(b_annuler);
		p2.setLayout(new BorderLayout());
		p2.add(new JPanel(),BorderLayout.NORTH);
		p2.add(p2_1,BorderLayout.CENTER);
		
		panel.setLayout(new BorderLayout());
		panel.add(p1,BorderLayout.CENTER);
		panel.add(p2,BorderLayout.SOUTH);
		
		m.fenetre.getContentPane().add(panel);
		m.fenetre.setModal(true);
		m.fenetre.setVisible(true);
		
		return m.fini;
	}
}
