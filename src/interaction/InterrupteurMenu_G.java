package interaction;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import edit_variable.Switch_G;
import evenement.EventListe;

public class InterrupteurMenu_G {
	
	private Interrupteurs_G fini = null;
	private JDialog fenetre;
	
	public InterrupteurMenu_G(JDialog f){
		fenetre = new JDialog(f);
		fenetre.setTitle("Interrupteur");
		fenetre.setMinimumSize(new Dimension(340,100));
		fenetre.setResizable(false);
	}
	
	public static Interrupteurs menuOpen(JDialog f, EventListe p){
		InterrupteurMenu_G m = new InterrupteurMenu_G(f);
		return InterrupteurMenu_G.menu(m,p);
	}
	
	public static Interrupteurs_G menu(InterrupteurMenu_G m, EventListe p) {
		
		JPanel panel = new JPanel(), p1 = new JPanel(), p2 = new JPanel(), p3 = new JPanel();
		Interrupteurs_G tmp = new Interrupteurs_G();
		JComboBox<Switch_G> interupteur = new JComboBox<Switch_G>();
		for(int i=0;i<p.getSwitchG().size();i++)
			interupteur.addItem(p.getSwitchG().get(i));
		
		if (tmp.valide())
			interupteur.setSelectedIndex(tmp.getInt_num());
		p1.setLayout(new BorderLayout());
		p1.add(interupteur,BorderLayout.EAST);
		interupteur.setPreferredSize(new Dimension(160,25));
		String[] val = {"Activer","Désactiver"};
		JComboBox<String> valeur = new JComboBox<String>(val);
		if (!tmp.isInt_val())
			valeur.setSelectedIndex(1);
		p2.add(interupteur);
		p2.add(valeur);
		
		p2.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		
		
		JButton b_accepter = new JButton("Accepter"), b_annuler = new JButton("Annuler");
		
		b_accepter.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (interupteur.getSelectedItem() != null){
				boolean actif = false;
				if (((String)valeur.getSelectedItem()).equals("Activer"))
					actif = true;
				tmp.set(((Switch_G)interupteur.getSelectedItem()).getId(),actif);}
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
		
		p3.setLayout(new GridLayout(1, 2));
		p3.add(b_accepter);
		p3.add(b_annuler);
		
		panel.setLayout(new GridLayout(2,1));
		panel.add(p2);
		panel.add(p3);
		
		p3.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		
		m.fenetre.getContentPane().add(panel);
		m.fenetre.setModal(true);
		m.fenetre.setVisible(true);
		return m.fini;
	}
}
