
package tableau;

import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;

import edit_variable.Condition;
import fenetre.MenuInteraction;

@SuppressWarnings("serial")
public class ZModel extends DefaultTableModel{
	private MenuInteraction mi;

	public ZModel(Object[][] data, String[] title, MenuInteraction i) {
		super(data,title);
		this.mi = i;
	}

	public void setValueAt(Object value, int row, int col) {

		switch (col){
		case 1:
			//			System.out.println(value+" "+row+" "+col);
			//créer puis supr pui grandi
			if (value.getClass() == JComboBox.class){
				@SuppressWarnings("unchecked")
				int cond_num = ((JComboBox<Integer>)value).getSelectedIndex();
				if(cond_num != 0){
					Condition cond = (Condition)this.mi.getCondListe().get(cond_num);
					this.mi.getP().getInteractions().get(row).setCondition(cond);
				}
			}
			break;
		}

		if(row>=0){
			super.setValueAt(value, row, col);
			this.fireTableCellUpdated(row,col);
		}

	}

}
