package edit_variable;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Switch_G extends Switch implements Serializable{
	
	public static int NB = 0;
	
	public Switch_G(){
		this.init();
	} 
	
	private void init(){
		Switch_G.NB += 1;
		this.setId(Switch_G.NB);
		this.disable();
		this.setNom("Interupteur G "+Switch_G.NB);
	}
	
}
