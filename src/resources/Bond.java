package resources;


public class Bond {

	int bondID;
	static int noBonds;
	Atom atom1, atom2;
	
	public Bond(Atom a1, Atom a2){
		bondID = noBonds;
		incBonds();
		atom1 = a1;
		atom2 = a2;
	}
	
	public static void incBonds(){
		noBonds ++;
	}
	
	public int getBonds(){
		return noBonds;
	}
	
	public int getBondID(){
		return bondID;
	}
	
	public String toString(){
		return "ID: " + bondID + "atom1: " + atom1.id + ", atom2: " + atom2.id; 
	}
}
