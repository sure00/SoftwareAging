package Y2U.DataStructure;

import java.util.ArrayList;

public class Event extends Automata {

	private String eventName; //chan eventName
	
	public Event(String name) {
		super(name + "Event");
		
		eventName = name;
		
		State state = new State("init_" + this.name, "");	
		state.setX(0);
		state.setY(0);
		addState(state);
		
		Transition  transition = new Transition("tran_" + name, "init_" + name, "init_" + name);
		transition.setSynchronisation(name + "!");
		transition.setX(100);
		transition.setY(100);
		addTransition(transition);
		
		initialStateID = "init_" + name;
	}
	
	

	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	
	
}
