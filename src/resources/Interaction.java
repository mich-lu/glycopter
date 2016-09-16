/*
 * 11 August 2016
 * Glycopter project
 * This class just creates objects of pairs of atoms that are not bonded to be used in energy calculations
 * between the non-bonded atoms.
 */

package resources;

public class Interaction {
	
	int interactionID;
	static int noInteractions;
	Atom atom1, atom2;
	double distance;
	
	public Interaction(Atom a1, Atom a2, Double d){
		interactionID = noInteractions;
		incInteractions();
		atom1 = a1;
		atom2 = a2;
		distance = d;
	}
	
	public static void incInteractions(){
		noInteractions++;
	}
	
	public int getInteractions(){
		return noInteractions;
	}
	
	public int getInteractionID(){
		return interactionID;
	}
	
	public String toString(){
		return "Non-bonding interaction ID: " + interactionID + "atom1: " + atom1.id + ", atom2: " + atom2.id; 
	}
	
	public Atom getAtom1(){
		return atom1;
	}
	
	public Atom getAtom2(){
		return atom2;
	}

	public double getDistance() {
		return distance;
	}
}
