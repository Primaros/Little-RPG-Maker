package tableau;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;

import evenement.Factorie_Perso;

@SuppressWarnings("serial")
public class ButtonEditorSup extends DefaultCellEditor {
	protected JButton button;
	private ButtonListener bListener = new ButtonListener();

	public ButtonEditorSup(JCheckBox arg0, Factorie_Perso bp) {
		super(arg0);
		button = new JButton();
		button.setOpaque(true);
		button.addActionListener(bListener);
		bListener.setPerso(bp);
	}

	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) { 

		bListener.setIndex(row);
		button.setText("X");

		return button;
	}

	public class ButtonListener implements ActionListener {
		private int index;
		private Factorie_Perso p;

		@Override
		public void actionPerformed(ActionEvent arg0) {
				p.getMi().supI(index);
		}

		public void setIndex(int i){this.index = i;}

		public void setPerso(Factorie_Perso p) {this.p = p;}

	}

}
