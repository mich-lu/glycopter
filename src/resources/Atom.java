package resources;


public abstract class Atom {
	int id;
	double xPos, yPos, zPos;
	String atomType;
	
	public Atom(int ID, double x, double y, double z){
		id = ID;
		xPos=x;
		yPos=y;
		zPos=z;
	}
	
	public int getID(){
		return id;
	}
	
	public double getX(){
		return xPos;
	}
	public double getY(){
		return yPos;
	}
	public double getZ(){
		return zPos;
	}
	
	public void setX(double x){
		xPos = x;
	}
	
	public void setY(double y){
		yPos = y;
	}
	
	public void setZ(double z){
		zPos = z;
	}
	
	public String getAtomType(){
		return atomType;
	}
}
