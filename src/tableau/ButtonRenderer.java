package tableau;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import edit_variable.Switch;
import evenement.Factorie_Perso;

@SuppressWarnings("serial")
public class ButtonRenderer extends JButton implements TableCellRenderer{


	  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean isFocus, int row, int col) {

		  if (value != null){
			  if(value.getClass().equals(Factorie_Perso.class)){
				  setText(((Factorie_Perso)value).getInteractions().get(row).toString());
			  }
			  else if (value.getClass().equals(new String().getClass())){
				  setText(value.toString());
			  }
			  else if (value.getClass().equals(Switch.class)){
				  setText(value.toString());
			  }
			  else if (value.getClass().equals(Integer.class)){
				  setText(value+"");
				  this.setHorizontalAlignment(JLabel.CENTER); 
			  }
		  }
		 
	    return this;

	  }

	}