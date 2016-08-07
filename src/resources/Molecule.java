
public class Molecule {

	int distance;
	Bond bonds[];
	Atom atoms[];
	Atom atom1;
	Atom atom2;
	
	public Molecule(){
		distance = 0;
		bonds = null;
		atoms = null;
		atom1 = null;
		atom2 = null;
		
	}
	
	// I wasn't sure how we check what type of atom it is (Will need to look at Michelle's code for that)
	public void identifyBonds(Atom atoms[]){
		
		for (int i = 1; i < atoms.length; i++){
			
			atom1 = atoms[i-1];
			atom2 = atoms[i];
			
			//Get the distance between the 2 atoms
			distance = sqrt(Math.pow((atom2.getX() - atom1.getX()), 2) + Math.pow((atom2.getY() - atom1.getY()), 2) + Math.pow((atom2.getZ() - atom1.getZ()),  2));
			
			// check if distance is right for a C-C bond
			// C-C bonds range from 120-154pm
			if (atom1.getID.equals("C") && atom2.getID.equals("C") && distance <= 154){
				new Bond(atom1, atom2);
				
			}
			
			// check if distance is right for a C-O bond
			// C-O bonds range from 143-215pm
			else if (atom1.getID.equals("C") && atom2.getID.equals("O") && distance <= 215){
				new Bond(atom1, atom2);
			}
			
			// check if distance is right for a C-H bond
			// C-H bonds range from 106-112pm
			else if (atom1.getID.equals("C") && atom2.getID.equals("H") && distance <= 112){
				new Bond(atom1, atom2);
			}
			
			// check if distance is right for a O-H bond
			// O-H bond is approximately 96pm
			else if (atom1.getID.equals("O") && atom2.getID.equals("H") && distance <= 96){
				new Bond(atom1, atom2);
			}
		}
	}
}
