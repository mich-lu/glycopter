package resources;

public class Optimum {

	final double eConstant = 0; //dieelectric constant
	
	//returns the total potential energy of the molecule
	public static double calculateTotalEnergy(){
		
		System.out.println("Calculating Total Energy...");
		return (calculateDihedralEnergy() + calculateNonbondedEnergy());
	}
	
	
	/*
	 * calculates the total energy of all the dihedrals by looping through the list of dihedral angles, calculating the individual
	 * energies of each angle, and adding them to the total list
	 */
	
	public static double calculateDihedralEnergy(){
			
		System.out.println("Calculating Dihedral Energy...");
		
		// sum up all the energies of all the dihedrals
		double dihedralEnergy=0;
		double angleEnergy = 0;
		
		// Loop to calculate the energy of each individual dihedral angle in the molecule and add it to the total energy
		for(DihedralAngle di: Molecule.dihedralList){
			System.out.println("Di angle being calculated: "+di.a1+di.a2+di.a3+ di.a4);
			angleEnergy = di.calculateAngleEnergy(di.a1, di.a2, di.a3, di.a4);
			
			dihedralEnergy += angleEnergy;
		}
		
		System.out.println(dihedralEnergy);
		return dihedralEnergy;
		
	}
	
	
	//calculates the energy between non-bonded atoms
	public static double calculateNonbondedEnergy(){
		
		System.out.println("Calculating Non-bonded Energy...");
		
		double epsilon = 0;
		double minRadius = 0; //min intersection radius
		double radius = 0;   //distance between two atoms
		
		double nEnergy = 0; //total non-bonded energy
		
		for(Interaction non: Molecule.interactionList){
			// get appropriate constants
			Double[] constants1 = getConstants(non.atom1);
			Double[] constants2 = getConstants(non.atom2);
			
			epsilon = constants1[0] + constants2[0];
			minRadius = constants1[1] + constants2[1];
			radius= non.distance; 
			
			// get energy by substituting into formula
			//nEnergy = epsilon*(Math.pow((minRadius/radius), 12) - 2*Math.pow((minRadius/radius), 6));
			nEnergy = constants1[0]*(Math.pow((constants1[1]/radius), 12) - 2*Math.pow((constants1[1]/radius), 6))+
					  constants2[0]*(Math.pow((constants2[1]/radius), 12) - 2*Math.pow((constants2[1]/radius), 6));
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
	
	public static double steepestDescent(float stepSize, int noSteps){
		
		System.out.println("Minimizing energy using Steepest Descent...");
		
		float gamma=stepSize; //mall number that forces the algorithm to make small jumps, value changes with algorithm using line search
		float zero=0; //value that is close to zero
		
		
		//iterate through the max no. of steps
		for(int i =0; i<noSteps; i++){
			
			//calculate the derivative of the energy equation
			double dummy = 1; //dummy parameter
			double derivative = firstDerivative(dummy);
			
			//check if gradient is close to zero, if so minimum is found
			if(derivative == zero){
				break;
			}
			
		
			//else update x, y, z positions and recalculate angles
			else{
			//eg. atom.x = x - (gamma*derivative)
				System.out.println("Updating atom positions and dihedral angles...");
			}
		}
		
		System.out.println("Molecule is minimized.");
		return calculateTotalEnergy();
	}
	
	
	// calculates the first derivative with parameters values
	public static double firstDerivative(double value){
		
		System.out.println("Finding the first derivative...");
		
		return value;
	}
	
	
}
