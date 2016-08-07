package resources;


public class Hydrogen extends Atom{
	
	public Hydrogen(int ID, double x, double y, double z) {
		super(ID, x, y, z);
		atomType.equals("H");
		// TODO Auto-generated constructor stub
	}

	public String getAtomType(){
		return atomType;
	}

}
