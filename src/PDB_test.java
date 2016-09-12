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
import resources.Molecule;
import resources.Optimum;


public class PDB_test {

	static String pdb1 = "aDManOMe.pdb"; //total of 27 atoms; total of 27 bonds; total of 48 angles; total of 69 dihedrals
	static String pdb2 = "aDGlc12aLRha13aDMan.pdb"; //total of 65 atoms; total of 67 bonds; total of 123 angles; total of 195 dihedrals
	static String pdb3 = "aDGal14aDGlc.pdb"; // total of 45 atoms; total of 46 bonds; total of 83 angles; total of 132 dihedrals
	static String pdb4 = "aDGal13bDXyl.pdb"; // total of 41 atoms; total of 42 bonds; total of 76 angles; total of 120 dihedrals
	
	// lists before energy of molecule is minimized
	static ArrayList<Atom> atoms = new ArrayList<Atom>();
	static ArrayList<Bond> bonds = new ArrayList<Bond>();
	static ArrayList<DihedralAngle> dihedrals = new ArrayList<DihedralAngle>();
	static ArrayList<Angle> angles = new ArrayList<Angle>();
	
	// Lists after energy of molecule is minimized
	static ArrayList<Atom> atomsNew = new ArrayList<Atom>();
	static ArrayList<Bond> bondsNew = new ArrayList<Bond>();
	static ArrayList<DihedralAngle> dihedralsNew = new ArrayList<DihedralAngle>();
	static ArrayList<Angle> anglesNew = new ArrayList<Angle>();
	
	static PDB tester = new PDB();
	static Molecule molTest;
	
	static double initialEnergy, finalEnergy;
	
	@BeforeClass
	// load the pdb file and run the plugin
    public static void SetUp() throws IOException {
		
        tester.readFromPDB(pdb4);
        atoms = tester.atomList;
        molTest = new Molecule (atoms);
        bonds = molTest.getBondList();      
        angles = molTest.getAngleList();
        
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
        
        
    }
	
	
	@Test
	// Test that the plugin loads in the correct number of atoms.
	public void testNumAtoms() throws Exception {
		assertEquals("The program should load 41 atoms", 41, atoms.size());
	}
	
	@Test
	//Test that the plugin loads creates the correct number of bond objects
	public void testNumBonds() throws Exception {
		assertEquals("The program should load 42 bonds", 42, bondsNew.size());
	}
	
	@Test
	// Test that the plugin creates the correct number of angles
	public void testNumAngles() throws Exception {
		assertEquals("The program should load 76 angles", 76, angles.size());
	}
	
	@Test
	// Test that the plugin creates the correct number of dihedral angles
	public void testNumDihedrals() throws Exception {
		assertEquals("The program should load 120 dihedrals", 120, dihedrals.size());
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
				System.out.print("The dihedrals are different.");
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
			if (!(atoms.get(i).getXYZ().equals(atomsNew.get(i).getXYZ()))){
				System.out.println("The positions of the atoms have changed.");
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
			
			//check if the bond exists between the same atoms
			if( !(bonds.get(i).getAtom1().getID() == bondsNew.get(i).getAtom1().getID()) && !(bonds.get(i).getAtom2().getID() == bondsNew.get(i).getAtom2().getID())){
				System.out.println("Old : " +bonds.get(i).toString() + "\nNew: " + bondsNew.get(i).toString());
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
			
			//check if the angles between 2 bonds are not the same, as well as the shared atom
			if( !(angles.get(i).getAngle() == anglesNew.get(i).getAngle()) && !(angles.get(i).getSharedAtom().equals(anglesNew.get(i).getSharedAtom()))){
				System.out.println("Old : " +angles.get(i).toString() + "\nNew: " + anglesNew.get(i).toString());
				same = false;
				break;
			}
		}
		assertTrue(same == true);
		
	}
}
