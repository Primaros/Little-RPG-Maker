package lancement;

import java.awt.BorderLayout;

import javax.swing.JProgressBar;
import javax.swing.JWindow;

@SuppressWarnings("serial")
public class Chargement extends  JWindow implements Runnable{
	JProgressBar bar;
	Chargement wind;
	Thread t;
	public Chargement(Thread i){
		t = new Thread();
		t=i;
		bar = new JProgressBar();
		 bar.setMaximum(100);
		 bar.setMinimum(0);
		 bar.setStringPainted(true);
		 setSize(200, 50);
		 setLocationRelativeTo(null);  
		 setAlwaysOnTop(true);
		 this.getContentPane().add(bar, BorderLayout.CENTER);
		 this.setVisible(true);
	}
	
	public void run(Thread i) {
		wind = new Chargement(i);
	}
	
	public void up(int v){
		bar.setValue(bar.getValue()+v);
		if (bar.getValue() >= 100)
			this.dispose();
		try {

	          Thread.sleep(1000);

	        } catch (InterruptedException e) {

	          // TODO Auto-generated catch block

	        e.printStackTrace();

	        }
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
