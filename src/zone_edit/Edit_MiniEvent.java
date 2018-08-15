package zone_edit;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import evenement.EventListe;
import evenement.EventX;
import evenement.Factorie_Perso;
import evenement.Perso;
import evenement.Pos;
import fenetre.Event_imageBuild2;
import zone.Map;

@SuppressWarnings("serial")
public class Edit_MiniEvent extends JPanel{
	EventX event;
	JCheckBox b_bou = new JCheckBox("B");
	JCheckBox b_ani = new JCheckBox("A");
	JCheckBox b_sol = new JCheckBox("S");
	

	public Edit_MiniEvent(int x, Perso e, EventListe l){
		this.event = e;
		JPanel right = new JPanel();
		JButton mod = new JButton("Edit"), supr = new JButton("Del");
		
		
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createTitledBorder(event.getNom()));
		this.setPreferredSize(new Dimension(x, 60));
		right.setLayout(new GridLayout(2, 1));
		right.add(mod);
		right.add(supr);
		JLabel image = new JLabel();
		if(event.getImg().getThumb()!=null)
			image = new JLabel(new ImageIcon(event.getImg().getThumb()));
		image.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent e){
				if(event.getImg().getNomT().equals("vide")){
					JFileChooser chooser = new JFileChooser();
					chooser.setCurrentDirectory(new File("charset"));
					FileNameExtensionFilter filter = new FileNameExtensionFilter("Images valide", "jpg", "png");
					chooser.setFileFilter(filter);
					if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
						Event_imageBuild2.charSetParam(chooser.getSelectedFile().getPath(), (Perso)event, new JDialog());
					}
				} else
					Event_imageBuild2.charSetParam("charset"+File.separator+event.getImg().getNomT(), (Perso)event, new JDialog());
			}
			
		});
		mod.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				Factorie_Perso.perso_edit((Perso)e);
			}
		});
		supr.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				l.remove((Perso)e);
			}
		});
		
		this.add(new Menu_MiniEvents(),BorderLayout.CENTER);
		this.add(image,BorderLayout.WEST);
		this.add(right,BorderLayout.EAST);
	}

	private class Menu_MiniEvents extends JPanel{
		public Menu_MiniEvents(){
			this.setLayout(new GridLayout(2, 1));
			JPanel haut = new JPanel(), bas = new JPanel();

			NumberFormat format = NumberFormat.getInstance();
			JFormattedTextField pos_x = new JFormattedTextField(format), pos_y = new JFormattedTextField(format);
			format.setMaximumFractionDigits(3);
			pos_x.setText(""+event.getPos().getX()/Map.TAILLE_TUILE);
			pos_y.setText(""+event.getPos().getY()/Map.TAILLE_TUILE);
			pos_x.addKeyListener(new KeyAdapter() {

				public void keyReleased(KeyEvent e) {
					if (pos_x.getText().isEmpty())
						pos_x.setText("0");
					else if(Integer.parseInt(pos_x.getText()) >= Map.NB_TUILE_TOTAL_X){
						pos_x.setText(Integer.toString(Map.NB_TUILE_TOTAL_X));
					}
					((Perso)event).setPos(new Pos(Integer.parseInt(pos_x.getText())*Map.TAILLE_TUILE,Integer.parseInt(pos_y.getText())*Map.TAILLE_TUILE));
				}

			});
			pos_y.addKeyListener(new KeyAdapter() {

				public void keyReleased(KeyEvent e) {
					if (pos_y.getText().isEmpty())
						pos_y.setText("0");
					else if(Integer.parseInt(pos_y.getText()) >= Map.NB_TUILE_TOTAL_Y){
						pos_y.setText(Integer.toString(Map.NB_TUILE_TOTAL_Y));
					}
					((Perso)event).setPos(new Pos(Integer.parseInt(pos_x.getText())*Map.TAILLE_TUILE,Integer.parseInt(pos_y.getText())*Map.TAILLE_TUILE));
				}

			});
			haut.setLayout(new GridLayout(1, 2));
			JPanel h_1 = new JPanel(),h_2 = new JPanel();
			h_1.setLayout(new BorderLayout());
			h_1.add(new JLabel(" X: "),BorderLayout.WEST);
			h_1.add(pos_x);
			h_2.setLayout(new BorderLayout());
			h_2.add(new JLabel(" Y: "),BorderLayout.WEST);
			h_2.add(pos_y);
			haut.add(h_1);
			haut.add(h_2);

			bas.setLayout(new GridLayout(1, 3));
			if(event.isBouge())
				b_bou.setSelected(true);
			if(event.isAnime())
				b_ani.setSelected(true);
			if(event.isSolide())
				b_sol.setSelected(true);
			b_bou.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					if(e.getStateChange() == ItemEvent.DESELECTED)
						Edit_MiniEvent.this.event.setBouge(false);
					else if(e.getStateChange() == ItemEvent.SELECTED)
						Edit_MiniEvent.this.event.setBouge(true);
				}
			});
			b_ani.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					if(e.getStateChange() == ItemEvent.DESELECTED)
						Edit_MiniEvent.this.event.setAnime(false);
					else if(e.getStateChange() == ItemEvent.SELECTED)
						Edit_MiniEvent.this.event.setAnime(true);
				}
			});
			b_sol.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					if(e.getStateChange() == ItemEvent.DESELECTED)
						Edit_MiniEvent.this.event.setSolide(false);
					else if(e.getStateChange() == ItemEvent.SELECTED)
						Edit_MiniEvent.this.event.setSolide(true);
				}
			});

			bas.add(b_bou);
			bas.add(b_ani);
			bas.add(b_sol);

			this.add(haut);
			this.add(bas);
		}
	}

}
