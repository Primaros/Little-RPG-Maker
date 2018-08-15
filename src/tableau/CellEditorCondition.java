package tableau;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTable;

import edit_variable.Condition;
import fenetre.MenuInteraction;

@SuppressWarnings("serial")
public class CellEditorCondition extends DefaultCellEditor{
	
	protected JComboBox<Condition> button;
	private ButtonListener bListener = new ButtonListener();
	  
	public CellEditorCondition(JCheckBox arg0, MenuInteraction i) {
		super(arg0);
		button = i.getCondCombo();
	    bListener.setMi(i);
	    button = bListener.i.getCondCombo() ;
	    button.addItemListener(bListener);
	    
	}

	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) { 
			button = bListener.i.getCondCombo();
			button.addItemListener(bListener);
			bListener.setIndex(row);
			 
	    return button;
	  }
	
		public class ButtonListener implements ItemListener {
		private int index;
		private MenuInteraction i;
	    
		public void setIndex(int i){this.index = i;}

		public void setMi(MenuInteraction p) {this.i = p;}

		@Override
		public void itemStateChanged(ItemEvent e) {
			  int item = new Integer((int)button.getSelectedIndex());
              if(this.i.getCond(item)!=null){
              	this.i.getP().getP().getActions().get(index).setCondition(this.i.getCond(item));
              }
		}

	}
	
}

