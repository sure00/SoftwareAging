package Y2U.DataStructure;

import java.util.ArrayList;
import java.util.List;

public class Regions {
	protected String name;	
	protected List<State> stateList;
	protected List<Transition> transitionList;
	protected String initialStateID;
	protected List<String> declarationList;
	
	protected List<String> verticesList;
	
	public Regions(String name) {
		this.name = name;		//template name
		stateList = new ArrayList<State> ();	//location 
		transitionList = new ArrayList<Transition> ();	//transition
		declarationList = new ArrayList<String> ();
	}
	
	public String getName() {
		return name;
	}
}
