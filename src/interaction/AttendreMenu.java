package interaction;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AttendreMenu {
	
	private Attendre fini = null;
	private JDialog fenetre;
	
	public AttendreMenu(JDialog f){
		fenetre = new JDialog(f);
		fenetre.setTitle("Attendre");
		fenetre.setMinimumSize(new Dimension(200,100));
		fenetre.setResizable(false);
	}
	
	public static Attendre menuOpen(JDialog f){
		AttendreMenu m = new AttendreMenu(f);
		return AttendreMenu.menu(m,new Attendre());
	}
	
	public static Attendre menu(AttendreMenu m, Attendre tmp){
		JPanel panel = new JPanel(), p1 = new JPanel(), p2 = new JPanel();
		NumberFormat format = NumberFormat.getIntegerInstance();
		JFormattedTextField temps = new JFormattedTextField(format);
		temps.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				double temps2;
				if (temps.getText().isEmpty())
					temps2 = 0;
				else
					temps2 = Double.parseDouble(temps.getText().replace(",","."));
				
				if(temps2 < 0){
					temps.setText("0");
					temps2 = 0;
				} else if(temps2 > 3600){
					temps.setText("3600");
					temps2 = 3600;
				}
				
				tmp.setSeconde(temps2);
			}
		});
		p1.setLayout(new BorderLayout());
		p1.add(temps,BorderLayout.CENTER);
		p1.add(new JLabel("Secondes"), BorderLayout.EAST);
		
		JButton b_accepter = new JButton("Accepter"), b_annuler = new JButton("Annuler");
		b_accepter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				m.fini = new Attendre();
				m.fini.setSeconde(tmp.getSeconde());
				m.fenetre.dispose();
			}
		});
		b_annuler.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				m.fenetre.dispose();
			}
		});
		p2.setLayout(new GridLayout(1, 2));
		p2.add(b_accepter);
		p2.add(b_annuler);
		
		panel.setLayout(new GridLayout(2, 1));
		panel.add(p1);
		panel.add(p2);
		
		m.fenetre.getContentPane().add(panel);
		m.fenetre.setModal(true);
		m.fenetre.setVisible(true);
		
		return m.fini;
	}
}
