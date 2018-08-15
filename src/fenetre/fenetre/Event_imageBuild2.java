package fenetre;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.text.NumberFormat;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;

import evenement.EventImg2;
import evenement.EventX;
import evenement.Perso;
import evenement.Pos;
import outils.Pos2;

public class Event_imageBuild2 {
	private Event_imageIMG img;
	private Perso p;
	private String nomImg;
	private EventImg2 image;
	int direction = EventImg2.BAS;
	PanelAnime tmp;
	
	public static void charSetParam(String chemin,Perso p, JDialog f){
		Event_imageBuild2 tmp = new Event_imageBuild2(chemin,p);
		tmp.showWindow(f);
	}
	
	public Event_imageBuild2(String chemin,Perso p){
		this.img = new Event_imageIMG(chemin);
		this.p = p;
		this.image = new EventImg2(chemin);
		image.lookAt(EventImg2.BAS);
		tmp = new PanelAnime(image);
		this.nomImg = chemin.split("\\.tileset")[0];
		this.nomImg = this.nomImg.split(Pattern.quote(File.separator))[this.nomImg.split(Pattern.quote(File.separator)).length-1];
	}
	
	public void showWindow(JDialog f){
		JDialog d = new JDialog(f);
		JPanel p = new JPanel(), south = new JPanel(), east = new JPanel();
		JButton b_annuler = new JButton("Annuler"), b_add = new JButton("Add"), b_save = new JButton("Save"), b_addAll = new JButton("Add All");
		
		JPanel p_taille_x = new JPanel(), p_taille_y = new JPanel();
		NumberFormat m_taille = NumberFormat.getIntegerInstance();
		JFormattedTextField t_taille_x = new JFormattedTextField(m_taille), t_taille_y = new JFormattedTextField(m_taille);
		
		// EAST
		JPanel east_top = new JPanel(), east_bottom = new JPanel();
		east_top.setLayout(new GridLayout(7, 1,2,2));
		east_top.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		
		JCheckBox scale = new JCheckBox("Sclale"),reversed = new JCheckBox("Reversed");
		reversed.setEnabled(image.getSize(direction)==3);
		reversed.setSelected(image.isReversed());
		reversed.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				image.setReversed(reversed.isSelected());
			}
		});
		scale.setSelected(true);
		east_top.add(scale);
		east_top.add(reversed);
		
		t_taille_x.setText(""+EventX.TAILLE);
		t_taille_y.setText(""+EventX.TAILLE);
		t_taille_x.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e){
				int tmp = 32;
				if(!t_taille_x.getText().isEmpty()){
				tmp = Integer.parseInt(t_taille_x.getText());
					if (tmp<8)
						tmp = 8;
					else if(tmp > 99)
						tmp = 99;
					if (tmp > img.getWidth())
						tmp = img.getWidth();
					
				}
				img.setTaille(new Pos(tmp, Integer.parseInt(t_taille_y.getText())));
				img.repaint();
			}
		});
		t_taille_y.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e){
				int tmp = 32;
				if(!t_taille_y.getText().isEmpty()){
				tmp = Integer.parseInt(t_taille_y.getText());
					if (tmp<8)
						tmp = 8;
					else if(tmp > 99)
						tmp = 99;
					if (tmp > img.getHeight())
						tmp = img.getHeight();
					
				}
				img.setTaille(new Pos(Integer.parseInt(t_taille_x.getText()), tmp));
				img.repaint();
			}
		});

		p_taille_x.setLayout(new BorderLayout());
		p_taille_x.add(new JLabel(" X: "),BorderLayout.WEST);
		p_taille_x.add(t_taille_x,BorderLayout.CENTER);
		p_taille_y.setLayout(new BorderLayout());
		p_taille_y.add(new JLabel(" Y: "),BorderLayout.WEST);
		p_taille_y.add(t_taille_y,BorderLayout.CENTER);
		p_taille_y.setBorder(BorderFactory.createMatteBorder(0,0,1,0,Color.GRAY));
		east_top.add(p_taille_x);
		east_top.add(p_taille_y);
		
		
		b_add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				image.addImg(direction, new Pos2(img.getEmplacement().getX()*img.getTaille().getX(),img.getEmplacement().getY()*img.getTaille().getY(),img.getTaille().getX(),img.getTaille().getY()), scale.isSelected());
				Event_imageBuild2.this.tmp.repaint();
				reversed.setEnabled(image.getSize(direction)==3);
			}
		});
		b_addAll.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				image.addImg(EventImg2.HAUT, new Pos2(img.getEmplacement().getX()*img.getTaille().getX(),img.getEmplacement().getY()*img.getTaille().getY(),img.getTaille().getX(),img.getTaille().getY()), scale.isSelected());
				image.addImg(EventImg2.BAS, new Pos2(img.getEmplacement().getX()*img.getTaille().getX(),img.getEmplacement().getY()*img.getTaille().getY(),img.getTaille().getX(),img.getTaille().getY()), scale.isSelected());
				image.addImg(EventImg2.GAUCHE, new Pos2(img.getEmplacement().getX()*img.getTaille().getX(),img.getEmplacement().getY()*img.getTaille().getY(),img.getTaille().getX(),img.getTaille().getY()), scale.isSelected());
				image.addImg(EventImg2.DROITE, new Pos2(img.getEmplacement().getX()*img.getTaille().getX(),img.getEmplacement().getY()*img.getTaille().getY(),img.getTaille().getX(),img.getTaille().getY()), scale.isSelected());
				reversed.setEnabled(image.getSize(direction)==3);
				Event_imageBuild2.this.tmp.repaint();
			}
		});
		b_save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Event_imageBuild2.this.p.setImg(Event_imageBuild2.this.image);	
				d.dispose();
			}
		});
		east_top.add(b_add);
		east_top.add(b_addAll);
		east_top.add(b_save);
		
		ButtonGroup bg = new ButtonGroup();
		JRadioButton rh = new JRadioButton("Haut"),rb = new JRadioButton("Bas"),rg = new JRadioButton("Gauche"),rd = new JRadioButton("Droite");
		east_bottom.setLayout(new GridLayout(4, 1));
		rh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(rh.isSelected()){
					direction = EventImg2.HAUT;
					image.lookAt(direction);
					reversed.setEnabled(image.getSize(direction)==3);
					reversed.setSelected(image.isReversed());
				}
			}
		});
		rb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(rb.isSelected()){
					direction = EventImg2.BAS;
					image.lookAt(direction);
					reversed.setEnabled(image.getSize(direction)==3);
					reversed.setSelected(image.isReversed());
				}
			}
		});
		rg.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(rg.isSelected()){
					direction = EventImg2.GAUCHE;
					image.lookAt(direction);
					reversed.setEnabled(image.getSize(direction)==3);
					reversed.setSelected(image.isReversed());
				}
			}
		});
		rd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(rd.isSelected()){
					direction = EventImg2.DROITE;
					image.lookAt(direction);
					reversed.setEnabled(image.getSize(direction)==3);
					reversed.setSelected(image.isReversed());
				}
			}
		});
		
		bg.add(rh);
		bg.add(rb);
		bg.add(rg);
		bg.add(rd);
		rb.setSelected(true);
		east_bottom.setBorder(new EtchedBorder(EtchedBorder.LOWERED));

		east_bottom.add(rh);
		east_bottom.add(rb);
		east_bottom.add(rg);
		east_bottom.add(rd);
		
		east.setLayout(new BorderLayout());
		east.add(east_top,BorderLayout.CENTER);
		east.add(east_bottom,BorderLayout.SOUTH);
		
		
		//SOUTH
		south.setPreferredSize(new Dimension(d.getWidth(), 36));
		south.add(this.tmp);
//		south.add(b_accepter);
//		south.add(b_annuler);
		
		b_annuler.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				d.dispose();
			}
		});
		
		
		p.setLayout(new BorderLayout());
		JScrollPane scrollPane = new JScrollPane(this.img, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.img.setPreferredSize(new Dimension(this.img.getWidth(), this.img.getHeight()));
		p.add(scrollPane,BorderLayout.CENTER);
		p.add(east,BorderLayout.EAST);
		p.add(tmp,BorderLayout.SOUTH);
		d.getContentPane().add(p);
		d.setTitle("Evenement");
		d.setMinimumSize(new Dimension(415,400));
		d.setModal(true);
		
		d.setVisible(true);
	}

	
}
