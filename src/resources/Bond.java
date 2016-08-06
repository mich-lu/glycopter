
public class Bond {

	int bondID;
	int noBonds;
	
	public Bond(){
		bondID = 0;
		noBonds = 0;
	}
	
	public Bond(Atom atom1, Atom atom2){
		bondID = noBonds;
		incBonds();
	}
	
	public void incBonds(){
		noBonds ++;
	}
}
