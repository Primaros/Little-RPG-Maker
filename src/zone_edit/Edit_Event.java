package zone_edit;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import evenement.EventListe;
import evenement.Factorie_Perso;
import evenement.Perso;


@SuppressWarnings("serial")
public class Edit_Event extends JPanel implements Observer{
	
	JPanel events = new JPanel();
	EventListe l_events;
	JButton newEvent;
	
	public Edit_Event(){
		super();
		this.setBackground(null);
		this.setBorder(BorderFactory.createRaisedBevelBorder());
		this.setLayout(new BorderLayout());
		
		newEvent = new JButton("Nouveau");
		newEvent.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Factorie_Perso.perso_create(Edit_Event.this.l_events);
			}
		});
		events.setLayout(new BorderLayout());
		
		this.add(events, BorderLayout.CENTER);
		this.add(newEvent, BorderLayout.SOUTH);
		newEvent.setEnabled(false);
	}
	
	public void setEvent(EventListe l){
		if (!newEvent.isEnabled())
			newEvent.setEnabled(true);
		this.l_events = new EventListe();
		this.l_events = l;
		this.l_events.addObserver(this);
		this.maj();
	}
	
	public void maj(){
		JPanel tmp = new JPanel();
		tmp.setLayout(new FlowLayout());
		for (int i=0;i<l_events.size();i++){
			tmp.add(new Edit_MiniEvent(this.getSize().width-20, (Perso) l_events.get(i),l_events));
		}
		tmp.setPreferredSize(new Dimension(220, 65*l_events.size()));
		events.removeAll();
		JScrollPane tmp2 = new JScrollPane(tmp,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		events.add(tmp2,BorderLayout.CENTER);
		this.revalidate();
		this.repaint();
	}
	
	public void disable(){
		this.setEvent(new EventListe());
		this.newEvent.setEnabled(false);
	}


	@Override
	public void update(Observable o, Object arg) {
		this.maj();
	}
	
}
