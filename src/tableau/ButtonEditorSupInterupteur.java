package tableau;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;

import fenetre.MenuInterupteur2;

@SuppressWarnings("serial")
public class ButtonEditorSupInterupteur extends DefaultCellEditor {
	protected JButton button;
	private ButtonListener bListener = new ButtonListener();

	public ButtonEditorSupInterupteur(JCheckBox arg0, MenuInterupteur2 mi) {
		super(arg0);
		button = new JButton();
		button.setOpaque(true);
		button.addActionListener(bListener);
		bListener.setMI(mi);
	}

	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) { 
		
		bListener.setIndex(row);
		button.setText("X");

		return button;
	}

	public class ButtonListener implements ActionListener {
		private int index;
		private MenuInterupteur2 p;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println(index);
				p.remove(index);
		}

		public void setIndex(int i){this.index = i;}

		public void setMI(MenuInterupteur2 p) {this.p = p;}

	}
}
