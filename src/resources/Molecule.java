package resources;

import java.util.ArrayList;


public class Molecule {

	double distance;
	//ArrayList<Atom> atomList = new ArrayList<Atom>(); I'm not sure how we are coordinating the atom list from readPDB and the atom list here.
	Atom atoms[];
	ArrayList<Bond> bondList = new ArrayList<Bond>();
	Bond bond;
	Atom atom1;
	Atom atom2;
	
	public Molecule(){
		distance = 0;
		bondList = null;
		bond = null;
		atoms = null;
		atom1 = null;
		atom2 = null;
		
	}
	
	// I wasn't sure how we check what type of atom it is (Will need to look at Michelle's code for that)
	public void identifyBonds(Atom atoms[]){
		
		for (int i = 0; i < (atoms.length - 1); i++){
			atom1 = atoms[i];
			
			for (int j = i+1; j < atoms.length; j++){
				
				atom2 = atoms[j];
			
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
					new Bond(atom1, atom2);
					bond = new Bond(atom1, atom2);
					bondList.add(bond);
				}
				
				// check if distance is right for a C-H bond
				// C-H bonds range from 106-112pm
				else if (atom1.getAtomType().equals("C") && atom2.getAtomType().equals("H") && distance <= 112){
					new Bond(atom1, atom2);
					bond = new Bond(atom1, atom2);
					bondList.add(bond);
				}
				
				// check if distance is right for a O-H bond
				// O-H bond is approximately 96pm
				else if (atom1.getAtomType().equals("O") && atom2.getAtomType().equals("H") && distance <= 96){
					new Bond(atom1, atom2);
					bond = new Bond(atom1, atom2);
					bondList.add(bond);
				}
			}
		}
	}
}
