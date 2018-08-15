package edit_variable;

@SuppressWarnings("serial")
public abstract class Cond_Switch extends Condition{

	private Switch i;
	
	public Cond_Switch(Switch i){
		this.i = i;
	}
	
	public Switch getI() {
		return i;
	}

	public String toString(){
		return super.toString() + "L'intérupteur " + this.getI();
	}
	
}
