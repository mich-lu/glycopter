/*
 * 6 August 2016
 * Glycopter project
 * Abstract class that keeps all the variables and methods associated with an atom
 */

package resources;

import java.util.ArrayList;

public abstract class Atom {
	int id;
	double xPos, yPos, zPos;
	String atomType;
	String part1, part3, part4, part5, part9, part10, part11; //The extra information needed to load into the PDB file;
	ArrayList<Atom> bondedToList;
	
	public Atom(int ID, double x, double y, double z){
		id = ID;
		xPos=x;
		yPos=y;
		zPos=z;
		bondedToList = new ArrayList<Atom>();
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
	
	/* This method and all the getter methods that follow are just to record all the extra information 
	 * in the pdb file associated with each atom in order to be able to write to the output PDB.
	 */
	public void addInformation(String a, String b, String c, String d, String e, String f, String g){
		part1 = a;
		part3 = b;
		part4 = c;
		part5 = d;
		part9 = e;
		part10 = f;
		part11 = g;
	}
	
	public String getPart1(){
		return part1;
	}
	
	public String getPart3(){
		return part3;
	}
	
	public String getPart4(){
		return part4;
	}
	
	public String getPart5(){
		return part5;
	}
	
	public String getPart9(){
		return part9;
	}
	
	public String getPart10(){
		return part10;
	}
	
	public String getPart11(){
		return part11;
	}
	
	public ArrayList<Atom> getBonds(){
		return bondedToList;
	}
}
