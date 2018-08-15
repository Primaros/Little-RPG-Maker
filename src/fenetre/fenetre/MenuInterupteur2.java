package fenetre;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import tableau.ButtonEditorSupInterupteur;
import tableau.ButtonRenderer;
import tableau.ZModel_Interupteur;
import edit_variable.Switch;
import evenement.Perso;

@SuppressWarnings("serial")
public class MenuInterupteur2 extends JDialog{

	private Perso p;
	private JTable t;
	
	public MenuInterupteur2(JDialog f,Perso p){
		super(f);
		this.p=p;
		this.t = new JTable();
	}
	
	public void openMenu(){
		JPanel panel = new JPanel(), p1 = new JPanel(), p2 = new JPanel();
		JButton b_ajouter = new JButton("Nouvelle interupteur");
		this.t = this.tableaux();
		p2.setLayout(new BorderLayout());
		p2.add(new JScrollPane(this.t));
		p2.setPreferredSize(new Dimension(240,100));
		p1.setLayout(new GridLayout(1,1));
		p1.add(b_ajouter);
		b_ajouter.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MenuInterupteur2.this.addI(new Switch());
			}
		});
		
		panel.setLayout(new BorderLayout());
		panel.add(p1,BorderLayout.NORTH);
		panel.add(p2,BorderLayout.CENTER);
		this.add(panel);
		this.setTitle("Actions");
		this.setMinimumSize(new Dimension(400,300));
		this.setResizable(false);
		this.setVisible(true);
		this.setModal(true);
		
	}
	
	private JTable tableaux(){
		int taille_x = 400, tailleMin = 50, tailleMax = taille_x - (3*tailleMin);
		
		Object[][] data = new Object[this.p.getInterupteurs().size()][4];
		for(int i=0;i<this.p.getInterupteurs().size();i++){
			data[i][0] = new Integer(this.p.getInterupteurs().get(i).getId());
			data[i][1] = this.p.getInterupteurs().get(i).getNom();
			data[i][2] = new Boolean(this.p.getInterupteurs().get(i).isEnable());
			data[i][3] = "X";
		}
		String  title[] = {"ID", "Nom", "Activé", "Del"};
		ZModel_Interupteur t =new ZModel_Interupteur(data, title, this);
		JTable tableau=new JTable(t);
		
		tableau.setRowHeight(25);

		
		
		for(int i=0;i<4;i++){
			tableau.getColumnModel().getColumn(i).setMaxWidth(tailleMax);
			tableau.getColumnModel().getColumn(i).setMinWidth(tailleMin);
		}
		
		tableau.getColumnModel().getColumn(1).setPreferredWidth(tailleMax);
		tableau.getColumn("Del").setCellRenderer(new ButtonRenderer());
		tableau.getColumn("Del").setCellEditor(new ButtonEditorSupInterupteur(new JCheckBox(),this));
		return tableau;
	}
	
	public void addI(Switch i){
		if(i!=null){
			this.p.getInterupteurs().add(i);
			Object[] data = new Object[4];
			data[0] = new Integer(i.getId());
			data[1] = i.getNom();
			data[2] = new Boolean(i.isEnable());
			data[3] = "X";
			((DefaultTableModel)t.getModel()).addRow(data);
		}
	}
	
	public void remove(int i){
		p.getInterupteurs().remove(i);
		((DefaultTableModel)t.getModel()).removeRow(i);
	}
	
	public void set(int i, Object value){
		if (value.getClass() == Boolean.class){
			if ((Boolean)value)
				this.p.getInterupteurs().get(i).enable();
			else
				this.p.getInterupteurs().get(i).disable();
		} else if (value.getClass() == String.class){
			this.p.getInterupteurs().get(i).setNom((String)value);
		}
	}
}
