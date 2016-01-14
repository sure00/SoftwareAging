package Y2U.Transformer;

import java.util.List;

import org.w3c.dom.Element;

import Y2U.DataStructure.Automata;
import Y2U.DataStructure.Model;
import Y2U.DataStructure.State;
import Y2U.DataStructure.Transition;

public class U2YLocationTransformer extends U2YElementTransformer {
	private Element locationEle;
	private Automata automata;
	private State location;

	public U2YLocationTransformer(Model model, Element locationEle, Automata automata) {
		super(model);
		this.locationEle = locationEle;
		this.automata = automata;
	}

	private void transformLocation() {
		
		String locationName = "";
		String locationID = "";
		String locationType = "";
		String position_x ="";
		String position_y ="";

		//locationType = locationEle.getAttribute("xsi:type").trim();
		locationID = locationEle.getAttribute("id").trim();
		position_x = locationEle.getAttribute("x").trim();
		position_y = locationEle.getAttribute("y").trim();
	
		location = automata.findState(locationID);
		//new state
		if(location == null)
		{
			location = new State(locationID);
			automata.addState(location);
			 
		}
		
		//initial location
		locationName = locationEle.getTextContent();
		location.setName(locationName); 
		
		//set initial state
		automata.setInitialStateID(locationID);
}

	public void transform() {
		
		transformLocation();
		/*
		// TODO Auto-generated method stub
		transformState();
		
		TransitionTransformer transitionTransformer = new TransitionTransformer(model, stateEle, automata, state);
		transitionTransformer.transform();	
		
		transformStateAction();
		*/
		
		/*
		NodeList verticesList = automataEle.getElementsByTagName("location");
		
		System.out.print("vertical list length\n");
		System.out.print(verticesList.getLength());
		System.out.print("vertical list length done\n");
		*/
		/*	
		for(int i = 0; i < verticesList.getLength(); i++) {
			
			//transform states
			Element elem1=(Element)verticesList.item(i);
	
			System.out.println(automataName);
			System.out.print("get vertical list\n");
			System.out.print("\n");
			System.out.print(elem1.getAttribute("id"));
			System.out.print("\n");
			System.out.print(elem1.getAttribute("x"));
			System.out.print("\n");
			System.out.print(elem1.getAttribute("y"));
			System.out.print("\n");

			System.out.print("get vertical list done\n");
			
		}
	
		//NodeList stateEleList = automataEle.getElementsByTagName("name");
		
		if(stateEleList != null) {          			

			for(int i = 0; i < stateEleList.getLength(); i++) {        	

				//transform states
				stateEle = (Element)stateEleList.item(i);

				//StateTransformer stateTransformer = new StateTransformer(model, stateEle, automata);
				//stateTransformer.transform();
			}

		}
		
		*/ 
		
	}
	
}
