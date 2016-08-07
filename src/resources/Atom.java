
public abstract class Atom {
	int id;
	double xPos, yPos, zPos;
	
	public Atom(int ID, double x, double y, double z){
		id = ID;
		xPos=x;
		yPos=y;
		zPos=z;
	}
	
	public int getID(){
		return id;
	}
	
	public int getX(){
		return xPos;
	}
	public int getY(){
		return yPos;
	}
	public int getZ(){
		return zPos;
	}
	
}
