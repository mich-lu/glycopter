/*
 * 11 August 2016
 * Glycopter project
 * This class creates objects of pairs of atoms that are not bonded to be used in energy calculations
 * between the non-bonded atoms. The calculation of non-bonded energies is also in this class.
 */

package resources;

public class Interaction {
	
	int interactionID;
	static int noInteractions;
	Atom atom1, atom2;

	double energy;
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
	
	public void updateDistance(){
		distance = Math.sqrt(Math.pow((atom2.getX() - atom1.getX()), 2) + Math.pow((atom2.getY() - atom1.getY()), 2) + Math.pow((atom2.getZ() - atom1.getZ()),  2));
	}
	
	/*
	 * calculate the steric interaction energy between two non-bonded atoms.
	 */
	public double calculateInteractionEnergy(){
		Double[] constants1 = getConstants(atom1);
		Double[] constants2 = getConstants(atom2);
		
		double epsilon = Math.sqrt(constants1[0]*constants2[0]);
		double minRadius = (constants1[1] + constants2[1])/2;
		double radius= distance; 
		
		// get energy by substituting into formula
		double nEnergy = epsilon*(Math.pow((minRadius/radius), 12) - 2*Math.pow((minRadius/radius), 6));
	
		return nEnergy;
	}
	
	// This method is used by calculateInteractionEnergy to get formula constants
	public static Double[] getConstants(Atom atom){
		Double[] constants = new Double[2]; // [epsilon, minRadius]
		
		switch(atom.atomType){
			case "C":
				constants[0] = -0.032;
				constants[1] = 2.0;
				break;
			case "O":
				constants[0] = -0.12;
				constants[1] = 1.7;
				break;
			case "O5":
				constants[0] = -0.1;
				constants[1] = 1.65;
				break;
			case "H":
				constants[0] = -0.045;
				constants[1] = 1.34;
				break;
		}
		
		return constants;
	}

	public double getDistance() {
		return distance;
	}
}
