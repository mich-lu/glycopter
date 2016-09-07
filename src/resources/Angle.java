package resources;

public class Angle {
	double angle;
	Bond b1, b2;
	Atom shared;
	public static Double dihedralAngleE;
	Atom[] uniqueList;

	public Angle(Bond bond1, Bond bond2, Atom atom){
		b1 = bond1;
		b2 = bond2;
		shared = atom;
		angle = calculateAngle(b1, b2, shared);
	}

	public double calculateAngle(Bond B1, Bond B2, Atom shared){ // calculate by finding angle between 2 planes
		// calculate distances between 3 angles
		getUniqueAtoms(B1, B2, shared);
		
		Atom a1 = uniqueList[0];
		Atom a2 = uniqueList[1];
		Atom a3 = uniqueList[2];
		
		Double d1 = Math.sqrt(Math.pow((a2.getX() - a1.getX()), 2) + Math.pow((a2.getY() - a1.getY()), 2) + Math.pow((a2.getZ() - a1.getZ()),  2));
		Double d2 = Math.sqrt(Math.pow((a3.getX() - a2.getX()), 2) + Math.pow((a3.getY() - a2.getY()), 2) + Math.pow((a3.getZ() - a2.getZ()),  2));
		Double d3 = Math.sqrt(Math.pow((a3.getX() - a1.getX()), 2) + Math.pow((a3.getY() - a1.getY()), 2) + Math.pow((a3.getZ() - a1.getZ()),  2));

		// d3 represents the side opposite the angle we are calculating, therefore using trig cosine rule;
		angle = Math.acos((Math.pow(d1, 2) + Math.pow(d2, 2) - Math.pow(d3, 2))/(2*d1*d2));
		
		return angle;
	}
	
	public void getUniqueAtoms(Bond B1, Bond B2, Atom shared){
		uniqueList = new Atom[3];
		uniqueList[1] = shared;
		if (b1.atom1 != shared){
			uniqueList[0] = b1.atom1;
		}
		else{
			uniqueList[0] = b1.atom2;
		}
		
		if (b2.atom1 != shared){
			uniqueList[2] = b2.atom1;
		}
		else{
			uniqueList[2] = b2.atom2;
		}
	}
	
	public String toString(){
		return "Angle: " + angle + ", bond 1: " + b1.bondID + ", bond 2: " + b2.bondID; 
	}
}
