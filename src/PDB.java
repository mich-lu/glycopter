/*
 * 6 August 2016
 * Glycopter project
 * This is the main class that reads in the PDB file, calls all the other classes to create the molecule,
 * calculate the energy, and minimize the energy, and then to write the updated molecule to a new PDB file.
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

import resources.Angle;
import resources.Atom;
import resources.Carbon;
import resources.Hydrogen;
import resources.Molecule;
import resources.Optimum;
import resources.Oxygen;


public class PDB {
	
	private static String inputFile, filename, fileOutName;
	static ArrayList<Atom> atomList = new ArrayList<Atom>();
	protected static Atom atom;
	static String part1, atomAndNum, part4, part5, part9, part10, part11, atomType;//These are all the aspects in the PDB file that need to be the same in the output, and so need to be associated with each atom.
	static int ID;
	static double xPos, yPos, zPos;
	static double initialEnergy, finalEnergy;
	static ArrayList<Atom> newAtomList = new ArrayList<Atom>();
	
	public static void main(String[] args) throws IOException{
		filename = args[0];
		fileOutName = args[1];
		//readFromPDB(); //load the atoms and all the information associated with each atom
		inputFile = args[0];
		readFromPDB(inputFile); //load the atoms and all the information associated with each atom
		
		Molecule mol = new Molecule(atomList); //create the molecule object
		//calculate the energy of the molecule
		System.out.println();
		System.out.println("***************************************************************");
		long startTime = System.currentTimeMillis();
		initialEnergy = Optimum.calculateTotalEnergy(mol);
		System.out.println();
		System.out.println("***************************************************************");
		System.out.println("The current energy of the molecule is: " + initialEnergy);
		System.out.println();
		System.out.println("***************************************************************");
		//minimize the energy of the molecule
		finalEnergy = Optimum.steepestDescent(mol);
		long endTime = System.currentTimeMillis();
		System.out.println("The initial energy of the molecule was: " + initialEnergy + "\n" + "The minimized energy of the molecule is: " + finalEnergy);
		System.out.println();
		System.out.println("***************************************************************");
		System.out.println("new atoms = " + newAtomList.size());
		long runTime = endTime - startTime;
		writeToPDB(atomList); //output the new molecule representation to PDB
		System.out.println("The calculations took: " + runTime + " ms");
	
	}
	
//	public static void readFromPDB() throws IOException{
//		
//		Scanner scan = new Scanner(System.in);
	
	public static void readFromPDB(String inputFile) throws IOException{
		filename = inputFile;
		
		//read in file
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		try{
		    String line;
		    String[] elements;
		    
		 // process the line.
		    while ((line = reader.readLine()) != null) {
		       
		    	//skip the first line
		    	if( line.startsWith("REMARK")){
		    		continue;}
		    	
		    	//skip the last line
		    	else if( line.startsWith("END")){
		    		break;}
		    	
		    	//split by any whitespaces
		    	elements = line.split("\\s+");
		    	
		    	
		    	//Get the elements of PDB associated with each atom
		    	part1 = elements[0];
		    	ID = Integer.parseInt(elements[1]); // Second element is the ID
		    	atomAndNum = elements[2]; //3rd element is the atom with it's number in the molecule
		    	part4 = elements[3]; //4th element in the PDB file (AMAN)
		    	part5 = elements[4]; //a number associated with the atom
		    	xPos = Double.parseDouble(elements[5]); //the x position of the atom
		    	yPos = Double.parseDouble(elements[6]); //the y position of the atom
		    	zPos = Double.parseDouble(elements[7]); //the z position of the atom
		    	part9 = elements[8]; //the 9th element in the PDB file
		    	part10 = elements[9]; //the 10th element in the PDB file
		    	part11 = elements[10]; //the 11th element in the PDB file (CARB)
		    	atomType = elements[11]; //get the type of atom it is, which is the last element in PDB file
		    	
		    	//create atoms by atom type
		    	switch(atomType){
		    	case "C": 
		    		atom = new Carbon(ID, xPos, yPos, zPos, atomAndNum);
		    		atomList.add(atom);
		    		break;
		    		
		    	case "H":
		    		atom = new Hydrogen(ID, xPos, yPos, zPos, atomAndNum);
		    		atomList.add(atom);
		    		break;
		    		
		    	case "O": 
		    		atom = new Oxygen(ID, xPos, yPos, zPos, atomAndNum);
		    		atomList.add(atom);
		    		break;
		    	}	
		    	
		    	atom.addInformation(part1, part4, part5, part9, part10, part11);
		    
		    }
		    	
		}
		
		catch(FileNotFoundException e){
			
			//remember Console.WriteLine
			System.out.println("File does not exist.");
		}
		
			
	}
	
	public static void writeToPDB(ArrayList<Atom> atoms){
		
		//more efficient way of building strings
		StringBuilder sb = new StringBuilder();
		
		Atom currentAtom;
		try {
			File file = new File(fileOutName); 
			
			file.createNewFile();
			
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			
			BufferedWriter writer = new BufferedWriter(fw);
			
			//round off the coordinates to 3 decimal places
			DecimalFormat df = new DecimalFormat("#.###");
			df.setRoundingMode(RoundingMode.CEILING);
			
			sb.append("REMARK minimized generated coordinate pdb file " + System.getProperty("line.separator")); //initial opening line on PDB file
			
			for (int i = 0; i < atoms.size(); i++){
				currentAtom = atoms.get(i);
				sb.append(String.format("%-9s", currentAtom.getPart1())); //ATOM and 5 spaces
				sb.append(String.format("%2s", currentAtom.getID())); //right justified
				sb.append("  "); //2 spaces
				sb.append(String.format("%-4s", currentAtom.getAtomAndNum())); //The atom and its number plus and 1 or 2 spaces (left justified)
				sb.append(String.format("%-8s", currentAtom.getPart4())); //AMAN and 4 spaces
				sb.append(String.format("%-7s", currentAtom.getPart5())); //Some number and 6 spaces
				sb.append(String.format("%6s", df.format(currentAtom.getX()))); //x-coordinate, right justified
				sb.append("  "); //2 spaces
				sb.append(String.format("%6s", df.format(currentAtom.getY()))); //y-coordinate, right justified
				sb.append("  "); //2 spaces
				sb.append(String.format("%6s", df.format(currentAtom.getZ()))); //z-coordinate, right justified
				sb.append("  "); //2 spaces
				sb.append(String.format("%-6s", currentAtom.getPart9())); //The element and 2 spaces
				sb.append(String.format("%-10s", currentAtom.getPart10())); //The element and 6 spaces
				sb.append(String.format("%-6s", currentAtom.getPart11())); //The element (CARB) and 1 space
				sb.append(currentAtom.getAtomType() + System.getProperty("line.separator"));
				
				
			}
	
			writer.write(sb.toString());
			writer.write("END");
			writer.close();
			System.out.println("Done");
		} catch (IOException e) {
			e.printStackTrace();
			
		}
	}
	
	// For testing purposes
	public double getInitialEnergy(){
		return initialEnergy;
	}
	
	//For testing purposes
	public double getFinalEnergy(){
		return finalEnergy;
	}
	
	//For testing purposes
}
