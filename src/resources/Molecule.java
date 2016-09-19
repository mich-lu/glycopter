/*
 * 6 August 2016
 * Glycopter project
 * this is the main class that coordinates the molecule and all the different aspects of the molecule. It has
 * lists for the atoms, the bonds, the non-bonding interactions, and the dihedral angles. 
 */

package resources;

import java.util.ArrayList;


public class Molecule {

	ArrayList<Atom> atoms;
	ArrayList<Bond> bondList;
	ArrayList<Interaction> interactionList; //non-bonded atoms
	ArrayList<DihedralAngle> dihedralList;
	ArrayList<Angle> angleList; //used for checking purposes, all the angles between bonds
	ArrayList<Angle> originalAngleList; //testing purposes
	
	double distance; //distance between 2 atoms
	Bond bond;
	Interaction interaction;
	Atom atom1;
	Atom atom2;
	int numAtoms; //For testing purposes
	Boolean bonded; // For testing purposes
	
	public Molecule(ArrayList<Atom> input){
		
		distance = 0;
		bondList = new ArrayList<Bond>();
		dihedralList = new ArrayList<DihedralAngle>();
		interactionList = new ArrayList<Interaction>();
		angleList = new ArrayList<Angle>();
		originalAngleList = angleList;
		atoms = input;
		bond = null;
		atom1 = null;
		atom2 = null;
		numAtoms = atoms.size(); //For testing purposes
		
		System.out.println("number of atoms in molecule = " + numAtoms);
		System.out.println("*****************************************************");
		System.out.println("Identifying the bonds...");
		identifyBonds(input, bondList);
		System.out.println();
		System.out.println("number of bonds calculated = " + bondList.size());
		System.out.println("*****************************************************");
		
		System.out.println("Identifying the angles between bonds...");
		identifyAngles(bondList, angleList);
		
		System.out.println();
		System.out.println("number of angles calculated = " + angleList.size());
		System.out.println("*****************************************************");
		
		
		System.out.println("Identifying the Dihedral Angles...");
		
		identifyDihedrals(input, dihedralList);
		
		System.out.println();
		System.out.println("number of dihedral angles calculated = " + dihedralList.size());
		
	}
	
	/*
	 * Used method used by RasMol in determining bonds in molecules with less than 255 atoms:
	 * 1) If (atoms + heteroatoms) is less than or equal to 255, a more time-consuming algorithm is employed. This algorithm can be 
	 * forced on any loaded structure with the command connect true. Two atoms are considered bonded when the distance between them 
	 * is between 0.4 Angstroms and (the sum of their covalent radii plus 0.56 Angstroms).
	 * Source: https://www.umass.edu/microbio/rasmol/rasbonds.htm
	 */
	public void identifyBonds(ArrayList<Atom> atoms, ArrayList<Bond> b){
		
		for (int i = 0; i < (atoms.size() - 1); i++){
			atom1 = atoms.get(i);
			
			for (int j = i+1; j < atoms.size(); j++){
				
				atom2 = atoms.get(j);
			
				//Get the distance between the 2 atoms
				distance = Math.sqrt(Math.pow((atom2.getX() - atom1.getX()), 2) + Math.pow((atom2.getY() - atom1.getY()), 2) + Math.pow((atom2.getZ() - atom1.getZ()),  2));
				
				// check if distance is right for a C-C bond
				// C-C bonds range from 1.20-1.54 Angstrom

				if ((atom1 instanceof Carbon && atom2 instanceof Carbon && distance <= (1.7 + 1.7)/2)){

					bond = new Bond(atom1, atom2);
					b.add(bond);
					addToBondsList(atom1, atom2);
					bonded = true; //testing
				}
				
				// check if distance is right for a C-O bond
				// C-O bonds range from 1.43-2.15 Angstrom
				else if ((atom1 instanceof Carbon  && atom2 instanceof Oxygen && distance <= (1.7 + 1.52)/2) || (atom1 instanceof Oxygen && atom2 instanceof Carbon && distance <= (1.7 + 1.52)/2)){

					bond = new Bond(atom1, atom2);
					b.add(bond);
					addToBondsList(atom1, atom2);
					bonded = true; //testing
				}
				
				// check if distance is right for a C-H bond
				// C-H bonds range from 1.06-1.12 Angstrom

				else if ((atom1 instanceof Carbon && atom2 instanceof Hydrogen && distance <= (1.7 + 1.09)/2) || (atom1 instanceof Hydrogen && atom2 instanceof Carbon && distance <= (1.7 + 1.09)/2)){
				
					bond = new Bond(atom1, atom2);
					b.add(bond);
					addToBondsList(atom1, atom2);
					bonded = true; //testing
				}
				
				// check if distance is right for a O-H bond
				// O-H bond is approximately 0.96 Angstrom

				else if ((atom1 instanceof Oxygen  && atom2 instanceof Hydrogen && distance <= (1.52 + 1.09)/2) || (atom1 instanceof Hydrogen && atom2 instanceof Oxygen && distance <= (1.52 + 1.09)/2)){

					bond = new Bond(atom1, atom2);
					b.add(bond);
					addToBondsList(atom1, atom2);
					bonded = true; //testing
				}
				
				/*else if it is not a bond, then just create the non-bonded interaction to be used
				 * for the non-bonding energy calculations
				 */
				else{
					
					interaction = new Interaction(atom1, atom2, distance);
					interactionList.add(interaction);
					bonded = false; //testing
				}
				
			}
		}
	}

