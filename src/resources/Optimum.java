package resources;

public class Optimum {

	
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
		
		double totalEnergy = 0;
		double interactionEnergy = 0;
		
		// loop through all interations and sum each ones energy
		for(Interaction non: mol.getInteractionList()){
			interactionEnergy = non.calculateInteractionEnergy();
			totalEnergy += interactionEnergy;
		}
		
		System.out.println(totalEnergy);
		return totalEnergy;
	}
		
	
	/*fixed-length steepest descent method
	 * 
	 *return the potential energy after this step of minimization 
	 */
	
	public static double steepestDescent(Molecule mol){
		
		System.out.println("Minimizing energy using Steepest Descent...");
		
		double stepsize = -27/50; 
		
		
		//iterate through the dihedral angles to find the OH bonds
		for( DihedralAngle di : mol.getDihedralList()){
			
			// get Interaction energies involving atom 4
			double interactionEnergy = 0;
			for(Interaction non : mol.getInteractionList()){
				if(non.getAtom1() == di.a4 || non.getAtom2() == di.a4){
					interactionEnergy += non.calculateInteractionEnergy();
				}
			}
			
			double currentEnergy= di.calculateAngleEnergy(di.a1, di.a2, di.a3, di.a4) + interactionEnergy;
			double previousEnergy =0;

			if (di.a3 instanceof Oxygen &&  di.a4 instanceof Hydrogen){
		
						
				//do the steepest descent for that angle in a certain no. of steps
				for(int i =0; i<100; i++){
					
					previousEnergy = currentEnergy;
					//calculate the derivative of the energy equation
					double derivative = firstDerivative(di);
					
					double angle2rotate = di.angle - stepsize*derivative;
					
					double [] oldXYZ = di.a4.getXYZ();
					double oldAngle = di.angle;
						
					double[] newXYZ = rotateToPhiAxis(di.a4.getXYZ(), di.a2.getXYZ(), di.a3.getXYZ(), angle2rotate);
						
					//set the new coordinates
					di.a4.setXYZ(newXYZ);
						
						
					//calculate updated dihedral angle
					di.angle = DihedralAngle.calculateDihedralAngle(di.a1, di.a2, di.a3, di.a4);
					
					// update interactions and get new energies
					interactionEnergy = 0;// reset energy
					for(Interaction non : mol.getInteractionList()){
						if(non.getAtom1() == di.a4 || non.getAtom2() == di.a4){
							non.updateDistance();
							interactionEnergy += non.calculateInteractionEnergy();
						}
					}
					
					//calculate the updated energy
					currentEnergy = di.calculateAngleEnergy(di.a1, di.a2, di.a3, di.a4) + interactionEnergy;
		
					
					//if the energy is unchanged, or if the enery has increased, change back to old values and break;
					if(currentEnergy>= previousEnergy){
							di.a4.setXYZ(oldXYZ);
							di.angle = oldAngle;
							break;
					}
					
				}
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
	
	
	//https://bitbucket.org/mkuttel/carbbuilderii/src/c6e583aab3bf7189c02a4f9060feff77190a6688/CBIIsource/Connecting.cs?at=default&fileviewer=file-view-default
	
	// modified methods from Carbuilder 
	public static double[] rotateToPhiAxis(double[] hydrogen, double[] carbon,  double[] oxygen, double angle2rotate)
    {

    	//check the direction to rotate
		if (angle2rotate <0){
			return rotateNodeAboutVector (hydrogen,  oxygen, carbon, angle2rotate);
		}
		
		else{
			return rotateNodeAboutVector(hydrogen ,carbon , oxygen ,angle2rotate);
		}
    }
	
	
	public static double[] rotateNodeAboutVector(double[] hydrogen, double[]  pta, double[] ptb,double angle2rotate)
	{ 
		
		double[] newXYZ = hydrogen;
		
		
		double[] oldXYZ = hydrogen;

		newXYZ = Rotation.getNewPoint (pta, ptb , angle2rotate, oldXYZ);

		return newXYZ;
	}
	
}