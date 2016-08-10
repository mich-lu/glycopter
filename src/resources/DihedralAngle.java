public class DihedralAngle{
	
	int angle;
	Atom atom1, atom2, atom3, atom4;

	public DihedralAnge(Atom a1, Atom a2, Atom a3, Atom a4){
		atom1 = a1;
		atom2 = a2;
		atom3 = a3;
		atom4 = a4;
		angle = calculateAngle();
	}

	public int calculateAngle(){
		// calculate the distnace between the atoms;
		int d12 = Math.sqrt(sq(a2.getX()-a1.getX()) + sq(a2.getY()-a1.getY()) + sq(a2.getZ()-a1.getZ()));
		int d23 = Math.sqrt(sq(a3.getX()-a2.getX()) + sq(a3.getY()-a2.getY()) + sq(a3.getZ()-a2.getZ()));
		int d34 = Math.sqrt(sq(a4.getX()-a3.getX()) + sq(a4.getY()-a3.getY()) + sq(a4.getZ()-a3.getZ()));
		int d24 = Math.sqrt(sq(a4.getX()-a2.getX()) + sq(a4.getY()-a2.getY()) + sq(a4.getZ()-a2.getZ()));
		int d14 = Math.sqrt(sq(a4.getX()-a1.getX()) + sq(a4.getY()-a1.getY()) + sq(a4.getZ()-a1.getZ()));


		// dihedral defined as: cos(angle) = P/sqrt(Q). Where P and Q are defined as;
		int P = sq(d12)*(sq(23) + sq(34) - sq(d24)) + 
				sq(d23)*(sq(23) + sq(34) - sq(d24)) + 
				sq(d13)*(sq(23) + sq(34) - sq(d24)) -
				2*sq(d23)*sq(d14);

		int Q = (d12 + d23 + d13)*(d12 + d23 - d13)*
				(d12 - d23 + d13)*(-d12 + d23 + d13)*
				(d23 + d34 + d24)*(d23 + d34 - d24)*
				(d23 - d34 + d24)*(-d23 + d34 + d24);

		// therfore, angle is;
		int angle = Math.acos(P/Math.sqrt(Q));

		return angle;
	}
}