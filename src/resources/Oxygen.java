/*
 * 6 August 2016
 * Glycopter project
 * Instantiation of Atom
 */

package resources;


public class Oxygen extends Atom{

	public Oxygen(int ID, double x, double y, double z, String an) {
		super(ID, x, y, z, an);
		
		//check if it is an O5 or an O
		if (atomAndNum.equals("O5")){
			atomType = "O5";
		}
		
		else{
			atomType="O"; 
		}
		
		System.out.println(atomType);
	}
}
