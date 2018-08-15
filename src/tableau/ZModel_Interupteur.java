package tableau;

import javax.swing.table.DefaultTableModel;

import fenetre.MenuInterupteur2;


@SuppressWarnings("serial")
public class ZModel_Interupteur extends DefaultTableModel{
	private MenuInterupteur2 i;
	
	public ZModel_Interupteur(Object[][] data, String[] title, MenuInterupteur2 i) {
		super(data,title);
		this.i=i;
	}
	
	@Override
	public boolean isCellEditable(int row, int col){
		if(col!=0)
			return true;
		else
			return false;
	}

	@Override
	public Class<?> getColumnClass(int arg0) {
		if(arg0==2)
			return Boolean.class;
		return super.getColumnClass(arg0);
	}
	
	public void setValueAt(Object value, int row, int col) {
		
			switch(col){
			case 1:
				i.set(row, value);
				break;
			case 2:
				i.set(row, value);
				break;
			case 3:
				row=row-1;
				break;
			}
			if(row>=0){
				super.setValueAt(value, row, col);
				this.fireTableCellUpdated(row,col);
			}
			this.fireTableChanged(null);
			
    }
	
}
