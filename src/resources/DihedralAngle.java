package resources;


public class DihedralAngle{
	
	double angle;
	Atom a1, a2, a3, a4;

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
		return angleDeg;
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
}