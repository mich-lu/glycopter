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
		angle = calculateDihedralAngle(a1, a2, a3, a4);
	}

	//Modified version of CarbBuilder's getDihedralAngleDiff();
	public static double calculateDihedralAngle(Atom atom1, Atom atom2, Atom atom3, Atom atom4)
	{

		double angleRad;
		// 2. for psi = C1 - O1 - Cx' - Hx'
		// childC, childO, parentC, parentH

		double[] normalPsi1 = Rotation.getNormal (atom1.getXYZ(), atom2.getXYZ(), atom3.getXYZ());
		double[] normalPsi2 = Rotation.getNormal( atom2.getXYZ(), atom3.getXYZ(), atom4.getXYZ());
		angleRad = Rotation.getAngleBetweenNormal(normalPsi1, normalPsi2);

		angleRad = Rotation.getSigned(angleRad, normalPsi2, atom1.getXYZ(), atom2.getXYZ()); // normal 2 with first two in normal 
		return angleRad;
	}

	
	public String toString(){
		return "Dihedral Angle: " + angle + ", atom1: " + a1.id + ", atom2: " + a2.id + ", atom3: " + a3.id + ", atom4: " + a4.id; 
	}
	
	/*
	 * This method is hardcoded to calculate the energy of a dihedral angle consisting of 4 atoms because each set of 4
	 * atoms has different values for the force constant, value of n, and the phase angle. These are literature values
	 * that are known.
	 */
	public double calculateAngleEnergy(Atom atom1, Atom atom2, Atom atom3, Atom atom4){
		String atomsInvolved = atom1.getAtomType() + atom2.getAtomType() + atom3.getAtomType() + atom4.getAtomType();
		
		
		//dEnergy+= k * (1 + Math.cos(n*torAngle-phaseAngle));
		
		if (atomsInvolved.equals("CCCC")){
			dihedralAngleE = 0.19*(1 + Math.cos(3*calculateDihedralAngle(atom1, atom2, atom3, atom4) - 180));
		}
		
		if (atomsInvolved.equals("CCCO") || atomsInvolved.equals("OCCC")){
			dihedralAngleE = 0.20*(1 + Math.cos(3*calculateDihedralAngle(atom1, atom2, atom3, atom4)));
		}
		
		if (atomsInvolved.equals("CCCO5") || atomsInvolved.equals("O5CCC")){
			dihedralAngleE = 0.31*(1 + Math.cos(3*calculateDihedralAngle(atom1, atom2, atom3, atom4) - 180));
		}
		
		if (atomsInvolved.equals("CCCH") || atomsInvolved.equals("HCCC")){
			dihedralAngleE = 0.20*(1 + Math.cos(3*calculateDihedralAngle(atom1, atom2, atom3, atom4)));
		}
		
		if (atomsInvolved.equals("CCO5C") || atomsInvolved.equals("CO5CC")){
			dihedralAngleE = 0.20*(1 + Math.cos(3*calculateDihedralAngle(atom1, atom2, atom3, atom4)));
		}
		
		if (atomsInvolved.equals("CCOC") || atomsInvolved.equals("COCC")){
			dihedralAngleE = 0.20*(1 + Math.cos(3*calculateDihedralAngle(atom1, atom2, atom3, atom4)));
		}
		
		if (atomsInvolved.equals("CCOH") || atomsInvolved.equals("HOCC")){
			dihedralAngleE = 0.18*(1 + Math.cos(3*calculateDihedralAngle(atom1, atom2, atom3, atom4)));
		}
		
		if (atomsInvolved.equals("COCO") || atomsInvolved.equals("OCOC")){
			dihedralAngleE = 0.41*(1 + Math.cos(1*calculateDihedralAngle(atom1, atom2, atom3, atom4) - 180));
		}
		
		if (atomsInvolved.equals("COCO5") || atomsInvolved.equals("O5COC")){
			dihedralAngleE = 0.14*(1 + Math.cos(1*calculateDihedralAngle(atom1, atom2, atom3, atom4)))
			+ 0.97*(1 + Math.cos(2*calculateDihedralAngle(atom1, atom2, atom3, atom4))) 
			+ 0.11*(3 + Math.cos(3*calculateDihedralAngle(atom1, atom2, atom3, atom4) - 180));
		}
		
		if (atomsInvolved.equals("CO5CO") || atomsInvolved.equals("OCO5C")){
			dihedralAngleE = 0.62*(1 + Math.cos(1*calculateDihedralAngle(atom1, atom2, atom3, atom4)))
			+ 1.54*(1 + Math.cos(2*calculateDihedralAngle(atom1, atom2, atom3, atom4)))
			+ 0.48*(1 + Math.cos(3*calculateDihedralAngle(atom1, atom2, atom3, atom4)));
		}
		
		if (atomsInvolved.equals("COCH") || atomsInvolved.equals("HCOC")){
			dihedralAngleE = 0.284*(1 + Math.cos(3*calculateDihedralAngle(atom1, atom2, atom3, atom4)));
		}
		
		if (atomsInvolved.equals("CO5CH") || atomsInvolved.equals("HCO5C")){
			dihedralAngleE = 0.284*(1 + Math.cos(3*calculateDihedralAngle(atom1, atom2, atom3, atom4)));
		}
		
		if (atomsInvolved.equals("OCCO")){
			dihedralAngleE = 2.65*(1 + Math.cos(1*calculateDihedralAngle(atom1, atom2, atom3, atom4) - 180))
			+ 0*(1 + Math.cos(2*calculateDihedralAngle(atom1, atom2, atom3, atom4)- 180)) //NB CHECK THIS LINE (Kchi is zero?)
			+ 0.13*(1 + Math.cos(3*calculateDihedralAngle(atom1, atom2, atom3, atom4) - 180));
		}
		
		if (atomsInvolved.equals("OCCO5") || atomsInvolved.equals("O5CCO")){
			dihedralAngleE = 0.36*(1 + Math.cos(1*calculateDihedralAngle(atom1, atom2, atom3, atom4) - 180))
			+ 0.16*(1 + Math.cos(2*calculateDihedralAngle(atom1, atom2, atom3, atom4)))
			+ 1.01*(1 + Math.cos(3*calculateDihedralAngle(atom1, atom2, atom3, atom4)));
		}
		
		if (atomsInvolved.equals("OCCH") || atomsInvolved.equals("HCCO")){
			dihedralAngleE = 0.14*(1 + Math.cos(3*calculateDihedralAngle(atom1, atom2, atom3, atom4)));
		}
		
		if (atomsInvolved.equals("O5CCH") || atomsInvolved.equals("HCCO5")){
			dihedralAngleE = 0.20*(1 + Math.cos(3*calculateDihedralAngle(atom1, atom2, atom3, atom4)));
		}
		
		if (atomsInvolved.equals("HCCH")){
			dihedralAngleE = 0.20*(1 + Math.cos(3*calculateDihedralAngle(atom1, atom2, atom3, atom4)));
		}
		
		return dihedralAngleE;
	}
	
	//return the constants for the dihedral angle energy calculation
	public static double[] getConstants(DihedralAngle di){
		
		String atomsInvolved = di.a1.getAtomType() + di.a2.getAtomType() + di.a3.getAtomType() + di.a4.getAtomType();
		
		double[] constants = new double[3];
		
		if (atomsInvolved.equals("CCOH")){
			constants[0] = 0.18;
			constants[1] = 3;
			constants[2] =0;
		}
		
		return constants;
					
	}
		
	public double getAngle(){
		return angle;
	}
	
	public Atom getAtom4(){
		return a4;
	}
}