package Y2U.DataStructure;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class Model {
	
	//all timers have the same timeUnit
	private Hashtable<String, Integer> timeUnitList; //enum TimeUnit { s, ms, us,ns };
	private String timeUnit;
	
	//UPPAAL data Structure
	private List<Automata> automataList;	
	private Hashtable<String, Event> eventList;
	private List<Timer> timerList;
	private List<String> declarationList;
	private List<String> sysList;
	private Hashtable<String, StringVariable> stringVariableList;
	private Hashtable<String, RealVariable> realVariableList;
	
	//Yakindu data Structure
	private List<String> specificationList;
	private List<Regions> regionsList;
	
	public Model() {
		automataList = new ArrayList<Automata> ();
		eventList = new Hashtable<String, Event>();
		timerList = new ArrayList<Timer>();
		regionsList = new ArrayList<Regions> ();
		
		declarationList = new ArrayList<String> ();
		sysList = new ArrayList<String> ();	
		stringVariableList = new Hashtable<String, StringVariable>();
		realVariableList = new Hashtable<String, RealVariable>();
		
		// Used for Yakindu datastructure
		specificationList = new ArrayList<String> ();
		
		timeUnit = "s";
		
		timeUnitList = new Hashtable<String, Integer>();
		timeUnitList.put("s", 0);
		timeUnitList.put("", 0);
		timeUnitList.put("ms", -3);
		timeUnitList.put("us", -6);
		timeUnitList.put("ns", -9);
	}
	
	public void addSpecification(String Specification) {
		specificationList.add(Specification);
	}
	
	public void addDeclaration(String declaration) {
		declarationList.add(declaration);
	}
	
	public void addAutomata(Automata automata) {
		automataList.add(automata);
		
		sysList.add(automata.getName());
	}
	
	public void addRegion(Regions region) {
		regionsList.add(region);
		
		sysList.add(region.getName());
	}
	
	public void addEvent(String eventName, Event event) {		
		eventList.put(eventName, event);
		sysList.add(event.getName());
	}
	
	public Event findEvent(String evenrName) {
		return eventList.get(evenrName);
	}
	
	//add timer and update all timers' time value based on timeUnit
	//all timers have the same timeUnit
	public void addTimer(String timerName, Timer timer) {
		String newTimeUnit = timer.getTimeUnit();
		
		if(timeUnitList.get(timeUnit) - timeUnitList.get(newTimeUnit) < 0) {
			timer.updateTimeByUnit(timeUnit, timeUnitList);
		}
			
		if(timeUnitList.get(timeUnit) - timeUnitList.get(newTimeUnit) > 0) {
			for(int i = 0; i < timerList.size(); i++) {
				timerList.get(i).updateTimeByUnit(newTimeUnit, timeUnitList);
				
				timeUnit = newTimeUnit;
			}
		}
			
		timerList.add(timer);
		
		sysList.add(timer.getName());
	}
	
	//find the every timer with the timerName (e.g. every5s)
	//every timer contains a self loop transition, all every timers with same time value can share one
	public Timer findTimer(String timerName) {
		for(Timer timer : timerList) {
			if(timer.getTimerName().equals(timerName))
				return timer;
		}

		return null;
	}
	
	//find counts of the after timer with same time value (e.g. after5s_0, after5s_1) 
	//each after timers with same time value has its own timer (NOT share)
	//the new timerName will be after5s_[count+1]
	public int findTimerCount(String timerName) {
		
		int count = 0;
		
		for(Timer timer : timerList) {
			if(timer.getTimerName().startsWith(timerName))
				count++;
		}

		return count;
	}
	
	public void addSys(String sys) {
		sysList.add(sys);
	}
	
	public void addStringVariable(String strName) {
		stringVariableList.put(strName, new StringVariable(strName));		
	}
	
	public StringVariable findStringVariable(String strName) {
		return stringVariableList.get(strName);
	}
	
	public void addRealVariable(String name) {
		realVariableList.put(name, new RealVariable(name));
	}
	
	public RealVariable findRealVariable(String name) {
		return realVariableList.get(name);
	}
	
	public List<Automata> getAutomataList() {
		return automataList;
	}
	public void setAutomataList(List<Automata> automataList) {
		this.automataList = automataList;
	}
	public Hashtable<String, Event> getEventList() {
		return eventList;
	}
	public void setEventList(Hashtable<String, Event> eventList) {
		this.eventList = eventList;
	}
	public List<Timer> getTimerList() {
		return timerList;
	}
	public void setTimerList(List<Timer> timerList) {
		this.timerList = timerList;
	}
	public List<String> getDeclarationList() {
		return declarationList;
	}
	
	public List<String> getSpecificationList() {
		return specificationList;
	}
	
	public void setDeclarationList(List<String> declarationList) {
		this.declarationList = declarationList;
	}	
	public List<String> getSysList() {
		return sysList;
	}
	public void setSysList(List<String> sysList) {
		this.sysList = sysList;
	}
	public Hashtable<String, StringVariable> getStringVariableList() {
		return stringVariableList;
	}
	public void setStringVariableList(Hashtable<String, StringVariable> stringVariableList) {
		this.stringVariableList = stringVariableList;
	}
	public Hashtable<String, RealVariable> getRealVariableList() {
		return realVariableList;
	}
	
	public void setRealVariableList(Hashtable<String, RealVariable> realVariableList) {
		this.realVariableList = realVariableList;
	}
	
}
