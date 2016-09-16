# Makefile for the CarbBuilder plugin
# Development team: Alison Hoernle, Kristin Kinmont, and Michelle Lu

JFLAGS = -g
JC = javac

.SUFFIXES: .java .class
.java .class:
	$(JC) $(JFLAGS) $*.java
	
CLASSES = \
	Minimizer.java \
	resources\Atom.java \
	resources\Bond.java \
	resources\Carbon.java \
	resources\DihedralAngle.java \
	resources\Hydrogen.java \
	resources\Interaction.java \
	resources\Molecule.java \
	resources\Optimum.java \
	resources\Oxygen.java \
	resources\Angle.java \
	resources\Rotation.java \

MAIN: Minimizer

default: classes

classes: $(CLASSES:.java=.class)

run:
	java PDB ${args}
	
clean:
	$(RM) *.class