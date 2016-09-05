/*
 * 9 August 2016
 * Glycopter project
 * This class calculates and makes a list of all the dihedral angles in the molecule. This will then be used to
 * calculate the energy associated with the dihedral angles in the molecule 
 */
package resources;


public class DihedralAngle{
	
	double angle;
	Atom a1, a2, a3, a4;
	public static Double dihedralAngleE;

	public DihedralAngle(Atom atom1, Atom atom2, Atom atom3, Atom atom4){
		a1 = atom1;
		a2 = atom2;
		a3 = atom3;
		a4 = atom4;
		angle = calculateAngle(a1, a2, a3, a4);
	}

	public double calculateAngle(Atom A1, Atom A2, Atom A3, Atom A4){ // calculate by finding angle between 2 planes
		// determine planes:
		// plane 1:
		Double[] plane1 = calculatePlane(A1,A2,A3);
		Double[] plane2 = calculatePlane(A2,A3,A4);

		// determine angle
		Double cosAngle = (plane1[0]*plane2[0] + plane1[1]*plane2[1] + plane1[2]*plane2[2])/
					   (Math.sqrt(sq(plane1[0]) + sq(plane1[1]) + sq(plane1[2]))*
					    Math.sqrt(sq(plane2[0]) + sq(plane2[1]) + sq(plane2[2])));
		
		Double angleRad = Math.acos(cosAngle); // Radians
		// convert to degrees;
		Double angleDeg = Math.toDegrees(angleRad);
		return angleRad;
	}
	
	public Double[] calculatePlane(Atom A1, Atom A2, Atom A3){
		// calculate 2 vectors from 3 points
		Double[] u = {A2.getX() - A1.getX(), A2.getY() - A1.getY(), A2.getZ() - A1.getZ()};
		Double[] v = {A3.getX() - A1.getX(), A3.getY() - A1.getY(), A3.getZ() - A1.getZ()};
		
		// take cross product to get normal vector
		Double planei = u[1] * v[2] - v[1] * u[2];
        Double planej = v[0] * u[2] - u[0] * v[2];
        Double planek = u[0] * v[1] - v[0] * u[1];
        // therefore plane; planeiX + planejY + planekZ = d
		
        Double[] plane = {planei, planej, planek};
		return plane;
	}
	
	// method to take the square of the input
	public double sq(double number){
		return Math.pow(number,2);
	}
	
	public String toString(){
		return "Angle: " + angle + ", atom1: " + a1.id + ", atom2: " + a2.id + ", atom3: " + a3.id + ", atom4: " + a4.id; 
	}
	
	/*
	 * This method is hardcoded to calculate the energy of a dihedral angle consisting of 4 atoms because each set of 4
	 * atoms has different values for the force constant, value of n, and the phase angle. These are literature values
	 * that are known.
	 */
	public double calculateAngleEnergy(Atom atom1, Atom atom2, Atom atom3, Atom atom4){
		String atomsInvolved = atom1.getAtomType() + atom2.getAtomType() + atom3.getAtomType() + atom4.getAtomType();
		switch(atomsInvolved){
		
		//dEnergy+= k * (1 + Math.cos(n*torAngle-phaseAngle));
		
		case("CCCC"):
			
			dihedralAngleE = 0.19*(1 + Math.cos(3*calculateAngle(atom1, atom2, atom3, atom4) - 180));
			break;
		
		case("CCCO"):
			dihedralAngleE = 0.20*(1 + Math.cos(3*calculateAngle(atom1, atom2, atom3, atom4)));
			break;
		
		case("CCCO5"):
			
			dihedralAngleE = 0.31*(1 + Math.cos(3*calculateAngle(atom1, atom2, atom3, atom4) - 180));
			break;
		
		case("CCCH"):
			dihedralAngleE = 0.20*(1 + Math.cos(3*calculateAngle(atom1, atom2, atom3, atom4)));
			break;
		
		case("CCO5C"):
			dihedralAngleE = 0.20*(1 + Math.cos(3*calculateAngle(atom1, atom2, atom3, atom4)));
			break;
		
		case("CCOC"):
			dihedralAngleE = 0.20*(1 + Math.cos(3*calculateAngle(atom1, atom2, atom3, atom4)));
			break;
		
		case("CCOH"):
			dihedralAngleE = 0.18*(1 + Math.cos(3*calculateAngle(atom1, atom2, atom3, atom4)));
			break;
		
		case("COCO"):
			dihedralAngleE = 0.41*(1 + Math.cos(1*calculateAngle(atom1, atom2, atom3, atom4) - 180));
			break;
		
		case("COCO5"):
			dihedralAngleE = 0.14*(1 + Math.cos(1*calculateAngle(atom1, atom2, atom3, atom4)))
			+ 0.97*(1 + Math.cos(2*calculateAngle(atom1, atom2, atom3, atom4))) 
			+ 0.11*(3 + Math.cos(3*calculateAngle(atom1, atom2, atom3, atom4) - 180));
			break;
		
		case("CO5CO"):
			dihedralAngleE = 0.62*(1 + Math.cos(1*calculateAngle(atom1, atom2, atom3, atom4)))
			+ 1.54*(1 + Math.cos(2*calculateAngle(atom1, atom2, atom3, atom4)))
			+ 0.48*(1 + Math.cos(3*calculateAngle(atom1, atom2, atom3, atom4)));
			break;
		
		case("COCH"):
			dihedralAngleE = 0.284*(1 + Math.cos(3*calculateAngle(atom1, atom2, atom3, atom4)));
			break;
		
		case("CO5CH"):
			dihedralAngleE = 0.284*(1 + Math.cos(3*calculateAngle(atom1, atom2, atom3, atom4)));
			break;
		
		case("OCCO"):
			dihedralAngleE = 2.65*(1 + Math.cos(1*calculateAngle(atom1, atom2, atom3, atom4) - 180))
			+ 0*(1 + Math.cos(2*calculateAngle(atom1, atom2, atom3, atom4) - 180)) //NB CHECK THIS LINE (Kchi is zero?)
			+ 0.13*(1 + Math.cos(3*calculateAngle(atom1, atom2, atom3, atom4) - 180));
			break;
		
		case("OCCO5"):
			dihedralAngleE = 0.36*(1 + Math.cos(1*calculateAngle(atom1, atom2, atom3, atom4) - 180))
			+ 0.16*(1 + Math.cos(2*calculateAngle(atom1, atom2, atom3, atom4)))
			+ 1.01*(1 + Math.cos(3*calculateAngle(atom1, atom2, atom3, atom4)));
			break;
		
		case("OCCH"):
			dihedralAngleE = 0.14*(1 + Math.cos(3*calculateAngle(atom1, atom2, atom3, atom4)));
			break;
		
		case("O5CCH"):
			dihedralAngleE = 0.20*(1 + Math.cos(3*calculateAngle(atom1, atom2, atom3, atom4)));
			break;
		
		case("HCCH"):
			dihedralAngleE = 0.20*(1 + Math.cos(3*calculateAngle(atom1, atom2, atom3, atom4)));
			break;
		}
		
		return dihedralAngleE;
	}
}