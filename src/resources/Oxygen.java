package resources;


public class Oxygen extends Atom{

	public Oxygen(int ID, double x, double y, double z) {
		super(ID, x, y, z);
		atomType.equals("O"); 
		// TODO Auto-generated constructor stub
	}
	
	public String getAtomType(){
		return atomType;
	}

}
