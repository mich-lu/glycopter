import static org.junit.Assert.*;

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
        tester.readFromPDB(pdb1);
        atoms = tester.atomList;
        molTest = new Molecule (atoms);
        bonds = molTest.getBondList();
        angles = molTest.getAngleList();
        dihedrals = molTest.getDihedralList();
        initialEnergy = Optimum.calculateTotalEnergy();
        finalEnergy = Optimum.steepestDescent();
        atomsNew = tester.atomList;
//        bondsNew = molTest.getBondList();
//        anglesNew = molTest.getAngleList();
        dihedralsNew = Optimum.getNewDihedrals();
    }
	
	
	@Test
	// Test that the plugin loads in the correct number of atoms.
	public void testNumAtoms() throws Exception {
		assertEquals("The program should load 27 atoms", 27, atoms.size());
	}
	
	@Test
	//Test that the plugin loads creates the correct number of bond objects
	public void testNumBonds() throws Exception {
		assertEquals("The program should load 27 bonds", 27, bonds.size());
	}
	
	@Test
	// Test that the plugin creates the correct number of angles
	public void testNumAngles() throws Exception {
		assertEquals("The program should load 48 angles", 48, angles.size());
	}
	
	@Test
	// Test that the plugin creates the correct number of dihedral angles
	public void testNumDihedrals() throws Exception {
		assertEquals("The program should load 69 dihedrals", 69, dihedrals.size());
	}

	@Test
	// Test that the plugin returns a molecule that does have a minimized energy
	public void testMinimizedEnergy() throws Exception {
		assertTrue("Initial energy (" + initialEnergy + ") should be greater than the final minimized energy (" + finalEnergy + ").", initialEnergy > finalEnergy);
	}
	
	@Test
	// Test that the list of dihedrals has changed
	public void testDihedralsChanged() throws Exception {
		boolean same = true;
		for (int i = 0; i < dihedrals.size(); i++){
			if (!(dihedrals.get(i).equals(dihedralsNew.get(i)))){
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
				same = false;
				break;
			}
		}
		assertTrue(same == false);
	}
	
	//@Test
	// Test that the bonds haven't been changed during minimization
//	public void testBondsSame() throws Exception {
//		
//	}
//	
//	@Test
//	// Check that the bond angles haven't changed during minimization
//	public void testAnglesSame() throws Exception {
//		
//	}
}
