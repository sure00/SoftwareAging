package Y2U.Transformer;

import java.util.List;

import org.w3c.dom.Element;

import Y2U.DataStructure.Automata;
import Y2U.DataStructure.Model;
import Y2U.DataStructure.State;
import Y2U.DataStructure.Transition;

public class StateTransformer extends ElementTransformer {

	private Element stateEle;
	private Automata automata;
	private State state;

	public StateTransformer(Model model, Element stateEle, Automata automata) {
		super(model);
		this.stateEle = stateEle;
		this.automata = automata;
	}

	public void transform() {
		
		transformState();
		
		TransitionTransformer transitionTransformer = new TransitionTransformer(model, stateEle, automata, state);
		transitionTransformer.transform();	
		
		transformStateAction();
	}


	private void transformState() {
		
		String stateName = "";
		String stateID = "";
		String stateType = "";

		stateType = stateEle.getAttribute("xsi:type").trim();
		stateID = stateEle.getAttribute("xmi:id").trim();

		state = automata.findState(stateID);
		//new state
		if(state == null)
		{
			state = new State(stateID);
			automata.addState(state);
		}

		//initial state
		if(stateType.equals("sgraph:Entry"))
		{
			stateName = "init_" + automata.getName();
			stateName = stateName.replace(' ', '_');
			stateName = stateName.replace('.', '_');
			stateName = stateName.replace('-', '_');
			state.setName(stateName);        					

			//set initial state
			automata.setInitialStateID(stateID);
		}
		//simple state
		else if(stateType.equals("sgraph:State"))
		{
			stateName = stateEle.getAttribute("name").trim() + "_" + automata.getName();
			stateName = stateName.replace(' ', '_');
			stateName = stateName.replace('.', '_');
			stateName = stateName.replace('-', '_');
			state.setName(stateName);

		}
	}


	//state specification
	private void transformStateAction() {

		Transition transition = null;

		String stateSpecification = stateEle.getAttribute("specification").trim();
		if(! stateSpecification.equals(""))
		{
			String[] stateSpecificationList = stateSpecification.split("\n");

			String entryString = "";
			String exitString = "";     
			boolean entry = false;
			boolean exit = false;
			for(int k = 0; k < stateSpecificationList.length; k++)
			{

				if(stateSpecificationList[k].contains("entry"))
				{
					entry = true;
					exit = false;
				}
				else if(stateSpecificationList[k].contains("exit"))
				{
					entry = false;
					exit = true;
				}
				else
				{
					if(entry)
						entryString = entryString + stateSpecificationList[k].trim();

					if(exit)
						exitString = exitString + stateSpecificationList[k].trim();
				}           		        	
			}

			entryString = entryString.replace("=", ":=");
			entryString = entryString.replace(';', ',');
			entryString = entryString.replace('.', '_');
			exitString = exitString.replace("=", ":=");
			exitString = exitString.replace(';', ',');
			exitString = exitString.replace('.', '_');


			if(entryString.length() > 0)
			{
				List<Transition> inTransitionList = automata.findIncomingTransition(state.getId());

				for(int l = 0; l < inTransitionList.size(); l++)
				{
					transition = inTransitionList.get(l);

					if(transition.getUpdate().length() == 0)
						transition.setUpdate(entryString);
					else
						transition.setUpdate(transition.getUpdate() + ", " + entryString);
				}
			}

			if(exitString.length() > 0)
			{
				List<Transition> outTransitionList = automata.findOutgoingTransition(state.getId());

				for(int l = 0; l < outTransitionList.size(); l++)
				{
					transition = outTransitionList.get(l);

					if(transition.getUpdate().length() == 0)
						transition.setUpdate(exitString);
					else
						transition.setUpdate(exitString + ", " + transition.getUpdate());
				}
			}
		}
	}


	public Element getStateEle() {
		return stateEle;
	}
	public void setStateEle(Element stateEle) {
		this.stateEle = stateEle;
	}
	public Automata getAutomata() {
		return automata;
	}
	public void setAutomata(Automata automata) {
		this.automata = automata;
	}
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
}