	public void identifyDihedrals(ArrayList<Atom> atomList, ArrayList<DihedralAngle> di){
		DihedralAngle dihedral;
		// Determine 4 atoms in dihedral
		for (Bond bond : bondList){ // iterate through middle bond, therefore between a2 and a3
			Atom a2 = bond.atom1;
			Atom a3 = bond.atom2;
			// check if each atom is bonded to another atom:
			if(a2.getBonds().size() > 1 && a3.getBonds().size() > 1){
				for(Atom first : a2.getBonds()){
					if (first == a3){continue;} // check atom is in current bond
					for(Atom last : a3.getBonds()){
						if (last == a2){continue;} // check atom is in current bond
						dihedral = new DihedralAngle(first, a2, a3, last);
						di.add(dihedral);
					}
				}
			}
		}
	}
	
	public void identifyAngles(ArrayList<Bond> bondList, ArrayList<Angle> a){
		Angle bondPair;
		Bond b1, b2;
		for (int i = 0; i < (bondList.size() - 1); i++){
			b1 = bondList.get(i);
			
			for (int j = i+1; j < bondList.size(); j++){
				
				b2 = bondList.get(j);
				
				if (b1.atom1 == b2.atom1 || b1.atom1 == b2.atom2){
					bondPair = new Angle(b1, b2, b1.atom1);
					a.add(bondPair);
					continue;
				}
				if (b1.atom2 == b2.atom1 || b1.atom2 == b2.atom2){
					bondPair = new Angle(b1, b2, b1.atom2);
					a.add(bondPair);
					continue;
				}
			}
		}
	}
	
	public void addToBondsList(Atom a1, Atom a2){
		if(!(a1.getBonds().contains(a2))){ // check if atom already in list
			a1.getBonds().add(a2);
		}
		if(!(a2.getBonds().contains(a1))){ // check if atom already in list
			a2.getBonds().add(a1);
		}
	}
	
	public ArrayList<Bond> getBondList(){
		return bondList;
	}
	
	public ArrayList<DihedralAngle> getDihedralList(){
		return dihedralList;	
	}
	
	public ArrayList<Angle> getAngleList(){
		return angleList;
	}
	
	public ArrayList<Interaction> getInteractionList(){
		return interactionList;
	}
	
	public ArrayList<Atom> getAtomList(){
		return atoms;
	}
}
