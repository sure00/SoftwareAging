package Y2U.Transformer;

import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import Y2U.DataStructure.Automata;
import Y2U.DataStructure.Regions;
import Y2U.DataStructure.Event;
import Y2U.DataStructure.Model;
import Y2U.DataStructure.State;
import Y2U.DataStructure.Timer;
import Y2U.DataStructure.Transition;


public class U2YAutomataTransformer extends U2YElementTransformer {
	
	private Element automataEle;
	private Automata automata;

	public U2YAutomataTransformer(Model model, Element automataEle) {
		super(model);
		this.automataEle = automataEle;

		automata = null;
	}
	
	public void transform(){	
		String automataName = "";
		Element stateEle = null;
		/*
		System.out.print("get region list\n");
		System.out.print(automataEle.getTextContent());
		System.out.print("get region list done\n");
		*/
		//Get the region Name
		NodeList automataEleList = automataEle.getElementsByTagName("name");
		Element elem=(Element)automataEleList.item(0);
		automataName = elem.getTextContent();
		
		// Set regions name or automate name to region list
		automata = new Automata(automataName);
		model.addAutomata(automata);
		
		NodeList locationleList = automataEle.getElementsByTagName("location");
		if(locationleList != null) { 

			for(int i = 0; i < locationleList.getLength(); i++) {   	

				//transform states
				stateEle = (Element)locationleList.item(i);

				U2YLocationTransformer u2ylocationTransformer = new U2YLocationTransformer(model, stateEle, automata);
				u2ylocationTransformer.transform();
			}
		}
		
		System.out.print("automataEle\n");
		System.out.print(automataEle.getTextContent());
		System.out.print("automataEle done\n");
		
		
		NodeList transitionleList = automataEle.getElementsByTagName("transition");
		if(transitionleList != null) { 

			for(int i = 0; i < transitionleList.getLength(); i++) {   	

				//transform states
				Element transitionEle = (Element)transitionleList.item(i);
				
				System.out.print("transition\n");
				System.out.print(transitionleList.getLength());
				System.out.print(transitionEle.getTextContent());
				System.out.print("state transition done\n");
				
				U2YTransitionTransformer transitionTransformer = new U2YTransitionTransformer(model, transitionEle, automata, i);
				transitionTransformer.transform();
				
			}
		}
		
	}
}
