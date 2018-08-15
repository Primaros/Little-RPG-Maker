package fenetre;
//package fenetre;
//
//import java.awt.BorderLayout;
//import java.awt.Dimension;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.io.File;
//import java.text.NumberFormat;
//import java.util.regex.Pattern;
//
//import javax.swing.ButtonGroup;
//import javax.swing.JButton;
//import javax.swing.JDialog;
//import javax.swing.JFormattedTextField;
//import javax.swing.JLabel;
//import javax.swing.JPanel;
//import javax.swing.JRadioButton;
//import javax.swing.JScrollPane;
//
//import evenement.EventX;
//import evenement.Perso;
//import evenement.Pos;
//
//public class Event_imageBuild {
//	private Event_imageIMG img;
//	private Perso p;
//	JFormattedTextField t_taille;
//	private String nomImg;
//	
//	public static void charSetParam(String chemin,Perso p, JDialog f){
//		Event_imageBuild tmp = new Event_imageBuild(chemin,p);
//		tmp.showWindow(f);
//	}
//	
//	public Event_imageBuild(String chemin,Perso p){
//		if(p.getFrame() == EventX.DEFAUT_FRAME_MULTIPLE)
//			this.img = new Event_imageIMG(chemin,true);
//		else
//			this.img = new Event_imageIMG(chemin,false);
//		this.p = p;
//		this.nomImg = chemin.split("\\.tileset")[0];
//		
//		this.nomImg = this.nomImg.split(Pattern.quote(File.separator))[this.nomImg.split(Pattern.quote(File.separator)).length-1];
//	}
//	
//	public void showWindow(JDialog f){
//		JDialog d = new JDialog(f);
//		JPanel p = new JPanel(),north = new JPanel(), south = new JPanel();
//		JButton b_accepter = new JButton("Accepter"), b_annuler = new JButton("Annuler");
//		ButtonGroup groupB = new ButtonGroup();
//		JRadioButton b_Perso = new JRadioButton("Multiple");
//		JRadioButton b_Objet = new JRadioButton("Solo");
//		groupB.add(b_Perso);
//		groupB.add(b_Objet);
//		if (this.p.getFrame()==EventX.DEFAUT_FRAME_MULTIPLE)
//			b_Perso.setSelected(true);
//		else
//			b_Objet.setSelected(true);
//		
//		JButton b_taille = new JButton("OK");
//		JPanel p_taille = new JPanel();
//		JLabel l_taille = new JLabel("Taille :");
//		NumberFormat m_taille = NumberFormat.getIntegerInstance();
//		t_taille = new JFormattedTextField(m_taille);
//		t_taille.setPreferredSize(new Dimension(10*4, 30));
//		t_taille.setText(""+EventX.TAILLE);
//		p_taille.add(l_taille);
//		p_taille.add(t_taille);
//		p_taille.add(b_taille);
//		b_Perso.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				Event_imageBuild.this.p.setFrame(EventX.DEFAUT_FRAME_MULTIPLE);
//				Event_imageBuild.this.img.setMultiple(true);
//				Event_imageBuild.this.img.repaint();
//			}
//		});
//		
//		b_Objet.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				Event_imageBuild.this.p.setFrame(EventX.DEFAUT_FRAME_UNIQUE);
//				Event_imageBuild.this.img.setMultiple(false);
//				Event_imageBuild.this.img.repaint();
//			}
//		});
//		
//		b_taille.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				int tmp = Integer.parseInt(t_taille.getText());
//				if(t_taille.getText() != null && (tmp >0 && (tmp <= img.getWidth() && tmp <= img.getHeight()))){
//					img.setTaille(new Pos(tmp ,tmp));
//					img.repaint();
//				}
//			}
//		});
//		
//		b_accepter.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				Event_imageBuild.this.p.setImg(Event_imageBuild.this.img.getImg());
//				
//				Event_imageBuild.this.p.getImg().setNomT(Event_imageBuild.this.nomImg);
//				
//				d.dispose();
//				
//			}
//		});
//		
//		b_annuler.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				d.dispose();
//			}
//		});
//		
//		north.add(b_Perso);
//		north.add(b_Objet);
//		north.add(p_taille);
//	
//		south.add(b_accepter);
//		south.add(b_annuler);
//		
//		p.setLayout(new BorderLayout());
//		p.add(north,BorderLayout.NORTH);
//		JScrollPane scrollPane = new JScrollPane(this.img, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//		this.img.setPreferredSize(new Dimension(this.img.getWidth(), this.img.getHeight()));
//		p.add(scrollPane,BorderLayout.CENTER);
//		p.add(south,BorderLayout.SOUTH);
//		d.getContentPane().add(p);
//		d.setTitle("Evenement");
//		d.setMinimumSize(new Dimension(415,400));
//		d.setModal(true);
//		
//		d.setVisible(true);
//	}
//}
