package Y2U.Transformer;


import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import Y2U.DataStructure.Automata;
import Y2U.DataStructure.Event;
import Y2U.DataStructure.Model;
import Y2U.DataStructure.State;
import Y2U.DataStructure.Transition;

public class TransitionTransformer extends ElementTransformer {

	private Element stateEle;
	private Automata automata;
	private State state;

	public TransitionTransformer(Model model, Element stateEle, Automata automata, State state) {
		super(model);
		this.stateEle = stateEle;
		this.automata = automata;
		this.state = state;
	}	

	public void transform() {	

		//state incoming transitions
		String incomingTransitions = stateEle.getAttribute("incomingTransitions").trim();
		if(! incomingTransitions.equals(""))
		{
			String[] incomingTransitionList = incomingTransitions.split(" ");

			for(int i = 0; i < incomingTransitionList.length; i++)
			{
				transformIncomingTransition(incomingTransitionList[i]);
			}
		}

		//state outgoing transitions
		NodeList outgoingTransitionEleList = stateEle.getElementsByTagName("outgoingTransitions");
		if(outgoingTransitionEleList != null)
		{
			for(int i = 0; i < outgoingTransitionEleList.getLength(); i++)
			{			
				transformOutgoingTransition((Element)outgoingTransitionEleList.item(i));
			}
		}
	}

	private void transformIncomingTransition(String incomingTransitionID) {

		Transition transition = null;

		transition = automata.findTransition(incomingTransitionID);
		if(transition == null)
		{
			transition = new Transition(incomingTransitionID);
			automata.addTransition(transition);
		}

		transition.setTarget(state.getId());

	}

	private void transformOutgoingTransition(Element outgoingTransitionEle) {

		String transitionID = "";
		Transition transition = null;		

		transitionID = outgoingTransitionEle.getAttribute("xmi:id").trim();

		transition = automata.findTransition(transitionID);
		if(transition == null)
		{
			transition = new Transition(transitionID);
			automata.addTransition(transition);
		}
		transition.setSource(state.getId());
		transition.setTarget(outgoingTransitionEle.getAttribute("target").trim());
		
		transformTransitionAction(outgoingTransitionEle, transition);
	}


	//outgoing transition specification
	private void transformTransitionAction(Element outgoingTransitionEle, Transition transition) {

		String outgoingTransitionSpecification = outgoingTransitionEle.getAttribute("specification").trim();
		if(! outgoingTransitionSpecification.equals(""))
		{
			outgoingTransitionSpecification = outgoingTransitionSpecification.replace("[", "");
			outgoingTransitionSpecification = outgoingTransitionSpecification.replace("]", "");
			outgoingTransitionSpecification = outgoingTransitionSpecification.replace("/", ""); //		?
			outgoingTransitionSpecification = outgoingTransitionSpecification.replace('.', '_');
			outgoingTransitionSpecification = outgoingTransitionSpecification.replace("always", "");
			outgoingTransitionSpecification.trim();


			//events            						
			String eventName = "";
			Event event =  model.getEventList().get(outgoingTransitionSpecification);
			if(event != null) {
				transition.setSynchronisation(outgoingTransitionSpecification + "?");

				outgoingTransitionSpecification = outgoingTransitionSpecification.replace(eventName, "").trim();
			}
			/*
			for(int l = 0; l < model.getEventList().size(); l++)
			{
				eventName = model.getEventList().get(l).getName();
				eventName = eventName.substring(0, eventName.length() - 5);
				if(outgoingTransitionSpecification.contains(eventName))
				{
					
				}
			}  
			*/          						



			//timer




			//general specifications            						
			transition.setGuard(outgoingTransitionSpecification);
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
