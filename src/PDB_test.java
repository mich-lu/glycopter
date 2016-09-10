import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import resources.Atom;
import resources.Bond;
import resources.DihedralAngle;
import resources.Molecule;


public class PDB_test {

	String pdb1 = "aDManOMe.pdb"; //total of 27 atoms; total of 27 bonds; total of 48 angles; total of 69 dihedrals
	String pdb2 = "aDGlc12aLRha13aDMan.pdb"; //total of 65 atoms; total of 67 bonds; total of 123 angles; total of 195 dihedrals
	String pdb3 = "aDGal14aDGlc.pdb"; // total of 45 atoms; total of 46 bonds; total of 83 angles; total of 132 dihedrals
	String pdb4 = "aDGal13bDXyl.pdb"; // total of 41 atoms; total of 42 bonds; total of 76 angles; total of 120 dihedrals
	
	ArrayList<Atom> atoms = new ArrayList<Atom>();
	ArrayList<Bond> bonds = new ArrayList<Bond>();
	ArrayList<DihedralAngle> dihedrals = new ArrayList<DihedralAngle>();
	
	PDB tester = new PDB();
	
	Molecule molTest;
	
	@Test
	// Test that the plugin loads in the correct number of atoms.
	public void testNumAtoms() throws Exception {
		tester.readFromPDB(pdb1);
		atoms = tester.atomList;
		assertEquals("The program should load 27 atoms", 27, atoms.size());
	}
	
	@Test
	//Test that the plugin loads creates the correct number of bond objects
	public void testNumBonds() throws Exception {
		atoms = tester.atomList;
		molTest = new Molecule(atoms);
		bonds = molTest.getBondList();
		assertEquals("The program should load 24 bonds", 24, bonds.size());
	}
	
	@Test
	// Test that the plugin creates the correct number of dihedral angles
	public void testNumDihedrals() throws Exception {
		atoms = tester.atomList;
		molTest = new Molecule(atoms);
		dihedrals = molTest.getDihedralList();
		assertEquals("The program should load 48 dihedrals", 48, dihedrals.size());
	}

}
