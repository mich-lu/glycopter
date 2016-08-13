package resources;

public class Optimum {

	final double eConstant = 0; //dieelectric constant
	
	//returns the total potential energy of the molecule
	public static double calculateTotalEnergy(){
		
		System.out.println("Calculating Total Energy...");
		return (calculateDihedralEnergy() + calculateNonbondedEnergy());
	}
	
	
	//calculates the total energy of all the dihedrals
	public static double calculateDihedralEnergy(){
			
		System.out.println("Calculating Dihedral Energy...");
		
		//force field parameters
		double k=0;  //force constant
		int n=1; 		//peridocity
		double torAngle=0;  //torsional angle
		double phaseAngle=0;  
		
		// sum up all the energies of all the dihedrals
		double dEnergy=0;
		
		for(DihedralAngle di: Molecule.dihedralList){
			
			//insert method to find all force parameters
			torAngle = di.angle;
			
			//dEnergy+= k * (1 + Math.cos(n*torAngle-phaseAngle));
		}
		
		return dEnergy;
		
	}
	
	
	//calculates the energy between non-bonded atoms
	public static double calculateNonbondedEnergy(){
		
		System.out.println("Calculating Non-bonded Energy...");
		
		double minRadius=0; //min intersection radius
		double radius=0;   //distance between two atoms
		double charge1=0;  //charge of atom1
		double charge2=0;  //charge of atom2
		
		
		double nEnergy =0;
		
		for(Interaction non: Molecule.interactionList){
		
		//calculate the radii and charges etc.
		
		radius= non.distance; 
		//nEnergy+= (Math.pow((minRadius/radius), 12) - 2*Math.pow((minRadius/radius), 6)) + (charge1*charge2)/(eConstant*radius);
		
		
		}
		return nEnergy;
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
