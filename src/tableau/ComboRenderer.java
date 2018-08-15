package tableau;

import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import edit_variable.Condition;
import fenetre.MenuInteraction;

@SuppressWarnings("serial")
public class ComboRenderer extends JComboBox<Object> implements TableCellRenderer {
	
	MenuInteraction m;
	
	public ComboRenderer(MenuInteraction m){
		this.m = m;
	}
	
	@Override
	  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean isFocus, int row, int col) {
	   JComboBox<Condition> tmp = m.getCondCombo();
	   tmp.setSelectedItem(m.getP().getInteractions().get(row).getCondition());
		return tmp;
	  }

	}