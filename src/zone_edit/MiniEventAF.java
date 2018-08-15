package zone_edit;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.text.NumberFormat;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import evenement.EventListe;
import evenement.Perso;
import evenement.Pos;
import fenetre.Event_imageBuild2;
import zone.Map;

@SuppressWarnings("serial")
public class MiniEventAF extends JPanel implements Observer{
	Perso event;
	ImageMini image;
	JPanel mid = new JPanel();
	
	public MiniEventAF(int x, Perso e, EventListe l){
		this.event = e;
		this.event.addObserver(this);
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createTitledBorder(event.getNom()));
		this.setPreferredSize(new Dimension(x, 60));
		image = new ImageMini();
		mid = Menu_MiniEvents();
		this.add(mid,BorderLayout.CENTER);
		this.add(image,BorderLayout.WEST);
	}

	public void setPerso(Perso p){
		this.event = p;
		this.event.addObserver(this);
		this.setBorder(BorderFactory.createTitledBorder(event.getNom()));
		this.removeAll();
		image = new ImageMini();
		mid = Menu_MiniEvents();
		this.add(mid,BorderLayout.CENTER);
		this.add(image,BorderLayout.WEST);
		this.revalidate();
		this.repaint();
	}
	
	public JPanel Menu_MiniEvents(){
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 1));
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
				event.setPos(new Pos(Integer.parseInt(pos_x.getText())*Map.TAILLE_TUILE,Integer.parseInt(pos_y.getText())*Map.TAILLE_TUILE));
			}

		});
		pos_y.addKeyListener(new KeyAdapter() {

			public void keyReleased(KeyEvent e) {
				if (pos_y.getText().isEmpty())
					pos_y.setText("0");
				else if(Integer.parseInt(pos_y.getText()) >= Map.NB_TUILE_TOTAL_Y){
					pos_y.setText(Integer.toString(Map.NB_TUILE_TOTAL_Y));
				}
				event.setPos(new Pos(Integer.parseInt(pos_x.getText())*Map.TAILLE_TUILE,Integer.parseInt(pos_y.getText())*Map.TAILLE_TUILE));
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
		JCheckBox b_bou = new JCheckBox("B");
		JCheckBox b_ani = new JCheckBox("A");
		JCheckBox b_sol = new JCheckBox("S");
		if(event.isBouge())
			b_bou.setSelected(true);
		else
			b_bou.setSelected(false);
		if(event.isAnime())
			b_ani.setSelected(true);
		else
			b_ani.setSelected(false);
		if(event.isSolide())
			b_sol.setSelected(true);
		else
			b_sol.setSelected(false);
		b_bou.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.DESELECTED)
					MiniEventAF.this.event.setBouge(false);
				else if(e.getStateChange() == ItemEvent.SELECTED)
					MiniEventAF.this.event.setBouge(true);
			}
		});
		b_ani.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.DESELECTED)
					MiniEventAF.this.event.setAnime(false);
				else if(e.getStateChange() == ItemEvent.SELECTED)
					MiniEventAF.this.event.setAnime(true);
			}
		});
		b_sol.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.DESELECTED)
					MiniEventAF.this.event.setSolide(false);
				else if(e.getStateChange() == ItemEvent.SELECTED)
					MiniEventAF.this.event.setSolide(true);
			}
		});

		bas.add(b_bou);
		bas.add(b_ani);
		bas.add(b_sol);

		panel.add(haut);
		panel.add(bas);
		
		return panel;
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		this.repaint();
	}
	
	public class ImageMini extends JPanel{
		
		public ImageMini(){
			this.setPreferredSize(new Dimension(50, 50));
			this.addMouseListener(new MouseAdapter() {

				public void mouseClicked(MouseEvent e){
					if(event.getImg().getNomT().equals("vide")){
						JFileChooser chooser = new JFileChooser();
						chooser.setCurrentDirectory(new File("charset"));
						FileNameExtensionFilter filter = new FileNameExtensionFilter("Images valide", "jpg", "png");
						chooser.setFileFilter(filter);
						if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
							Event_imageBuild2.charSetParam(chooser.getSelectedFile().getPath(),event, new JDialog());
						}
					} else
						Event_imageBuild2.charSetParam("charset"+File.separator+event.getImg().getNomT(), event, new JDialog());
				}

			});
		}
		
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			g.drawImage(event.getImg().getImg(), 2, 2, 40,40, null);
		}
	}

}
