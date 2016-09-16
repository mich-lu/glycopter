import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import resources.Angle;
import resources.Atom;
import resources.Bond;
import resources.DihedralAngle;
import resources.Hydrogen;
import resources.Molecule;
import resources.Optimum;


public class Test3 {

	static String pdb3 = "aDGal14aDGlc.pdb"; // total of 45 atoms; total of 46 bonds; total of 83 angles; total of 132 dihedrals
	
	// lists before energy of molecule is minimized
	static ArrayList<Atom> atoms = new ArrayList<Atom>();
	static ArrayList<String> atomString = new ArrayList<String>();
	static ArrayList<Bond> bonds = new ArrayList<Bond>();
	static ArrayList<String> bondString = new ArrayList<String>();
	static ArrayList<DihedralAngle> dihedrals = new ArrayList<DihedralAngle>();
	static ArrayList<Angle> angles = new ArrayList<Angle>();
	static ArrayList<String> angleString = new ArrayList<String>();
	
	// Lists after energy of molecule is minimized
	static ArrayList<Atom> atomsNew = new ArrayList<Atom>();
	static ArrayList<Bond> bondsNew = new ArrayList<Bond>();
	static ArrayList<DihedralAngle> dihedralsNew = new ArrayList<DihedralAngle>();
	static ArrayList<Angle> anglesNew = new ArrayList<Angle>();
	
	static Minimizer tester = new Minimizer();
	static Molecule molTest;
	
	static double initialEnergy, finalEnergy;
	
	@BeforeClass
	// load the pdb file and run the plugin
    public static void SetUp() throws IOException {
		
        tester.readFromPDB(pdb3);
        atoms = tester.atomList;
        
        //add old atoms to a list of strings
        for(int i = 0; i< atoms.size(); i++){
        	
        	atomString.add(atoms.get(i).toString());
        	
        }
        
        molTest = new Molecule (atoms);
        
        bonds = molTest.getBondList(); 
        
        //add bonds to a list of strings
        for(int j = 0; j< bonds.size(); j++){
        	
        	bondString.add(bonds.get(j).toString());
        	
        }
        
        angles = molTest.getAngleList();
        
        //add old angles to a list of strings
        for(int k = 0; k< angles.size(); k++){
        	
        	angleString.add(angles.get(k).toString());
        	
        }
        
        //add original atoms to a new list
        molTest.identifyDihedrals(atoms, dihedrals);
        
        initialEnergy = Optimum.calculateTotalEnergy(molTest);
        finalEnergy = Optimum.steepestDescent(molTest);
        
        //get updated the molecule for testing purposes
        
        atomsNew = molTest.getAtomList();
        //recalculate bonds and add them to new list
        molTest.identifyBonds(atomsNew, bondsNew);
        
        //recalculate angles from the new bonds and add to new list
        molTest.identifyAngles(bondsNew, anglesNew);
        
        dihedralsNew = molTest.getDihedralList();
        
        for(int j =0; j< bonds.size(); j++){
        	
        }
        
    }
	
	
	@Test
	// Test that the plugin loads in the correct number of atoms.
	public void testNumAtoms() throws Exception {
		assertEquals("The program should load 45 atoms", 45, atoms.size());
	}
	
	@Test
	//Test that the plugin loads creates the correct number of bond objects
	public void testNumBonds() throws Exception {
		assertEquals("The program should load 46 bonds", 46, bondsNew.size());
	}
	
	@Test
	// Test that the plugin creates the correct number of angles
	public void testNumAngles() throws Exception {
		assertEquals("The program should load 83 angles", 83, anglesNew.size());
	}
	
	@Test
	// Test that the plugin creates the correct number of dihedral angles
	public void testNumDihedrals() throws Exception {
		assertEquals("The program should load 132 dihedrals", 132, dihedralsNew.size());
	}

	@Test
	// Test that the plugin returns a molecule that does have a minimized energy
	public void testMinimizedEnergy() throws Exception {
		assertTrue("Initial energy (" + initialEnergy + ") should be greater than the final minimized energy (" + finalEnergy + ").", initialEnergy > finalEnergy);
	}
	
	@Test
//	// Test that the list of dihedrals has changed
	public void testDihedralsChanged() throws Exception {
		boolean same = true;
		for (int i = 0; i < dihedrals.size(); i++){
			if (!(dihedrals.get(i).getAngle() == dihedralsNew.get(i).getAngle())){
				System.out.println("Old: "+ dihedrals.get(i).toString() + "\nNew: " + dihedralsNew.get(i).toString());
				System.out.println("The dihedrals are different.");
				same = false;
				break;
			}
		}
		
		assertTrue(same == false);
	}
	
	@Test
//	// Check that the number of atoms has not changed in the minimized molecule
	public void testNumberAtomsSame() throws Exception {
		System.out.println("atoms = " +atoms.size());
		System.out.println("atomsnew = " + atomsNew.size());
		
		assertTrue(atoms.size() == atomsNew.size());
		
	}
	
	@Test
	// Test that the positions of atoms in the minimized molecule have changed
	public void testAtomPosChanged() throws Exception {
		boolean same = true;
		for (int i = 0; i < atoms.size(); i++){
			
			if ( !(atomString.get(i).equals(atomsNew.get(i).toString()))){
				System.out.println("The positions of the atoms have changed.");
				System.out.println("Old: " + atomString.get(i) + "\nNew: " + atomsNew.get(i).toString());
				same = false;
				break;
			}
		}
		assertTrue(same == false);
	}
		
	
	@Test
	// Test that the bonds haven't been changed during minimization
	public void testBondsSame() throws Exception {
		
		boolean same = true;
		
		for (int i =0; i < bonds.size(); i++){
			//System.out.println("Old : " + bondString.get(i)+ "\nNew: " + bondsNew.get(i).toString());
			if( !(bondString.get(i).equals(bondsNew.get(i).toString())) ){
				System.out.println("Bonds are not the same");
				System.out.println("Old : " + bondString.get(i)+ "\nNew: " + bondsNew.get(i).toString());
				same = false;
				break;
			}
		}
		
		assertTrue(same == true);		
	}
//	
	@Test
	// Check that the bond angles haven't changed during minimization
	public void testAnglesSame() throws Exception {
		
		boolean same = true;
		
		for (int i =0; i < angles.size(); i++){
			
			if( !(angleString.get(i).equals(anglesNew.get(i).toString()))){
				System.out.println("Angles hve changed");
				System.out.println("Old : " +angleString.get(i) + "\nNew: " + anglesNew.get(i).toString());
				same = false;
				break;
			}
		}
		assertTrue(same == true);
		
	}
}
