package resources;

public class Optimum {

	//static ArrayList<DihedralAngle> newDihedrals = new ArrayList<DihedralAngle>();
	
	final double eConstant = 0; //dieelectric constant
	
	//returns the total potential energy of the molecule
	public static double calculateTotalEnergy(Molecule mol){
		
		System.out.println("Calculating Total Energy...");
		return (calculateDihedralEnergy(mol) + calculateNonbondedEnergy(mol));
	}
	
	
	/*
	 * calculates the total energy of all the dihedrals by looping through the list of dihedral angles, calculating the individual
	 * energies of each angle, and adding them to the total list
	 */
	
	public static double calculateDihedralEnergy(Molecule mol){
			
		System.out.println("Calculating Dihedral Energy...");
		
		// sum up all the energies of all the dihedrals
		double dihedralEnergy=0;
		double angleEnergy = 0;
		
		// Loop to calculate the energy of each individual dihedral angle in the molecule and add it to the total energy
		for(DihedralAngle di: mol.getDihedralList()){
			//System.out.println("Di angle being calculated: "+di.a1+di.a2+di.a3+ di.a4);
			angleEnergy = di.calculateAngleEnergy(di.a1, di.a2, di.a3, di.a4);
			
			//angleEnergy = k * (1 + Math.cos(n*di.angle-phaseAngle));
			
			dihedralEnergy += angleEnergy;
		}
		
		System.out.println(dihedralEnergy);
		return dihedralEnergy;
		
	}
	
	
	//calculates the energy between non-bonded atoms
	public static double calculateNonbondedEnergy(Molecule mol){
		
		System.out.println("Calculating Non-bonded Energy...");
		
		double epsilon = 0;
		double minRadius = 0; //min intersection radius
		double radius = 0;   //distance between two atoms
		
		double nEnergy = 0; //total non-bonded energy
		
		for(Interaction non: mol.getInteractionList()){
			// get appropriate constants
			Double[] constants1 = getConstants(non.atom1);
			Double[] constants2 = getConstants(non.atom2);
			
			epsilon = Math.sqrt(constants1[0]*constants2[0]);
			minRadius = (constants1[1] + constants2[1])/2;
			radius= non.distance; 
			
			// get energy by substituting into formula
			nEnergy = epsilon*(Math.pow((minRadius/radius), 12) - 2*Math.pow((minRadius/radius), 6));
			}
		
		System.out.println(nEnergy);
		return nEnergy;
	}
		
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
	
	/*fixed-length steepest descent method
	 * 
	 *return the potential energy after this step of minimization 
	 */
	
	public static double steepestDescent(Molecule mol){
		
		System.out.println("Minimizing energy using Steepest Descent...");
		
		double stepsize = 27/50; 
		
		//iterate through the dihedral angles to find the OH bonds
		for( DihedralAngle di : mol.getDihedralList()){

			if (di.a3 instanceof Oxygen &&  di.a4 instanceof Hydrogen){
		
				//System.out.println(di.a3.atomType + " " + di.a4.atomType);
				
				
				//do the steepest descent for that angle in a certain no. of steps
				for(int i =0; i<50; i++){
					
					//calculate the derivative of the energy equation
					double derivative = firstDerivative(di);
					//System.out.println("Derivative = " + derivative);
					
					//check if gradient is close to zero or there was no change from the previous derivative, the minimum is found
					if(derivative == 0.0000001 || di.angle < 0.0000001){
						break;
					}
					
					//else rotate the angle
					else{
						
						double angle2rotate = di.angle - stepsize*derivative;
						
//						//find the normal between 3 points
//						double[] normal = Rotation.getNormal(di.a2.getXYZ(), di.a3.getXYZ(), di.a4.getXYZ());
//						
//						//get the current coordinates of the angle that you want to rotate
//						double [] oldXYZ = di.a4.getXYZ();
//						//System.out.println("Old: "+ di.a4.getX() + " " + di.a4.getY() + " " + di.a4.getZ());
//						
//						//find the new coordinates about the stationary angle
//						double[] newXYZ = Rotation.getNewPointFromNormal(di.a3.getXYZ(), normal, angle2rotate, oldXYZ);
//						
						
						double[] newXYZ = rotateAtom(di.a2, di.a3, di.a4, angle2rotate);
						
						//set the new coordinates
						di.a4.setXYZ(newXYZ);
						//System.out.println("New: " +di.a4.getX() + " " + di.a4.getY() + " " + di.a4.getZ());
						
						//calculate updated dihedral angle
						di.angle = DihedralAngle.calculateAngle(di.a1, di.a2, di.a3, di.a4);
						//minAngle = new DihedralAngle(di.a1, di.a2, di.a3, di.a4);
					}
				}
				break;
			}
		}
		
		System.out.println("Molecule is minimized.");
		return calculateTotalEnergy(mol);
	}
		
	
	// calculates the first derivative with parameters values
	public static double firstDerivative(DihedralAngle di){
		
		double k = DihedralAngle.getConstants(di)[0];
		double n = DihedralAngle.getConstants(di)[1];
		double phase = DihedralAngle.getConstants(di)[2];
		
		double derivative = -(n*k)*(Math.sin(n*di.angle + phase));
		
		return derivative;
	}
	
	public static double[] rotateAtom (Atom C, Atom O, Atom H, double theta){
		
		double[] axis = {O.getX() - C.getX(), O.getY() - C.getY(), O.getZ() - C.getZ()};
		double axisMag = Math.sqrt(Math.pow(axis[0], 2) + Math.pow(axis[1], 2) + Math.pow(axis[2], 2));
		for (int i = 0; i < axis.length; i++){
			axis[i] = axis[i]/axisMag;
		}
		
		double[] vector = {H.getX() - O.getX(), H.getY() - O.getY(), H.getZ() - O.getZ()};	
		
		double[] newPoint = new double[3];
		double x, y, z;
	    double u, v, w;
	    x=vector[0];
	    y=vector[1];
	    z=vector[2];
	    u=axis[0];
	    v=axis[1];
	    w=axis[2];
	    double xPrime = u*(u*x + v*y + w*z)*(1d - Math.cos(theta)) 
	            + x*Math.cos(theta)
	            + (-w*y + v*z)*Math.sin(theta);
	    double yPrime = v*(u*x + v*y + w*z)*(1d - Math.cos(theta))
	            + y*Math.cos(theta)
	            + (w*x - u*z)*Math.sin(theta);
	    double zPrime = w*(u*x + v*y + w*z)*(1d - Math.cos(theta))
	            + z*Math.cos(theta)
	            + (-v*x + u*y)*Math.sin(theta);
	    
	    newPoint[0] = xPrime + O.getX();
	    newPoint[1] = yPrime + O.getY();
	    newPoint[2] = zPrime + O.getZ();
	    //Double[] newVec = {xPrime, yPrime, zPrime};
	    
	    return newPoint;
	}

}
