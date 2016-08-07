import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class readPDB {
	
	private static String filename;
	static ArrayList<Atom> atomList = new ArrayList<Atom>();
	protected static Atom atom;
	
	
	public static void main(String[] args) throws IOException{
		
		Scanner scan = new Scanner(System.in);
		
		//remember that this must be Console.WriteLine
		System.out.println("Please enter the file name: ");
		
		//get filename from user
		filename = scan.nextLine();
		System.out.println(filename)
;		
		//read in file
		try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
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
		    	
		    	//Testing purposes
		    	//************************
		    	//System.out.println(line);
		    	//*************************
		    	
		    	//split by any whitespaces
		    	elements = line.split("\\s+");
		    	
		    	/*
		    	 * ***************************
		    	for (String i: elements){
		    		System.out.print(i + " ");
		    	}
		    	********************************
		    	*/
		    	
		    	//System.out.println();
		    	
		    	//get the type of atom it is, which is the last element
		    	String atomType = elements[11];
		    	
		    	//create atoms by atom type
		    	switch(atomType){
		    	case "C": 
		    		atom = new Carbon(Integer.parseInt(elements[1]), Double.parseDouble(elements[5]), Double.parseDouble(elements[6]), Double.parseDouble(elements[7])  );
		    		atomList.add(atom);
		    		break;
		    		
		    	case "H":
		    		atom = new Hydrogen(Integer.parseInt(elements[1]), Double.parseDouble(elements[5]), Double.parseDouble(elements[6]), Double.parseDouble(elements[7])  );
		    		atomList.add(atom);
		    		break;
		    		
		    	case "O": 
		    		atom = new Oxygen(Integer.parseInt(elements[1]), Double.parseDouble(elements[5]), Double.parseDouble(elements[6]), Double.parseDouble(elements[7])  );
		    		atomList.add(atom);
		    		break;
		    	}	
		    
		    }
		    
		    /*
		     * ******************************************
		       	for (Atom j: atomList){
		       		System.out.print(j.getID() + " ");
		       	}
		       	******************************************
		      */ 	
		}
		
		catch(FileNotFoundException e){
			
			//remember Console.WriteLine
			System.out.println("File does not exist.");
		}
		
		
		
	}
}
