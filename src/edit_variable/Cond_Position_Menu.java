package edit_variable;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import evenement.EventListe;
import evenement.Perso;
import evenement.Pos;
import fenetre.MenuInteraction;
import zone.Map;
import zone_edit.MiniEventAF;

public class Cond_Position_Menu implements Observer{

	MiniEventAF perso;
	Cond_Position cond;
	
	private Perso pers;
	private Pos posi;
	private int num;

	public static Cond_Position openMenu(JDialog f, EventListe l, MenuInteraction i) {
		Cond_Position_Menu tmp = new Cond_Position_Menu();
		return tmp.menu(f,l, i);
	}

	private Cond_Position menu(JDialog f, EventListe ev, MenuInteraction in) {
//		cond = new Cond_Position(new Perso(), new Pos());
		JDialog fenetre = new JDialog(f);
		fenetre.setTitle("Condition Position");
		fenetre.setMinimumSize(new Dimension(230,170));
		fenetre.setResizable(false);
		
		JPanel top = new JPanel(), mid = new JPanel(), bot = new JPanel();
		
		JPanel p_x = new JPanel(), p_y = new JPanel(), p_l = new JPanel();
		top.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		top.setLayout(new BorderLayout());
		top.add(new JLabel(" Emplacement : "),BorderLayout.WEST);
		top.add(p_l);
		p_l.setLayout(new GridLayout(1, 2));
		p_l.add(p_x);
		p_l.add(p_y);
		
		p_x.setLayout(new BorderLayout());
		p_x.add(new JLabel(" X "),BorderLayout.WEST);
		NumberFormat format = NumberFormat.getInstance();
		JFormattedTextField pos_x = new JFormattedTextField(format), pos_y = new JFormattedTextField(format);
		pos_x.setValue(posi);
		pos_y.setValue(posi);
		
		pos_x.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				posi = new Pos(Cond_Position_Menu.getValue(pos_x)*Map.TAILLE_TUILE,Cond_Position_Menu.getValue(pos_y)*Map.TAILLE_TUILE);	
			}
		});
		p_x.add(pos_x,BorderLayout.CENTER);
		
		p_y.setLayout(new BorderLayout());
		p_y.add(new JLabel(" Y "),BorderLayout.WEST);
			pos_y.addKeyListener(new KeyAdapter() {
			
			public void keyReleased(KeyEvent e) {
				posi = new Pos(Cond_Position_Menu.getValue(pos_x)*Map.TAILLE_TUILE,Cond_Position_Menu.getValue(pos_y)*Map.TAILLE_TUILE);	
			}
			
		});
		p_y.add(pos_y,BorderLayout.CENTER);
		
		mid.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		JComboBox<Perso> jcomb = new JComboBox<Perso>();
		perso = new MiniEventAF(0, (Perso)ev.get(0), ev);
		pers = (Perso)ev.get(0);
		for(int i=0; i<ev.nb_event;i++){
			jcomb.addItem((Perso)ev.get(i));
		}
		jcomb.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					perso.setPerso((Perso)e.getItem());
					pers = (Perso)e.getItem();
					num = jcomb.getSelectedIndex();
					mid.repaint();
					fenetre.repaint();
                }
			}
		});
		mid.setLayout(new BorderLayout());
		mid.add(jcomb, BorderLayout.NORTH);
		mid.add(perso,BorderLayout.CENTER);
		
		JButton acc = new JButton("Accepter"), ann = new JButton("Annuler");
		acc.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				cond = new Cond_Position(pers, num, posi);
				in.addI(cond);
				fenetre.dispose();
			}
		});
		ann.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				fenetre.dispose();
			}
		});
		bot.setLayout(new GridLayout(1, 2));
		bot.add(acc);
		bot.add(ann);
		
		fenetre.getContentPane().setLayout(new BorderLayout());
		fenetre.add(top,BorderLayout.NORTH);
		fenetre.add(mid, BorderLayout.CENTER);
		fenetre.add(bot, BorderLayout.SOUTH);
		fenetre.pack();
		fenetre.setVisible(true);
		fenetre.setLocationRelativeTo(null);
		
		return cond;
	}
	
	public static int getValue(JFormattedTextField v){
		if (!v.getText().isEmpty() && Integer.parseInt(v.getText()) >= Map.NB_TUILE_TOTAL_X){
			v.setText(Integer.toString(Map.NB_TUILE_TOTAL_X));
		}
		
		if(!v.getText().isEmpty())
			return Integer.parseInt(v.getText());
		else
			return 0;
			
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
	
}
