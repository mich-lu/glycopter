# Makefile for the CarbBuilder plugin
# Development team: Alison Hoernle, Kristin Kinmont, and Michelle Lu

JFLAGS = -g
JC = javac

.SUFFIXES: .java .class
.java .class:
	$(JC) $(JFLAGS) $*.java
	
CLASSES = \
	PDB.java \
	Atom.java \
	Bond.java \
	Carbon.java \
	DihedralAngle.java \
	Hydrogen.java \
	Interaction.java \
	Molecule.java \
	Optimum.java \
	Oxygen.java

Default: classes

classes: $(CLASSES:.java=.class)

run:
	java PDB final.pdb
	
clean:
	$(RM) *.class