package resources;

import java.util.ArrayList;


public class Molecule {

	double distance;
	ArrayList<Atom> atoms;
	ArrayList<Bond> bondList;
	ArrayList<DihedralAngle> dihedralList;
	Bond bond;
	Atom atom1;
	Atom atom2;
	
	public Molecule(ArrayList<Atom> input){
		distance = 0;
		bondList = new ArrayList<Bond>();
		dihedralList = new ArrayList<DihedralAngle>();
		atoms = input;
		bond = null;
		atom1 = null;
		atom2 = null;
		identifyBonds(input);
		System.out.println("atoms");
		for (Atom a: atoms ){
			System.out.println(a.id);
		}
		System.out.println("bonds");
		for (Bond b: bondList ){
			System.out.println(b);
		}
	}
	
	// I wasn't sure how we check what type of atom it is (Will need to look at Michelle's code for that)
	public void identifyBonds(ArrayList<Atom> atoms){
		
		for (int i = 0; i < (atoms.size() - 1); i++){
			atom1 = atoms.get(i);
			
			for (int j = i+1; j < atoms.size(); j++){
				
				atom2 = atoms.get(j);
			
				//Get the distance between the 2 atoms
				distance = Math.sqrt(Math.pow((atom2.getX() - atom1.getX()), 2) + Math.pow((atom2.getY() - atom1.getY()), 2) + Math.pow((atom2.getZ() - atom1.getZ()),  2));
				
				// check if distance is right for a C-C bond
				// C-C bonds range from 120-154pm
				
				if (atom1.getAtomType().equals("C") && atom2.getAtomType().equals("C") && distance <= 154){
					bond = new Bond(atom1, atom2);
					bondList.add(bond);
				}
				
				// check if distance is right for a C-O bond
				// C-O bonds range from 143-215pm
				else if (atom1.getAtomType().equals("C") && atom2.getAtomType().equals("O") && distance <= 215){
			
					bond = new Bond(atom1, atom2);
					bondList.add(bond);
				}
				
				// check if distance is right for a C-H bond
				// C-H bonds range from 106-112pm
				else if (atom1.getAtomType().equals("C") && atom2.getAtomType().equals("H") && distance <= 112){
					bond = new Bond(atom1, atom2);
					bondList.add(bond);
				}
				
				// check if distance is right for a O-H bond
				// O-H bond is approximately 96pm
				else if (atom1.getAtomType().equals("O") && atom2.getAtomType().equals("H") && distance <= 96){
					bond = new Bond(atom1, atom2);
					bondList.add(bond);
				}
			}
		}
	}

	public void identifyDihedrals(ArrayList<Atom> atomList){

		// Determine 4 atoms in dihedral


		
		// create DihedralAngle objects and add to list
		DihedralAngle dihedral = new DihedralAngle(a1, a2, a3, a4); // code to calculate angle in DihedralAngle class
		dihedralList.add(dihedral);
	}
}
