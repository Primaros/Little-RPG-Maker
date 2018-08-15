package tableau;


import interaction.Interaction;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;

import fenetre.MenuInteraction;

@SuppressWarnings("serial")
public class ButtonEditorInteraction extends DefaultCellEditor {
	protected JButton button;
	private ButtonListener bListener = new ButtonListener();
	  
	public ButtonEditorInteraction(MenuInteraction i) {
		super(new JCheckBox());
		button = new JButton();
	    button.setOpaque(true);
	    bListener.setMi(i);
	    button.addActionListener(bListener);
	}

	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) { 

			 bListener.setIndex(row);
			 button.setText(bListener.i.getP().getInteractions().get(row).toString());
	    
	    return button;
	  }
	
		public class ButtonListener implements ActionListener {
		private int index;
		private MenuInteraction i;
	    
		@Override
		public void actionPerformed(ActionEvent arg0) {
			Interaction tmp = i.getP().getInteractions().get(index).menuOpen(null);
			if(tmp!=null){
				i.getP().getMi().modI(index, tmp);
			}
				
		}
		
		public void setIndex(int i){this.index = i;}

		public void setMi(MenuInteraction p) {this.i = p;}

	}
	
}
