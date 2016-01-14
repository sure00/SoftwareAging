package Y2U.ReadWrite;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;

import Y2U.DataStructure.Automata;
import Y2U.DataStructure.Event;
import Y2U.DataStructure.Model;
import Y2U.DataStructure.RealVariable;
import Y2U.DataStructure.State;
import Y2U.DataStructure.StringVariable;
import Y2U.DataStructure.Timer;
import Y2U.DataStructure.Transition;

public class UppaalWriter {
	
	private String UppaalFilePath;
	private Document UppaalDoc;	
	private Model model;
	
	
	
	public UppaalWriter(String UppaalFilePath, Model model) {
		super();
		this.UppaalFilePath = UppaalFilePath;
		this.model = model;		
		
		UppaalDoc = null;
	}
		
	public void write()
	{   	     
		UppaalDoc = createDoc();  

		writeModel();    		

		writeDoc();
    }  
	
	
	private Document createDoc() { 
    	Document document = null; 
    	try { 
    		//DOM new Document instance 
    		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
    		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder(); 
    		//create new document 
    		document = docBuilder.newDocument(); 
    	} catch (ParserConfigurationException e) { 
    		e.printStackTrace();  
    	}
    	return document; 
    }

	
	// write the content into xml file
	private void writeDoc()
	{
		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			DOMImplementation domImpl = UppaalDoc.getImplementation();
			DocumentType doctype = domImpl.createDocumentType("doctype",
			    "-//Uppaal Team//DTD Flat System 1.1//EN",
			    "http://www.it.uu.se/research/group/darts/Uppaal/flat-1_2.dtd");
			transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, doctype.getPublicId());
			transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, doctype.getSystemId());
			
			UppaalDoc.setXmlStandalone(true);
			DOMSource source = new DOMSource(UppaalDoc);
			PrintWriter pw = new PrintWriter(UppaalFilePath, "utf-8"); 
			StreamResult result = new StreamResult(pw);
	 
			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);
	 
			transformer.transform(source, result);
	 
			System.out.println("File saved!");
		}
		catch (TransformerException tfe) {
    		tfe.printStackTrace();
    	}
    	catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    	catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}		
	}
	
	
	private void writeModel() {
		
		//root (nta) elements    		
		Element rootEle = UppaalDoc.createElement("nta");
		UppaalDoc.appendChild(rootEle);		
	
		//declaration elements
		//golbal declarations
		Element declaratonEle = UppaalDoc.createElement("declaration");
		rootEle.appendChild(declaratonEle);
		
		String declaratons = "";
		for(int i = 0; i < model.getDeclarationList().size(); i++)
		{
			declaratons = declaratons + model.getDeclarationList().get(i) + "\n";
		}		
		
		List<StringVariable> stringVariableList = new ArrayList<StringVariable>(model.getStringVariableList().values());		
		for(StringVariable stringVariable : stringVariableList) {
			declaratons = declaratons + "int " + stringVariable.getName() + "\n";
		}
		List<RealVariable> realVariableList = new ArrayList<RealVariable>(model.getRealVariableList().values());		
		for(RealVariable realVariable : realVariableList) {
			declaratons = declaratons + "int " + realVariable.getIntegerPartName() + "\n";
			declaratons = declaratons + "int " + realVariable.getFractionPartName() + "\n";
		}
		declaratonEle.appendChild(UppaalDoc.createTextNode(declaratons));
		
		
		
		//template (automata) elements
		Automata automata = null;
		for(int i = 0; i < model.getAutomataList().size(); i++)
		{    			
			automata = model.getAutomataList().get(i);    			
			writeAutomata(rootEle, automata);
		}
		
		
		//template (event) elements	
		List<Event> eventList = new ArrayList<Event>(model.getEventList().values());		
		for(Event event : eventList)
		{    						   			
			writeAutomata(rootEle, event);
		}
		
		//template (timer) elements
		for(Timer timer : model.getTimerList()) {
			writeAutomata(rootEle, timer);
		}		

		//system element
		Element systemEle = UppaalDoc.createElement("system");
		rootEle.appendChild(systemEle);
		String systemStr = "system";
		for(int i = 0; i < model.getSysList().size(); i++)
		{
			if(i==0)
				systemStr = systemStr + " " + model.getSysList().get(i);  
			else
				systemStr = systemStr + ", " + model.getSysList().get(i); 
		}
		systemStr = systemStr + ";";
		systemEle.appendChild(UppaalDoc.createTextNode(systemStr));

		//query element
		Element queryEle = UppaalDoc.createElement("queries");
		rootEle.appendChild(queryEle); 
	}   
    
    
    private void writeAutomata(Element rootEle, Automata automata)
    {		
		Element automataEle = UppaalDoc.createElement("template");
		rootEle.appendChild(automataEle);
		
		//automata basic info
		Element automataNameEle = UppaalDoc.createElement("name");   		
		automataNameEle.appendChild(UppaalDoc.createTextNode(automata.getName()));
		automataEle.appendChild(automataNameEle);
		
		
		
		//declaration elements
		//automata declarations
		Element declaratonEle = UppaalDoc.createElement("declaration");
		automataEle.appendChild(declaratonEle);

		String declaratons = "";
		for(int i = 0; i < automata.getDeclarationList().size(); i++)
		{
			declaratons = declaratons + automata.getDeclarationList().get(i) + "\n";
		}
		declaratonEle.appendChild(UppaalDoc.createTextNode(declaratons));
		
		
		
		//location (state) elements
		State state = null;
		for(int j = 0; j < automata.getStateList().size(); j++)
		{
			state = automata.getStateList().get(j);			
			writeStates(automataEle, state);
		}
		
		
		
		//initial state element
		Element initStateEle = UppaalDoc.createElement("init");
		initStateEle.setAttribute("ref", automata.getInitialStateID());
		automataEle.appendChild(initStateEle);
		
		
		
		//transition elements
		Transition transition = null;
		for(int j = 0; j < automata.getTransitionList().size(); j++)
		{
			transition = automata.getTransitionList().get(j);			
			writeTransitions(automataEle, transition);
		}
    }
    
    
    
    private void writeStates(Element automataEle, State state)
    {    				
    	//state basic info
    	Element stateEle = UppaalDoc.createElement("location");
    	stateEle.setAttribute("id", state.getId());
    	stateEle.setAttribute("x", String.valueOf(state.getX()));
    	stateEle.setAttribute("y", String.valueOf(state.getY()));
    	automataEle.appendChild(stateEle);

    	//state name
    	Element stateNameEle = UppaalDoc.createElement("name");
    	stateNameEle.setAttribute("x", String.valueOf(state.getX()));
    	stateNameEle.setAttribute("y", String.valueOf(state.getY()));
    	stateNameEle.appendChild(UppaalDoc.createTextNode(state.getName()));
    	stateEle.appendChild(stateNameEle);


    	//state invariant
    	Element invariantEle = UppaalDoc.createElement("label");
    	invariantEle.setAttribute("kind", "invariant");
    	invariantEle.setAttribute("x", String.valueOf(state.getX()));
    	invariantEle.setAttribute("y", String.valueOf(state.getY() + 10));
    	invariantEle.appendChild(UppaalDoc.createTextNode(state.getInvariant()));    	
    }
    
    
    private void writeTransitions(Element automataEle, Transition transition)
    {   				
    	//transition basic info
    	Element transitionEle = UppaalDoc.createElement("transition");        			
    	automataEle.appendChild(transitionEle);


    	//transition source
    	Element transitionSourceEle = UppaalDoc.createElement("source");
    	transitionSourceEle.setAttribute("ref", transition.getSource());    				
    	transitionEle.appendChild(transitionSourceEle);


    	//transition target
    	Element transitionTargetEle = UppaalDoc.createElement("target");
    	transitionTargetEle.setAttribute("ref", transition.getTarget());    				
    	transitionEle.appendChild(transitionTargetEle);


    	//transition guard
    	Element transitionGuardEle = UppaalDoc.createElement("label");
    	transitionGuardEle.setAttribute("kind", "guard");
    	transitionGuardEle.setAttribute("x", String.valueOf(transition.getX()));
    	transitionGuardEle.setAttribute("y", String.valueOf(transition.getY() + 10));
    	transitionGuardEle.appendChild(UppaalDoc.createTextNode(transition.getGuard()));    				
    	transitionEle.appendChild(transitionGuardEle);


    	//transition synchronisation
    	Element transitionSynEle = UppaalDoc.createElement("label");
    	transitionSynEle.setAttribute("kind", "synchronisation");
    	transitionSynEle.setAttribute("x", String.valueOf(transition.getX()));
    	transitionSynEle.setAttribute("y", String.valueOf(transition.getY() + 20));
    	transitionSynEle.appendChild(UppaalDoc.createTextNode(transition.getSynchronisation()));    				
    	transitionEle.appendChild(transitionSynEle);


    	//transition assignment
    	Element transitionAssignmentEle = UppaalDoc.createElement("label");
    	transitionAssignmentEle.setAttribute("kind", "assignment");
    	transitionAssignmentEle.setAttribute("x", String.valueOf(transition.getX()));
    	transitionAssignmentEle.setAttribute("y", String.valueOf(transition.getY() + 30));
    	transitionAssignmentEle.appendChild(UppaalDoc.createTextNode(transition.getUpdate()));    				
    	transitionEle.appendChild(transitionAssignmentEle);


    	//transition nail (positions)
    	Element transitionNailEle = UppaalDoc.createElement("nail");
    	transitionNailEle.setAttribute("x", String.valueOf(transition.getX()));
    	transitionNailEle.setAttribute("y", String.valueOf(transition.getY())); 				
    	transitionEle.appendChild(transitionNailEle);  

    	transitionNailEle = UppaalDoc.createElement("nail");
    	transitionNailEle.setAttribute("x", String.valueOf(transition.getX() + 50));
    	transitionNailEle.setAttribute("y", String.valueOf(transition.getY() + 30)); 				
    	transitionEle.appendChild(transitionNailEle);    			
    }

	public String getUppaalFilePath() {
		return UppaalFilePath;
	}
	public void setUppaalFilePath(String UppaalFilePath) {
		this.UppaalFilePath = UppaalFilePath;
	}
	public Document getUppaalDoc() {
		return UppaalDoc;
	}
	public void setUppaalDoc(Document UppaalDoc) {
		this.UppaalDoc = UppaalDoc;
	}
	public Model getModel() {
		return model;
	}
	public void setModel(Model model) {
		this.model = model;
	}
}
