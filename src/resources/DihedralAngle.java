package resources;


public class DihedralAngle{
	
	double angle;
	Atom a1, a2, a3, a4;

	public DihedralAngle(Atom atom1, Atom atom2, Atom atom3, Atom atom4){
		a1 = atom1;
		a2 = atom2;
		a3 = atom3;
		a4 = atom4;
		angle = calculateAngle();
	}

	public double calculateAngle(){
		// calculate the distnace between the atoms;
		double d12 = Math.sqrt(sq(a2.getX()-a1.getX()) + sq(a2.getY()-a1.getY()) + sq(a2.getZ()-a1.getZ()));
		double d13 = Math.sqrt(sq(a3.getX()-a1.getX()) + sq(a3.getY()-a1.getY()) + sq(a3.getZ()-a1.getZ()));
		double d23 = Math.sqrt(sq(a3.getX()-a2.getX()) + sq(a3.getY()-a2.getY()) + sq(a3.getZ()-a2.getZ()));
		double d34 = Math.sqrt(sq(a4.getX()-a3.getX()) + sq(a4.getY()-a3.getY()) + sq(a4.getZ()-a3.getZ()));
		double d24 = Math.sqrt(sq(a4.getX()-a2.getX()) + sq(a4.getY()-a2.getY()) + sq(a4.getZ()-a2.getZ()));
		double d14 = Math.sqrt(sq(a4.getX()-a1.getX()) + sq(a4.getY()-a1.getY()) + sq(a4.getZ()-a1.getZ()));


		// dihedral defined as: cos(angle) = P/sqrt(Q). Where P and Q are defined as;
		double P = sq(d12)*(sq(23) + sq(34) - sq(d24)) + 
				sq(d23)*(sq(23) + sq(34) - sq(d24)) + 
				sq(d13)*(sq(23) + sq(34) - sq(d24)) -
				2*sq(d23)*sq(d14);

		double Q = (d12 + d23 + d13)*(d12 + d23 - d13)*
				(d12 - d23 + d13)*(-d12 + d23 + d13)*
				(d23 + d34 + d24)*(d23 + d34 - d24)*
				(d23 - d34 + d24)*(-d23 + d34 + d24);

		// therefore, angle is;
		double angle = Math.acos(P/Math.sqrt(Q));

		return angle;
	}
	
	// I added this method to make the code in identifyDihedrals() simpler
	public double sq(double number){
		return Math.pow(number,2);
	}
	
	public String toString(){
		return "Angle: " + angle + "atom1: " + a1.id + ", atom2: " + a2.id + "atom3: " + a3.id + ", atom4: " + a4.id; 
	}
}