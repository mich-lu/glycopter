/*
 * 6 August 2016
 * Glycopter project
 * this is the main class that coordinates the molecule and all the different aspects of the molecule. It has
 * lists for the atoms, the bonds, the non-bonding interactions, and the dihedral angles. 
 */

package resources;

import java.util.ArrayList;


public class Molecule {

	double distance;
	ArrayList<Atom> atoms;
	ArrayList<Bond> bondList;
	ArrayList<Interaction> interactionList;
	ArrayList<DihedralAngle> dihedralList;
	Bond bond;
	Interaction interaction;
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
				// C-C bonds range from 1.20-1.54 Angstrom
				if (atom1.getAtomType().equals("C") && atom2.getAtomType().equals("C") && distance <= 1.54){
					bond = new Bond(atom1, atom2);
					bondList.add(bond);
				}
				
				// check if distance is right for a C-O bond
				// C-O bonds range from 1.43-2.15 Angstrom
				else if (atom1.getAtomType().equals("C") && atom2.getAtomType().equals("O") && distance <= 2.15){
			
					bond = new Bond(atom1, atom2);
					bondList.add(bond);
				}
				
				// check if distance is right for a C-H bond
				// C-H bonds range from 1.06-1.12 Angstrom
				else if (atom1.getAtomType().equals("C") && atom2.getAtomType().equals("H") && distance <= 1.12){
					bond = new Bond(atom1, atom2);
					bondList.add(bond);
				}
				
				// check if distance is right for a O-H bond
				// O-H bond is approximately 0.96 Angstrom
				else if (atom1.getAtomType().equals("O") && atom2.getAtomType().equals("H") && distance <= 0.96){
					bond = new Bond(atom1, atom2);
					bondList.add(bond);
				}
				
				/*else if it is not a bond, then just create the non-bonded interaction to be used
				 * for the non-bonding energy calculations
				 */
				else{
					interaction = new Interaction(atom1, atom2, distance);
					interactionList.add(interaction);
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
