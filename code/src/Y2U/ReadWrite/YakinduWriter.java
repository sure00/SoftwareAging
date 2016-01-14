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
import java.io.File; 

public class YakinduWriter {
	
	private String uppaalFilePath;
	private Document uppaalDoc;	
	private Model model;
	
	
	
	public YakinduWriter(String uppaalFilePath, Model model) {
		super();
		this.uppaalFilePath = uppaalFilePath;
		this.model = model;		
		
		uppaalDoc = null;
	}
	
	
	
	public void write()
	{   	     
		uppaalDoc = createDoc();  
		
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
/*
			DOMImplementation domImpl = uppaalDoc.getImplementation();
			DocumentType doctype = domImpl.createDocumentType("doctype",
			    "-//Uppaal Team//DTD Flat System 1.1//EN",
			    "http://www.it.uu.se/research/group/darts/uppaal/flat-1_2.dtd");
			transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, doctype.getPublicId());
			transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, doctype.getSystemId());
*/		
			uppaalDoc.setXmlStandalone(true);
			DOMSource source = new DOMSource(uppaalDoc);
			PrintWriter pw = new PrintWriter(uppaalFilePath, "utf-8"); 
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
		
	    Element XMI = uppaalDoc.createElement("xmi:XMI");
	    
	    XMI.setAttribute("xmlns:sgraph", "http://www.yakindu.org/sct/sgraph/2.0.0");
	    XMI.setAttribute("xmlns:notation", "http://www.eclipse.org/gmf/runtime/1.0.2/notation"); 
	    XMI.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
	    XMI.setAttribute("xmlns:xmi", "http://www.omg.org/XMI");
	    XMI.setAttribute("xmi:version", "2.0");
	    
	    uppaalDoc.appendChild(XMI);	    
	
		//declaration elements
		//sgraph:Statechart declarations
		Element declaratonEle = uppaalDoc.createElement("sgraph:Statechart");
		declaratonEle.setAttribute("xmi:id", "0");
		declaratonEle.setAttribute("specification", "");
		declaratonEle.setAttribute("name", "declaration elements");
		XMI.appendChild(declaratonEle);

		/*	
		String declaratons = "";
		for(int i = 0; i < model.getDeclarationList().size(); i++)
		{
			declaratons = declaratons + model.getDeclarationList().get(i) + "\n";
		}
*/		
		//region (automata) elements
		Automata region = null;
		for(int i = 0; i < model.getAutomataList().size(); i++)
		{    			
			region = model.getAutomataList().get(i);
			
			String name = region.getName();
			String id = region.getinitialStateID();
			System.out.print("#########################\n");;
			System.out.print(name);
			System.out.print(id);
			writeAutomata(declaratonEle, region);
		}
		
		/*		
		List<StringVariable> stringVariableList = new ArrayList<StringVariable>(model.getStringVariableList().values());		
		for(StringVariable stringVariable : stringVariableList) {
			declaratons = declaratons + "int " + stringVariable.getName() + "\n";
		}
		List<RealVariable> realVariableList = new ArrayList<RealVariable>(model.getRealVariableList().values());		
		for(RealVariable realVariable : realVariableList) {
			declaratons = declaratons + "int " + realVariable.getIntegerPartName() + "\n";
			declaratons = declaratons + "int " + realVariable.getFractionPartName() + "\n";
		}
		declaratonEle.appendChild(uppaalDoc.createTextNode(declaratons));
		
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
		Element systemEle = uppaalDoc.createElement("system");
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
		systemEle.appendChild(uppaalDoc.createTextNode(systemStr));
		
		
		//query element
		Element queryEle = uppaalDoc.createElement("queries");
		rootEle.appendChild(queryEle); 
		*/
	}   
    
    
    private void writeAutomata(Element rootEle, Automata automata)
    {		
		Element automataEle = uppaalDoc.createElement("regions");
		
		automataEle.setAttribute("xmi:id", "???");
		automataEle.setAttribute("name", automata.getName());
		String ID;
		
		rootEle.appendChild(automataEle);
		
		//location (state) elements
		State state = null;
		for(int j = 0; j < automata.getStateList().size(); j++)
		{
			state = automata.getStateList().get(j);		
			System.out.print("get node id is\n");
			String locationID = state.getId();
			System.out.print(state.getId());			
			System.out.print("\n");
			System.out.print(state.getName());			
			System.out.print("\n");		
						
			if (j == 0)
				addEntryTransition(automataEle ,automata);
			
			List<Transition> inTransitionList = automata.findIncomingTransition(state.getId());
			
			String sIncoming = "";
			
			for(int l = 0; l < inTransitionList.size(); l++)
			{
				System.out.print("wwwwwwwwwwwwwwww\n");
				System.out.print(locationID);
				System.out.print("\n");
				System.out.print(inTransitionList.get(l).getId());
				System.out.print("\n");
				
				if (sIncoming.matches(inTransitionList.get(l).getId())) {
				//if (sIncoming.contains(inTransitionList.get(l).getId())) {
						continue;
				}
				
				sIncoming +=sIncoming + ' ' + inTransitionList.get(l).getId();
			}
			
	    	Element transitionEle = uppaalDoc.createElement("vertices");
	    	transitionEle.setAttribute("xsi:type", "sgraph:State");
	    	transitionEle.setAttribute("xmi:id", state.getId());
	    	transitionEle.setAttribute("name", state.getName());
	    	
	    	transitionEle.setAttribute("incomingTransitions",sIncoming);
	    	//transitionEle.setAttribute("outgoingTransitions",transition.getTarget());
	    	
	    	
			List<Transition> outTransitionList = automata.findOutgoingTransition(state.getId());
			
			String sOutcoming = "";	    	
			Element outgoingTransitionsEle = uppaalDoc.createElement("outgoingTransitions");
	    	
			for(int l = 0; l < outTransitionList.size(); l++)
			{				
				//System.out.print("SSSSSSSSSSSSSSS\n");
				//System.out.print(outTransitionList.get(l).getId());
				//System.out.print("\n");
				
				outgoingTransitionsEle.setAttribute("xmi:id", outTransitionList.get(l).getId());
				
				String specification = "";	
				
				if ((outTransitionList.get(l).getGuard()) == "" )
				{
					specification = specification + outTransitionList.get(l).getGuardid() + ';';
				}
				if ((outTransitionList.get(l).getSynchronisation()) == "" )
				{
					specification = specification + outTransitionList.get(l).getSynchronisation() + ';';
				}
				if ((outTransitionList.get(l).getassignment()) == "" )
				{
					specification = specification + outTransitionList.get(l).getassignment();
				}
						
				outgoingTransitionsEle.setAttribute("specification", specification);
				outgoingTransitionsEle.setAttribute("target",outTransitionList.get(l).getTarget() );
				transitionEle.appendChild(outgoingTransitionsEle);
				automataEle.appendChild(transitionEle);
				
			}		
			
		}	
    }
    

    private void addEntryTransition(Element automataEle, Automata automata) {
    
    	Element transitionEle = uppaalDoc.createElement("vertices");
    	Element outgoingTransitions = uppaalDoc.createElement("outgoingTransitions");
    	transitionEle.setAttribute("xsi:type", "sgraph:Entry");
    	transitionEle.setAttribute("xmi:id","ENTRY");
    	
    	
    	State state = automata.getStateList().get(1);
    	String FirstStateID = state.getId();
    	
    	outgoingTransitions.setAttribute("xmi:id","TRANSATION1");    
    	outgoingTransitions.setAttribute("target",FirstStateID); 
    	
    	automataEle.appendChild(transitionEle);
    	transitionEle.appendChild(outgoingTransitions);
    	
	}

    private void writeTransitions(Element automataEle, Transition transition, State state)
    {   				
    	//transition basic info
  
    	Element transitionEle = uppaalDoc.createElement("vertices");
    	transitionEle.setAttribute("id", transition.getId());
    	transitionEle.setAttribute("incomingTransitions",transition.getSource());
    	transitionEle.setAttribute("outgoingTransitions",transition.getTarget());
    	
    	System.out.print("33333333333333333333\n");
    	System.out.print(transition.getId());
    	System.out.print("\n");
    	//System.out.print(transition.());
    	System.out.print("\n");
    	System.out.print(transition.getSource());
    	System.out.print("\n");
    	System.out.print(transition.getTarget());
    	System.out.print("\n");
    	automataEle.appendChild(transitionEle);

    }
    
    
   /* 
    private void writeStates(Element automataEle, State state)
    {    				
    	//state basic info
    	
	     // <vertices xsi:type="sgraph:Entry" xmi:id="_NPjBjRA-EeGQ0KsLDV4EFg">
  //<outgoingTransitions xmi:id="_NPsyhxA-EeGQ0KsLDV4EFg" target="_NPjBmBA-EeGQ0KsLDV4EFg"/>
//</vertices>
    	
    	
    	Element stateEle = uppaalDoc.createElement("vertices");
    	stateEle.setAttribute("id", state.getId());
    	stateEle.setAttribute("incomingTransitions", String.valueOf(state.getSource()));
    	stateEle.setAttribute("outgoingTransitions", String.valueOf(state.getTarget()));
    	automataEle.appendChild(stateEle);

    	//state name
    	Element stateNameEle = uppaalDoc.createElement("name");
    	stateNameEle.setAttribute("x", String.valueOf(state.getX()));
    	stateNameEle.setAttribute("y", String.valueOf(state.getY()));
    	stateNameEle.appendChild(uppaalDoc.createTextNode(state.getName()));
    	stateEle.appendChild(stateNameEle);


    	//state invariant
    	Element invariantEle = uppaalDoc.createElement("label");
    	invariantEle.setAttribute("kind", "invariant");
    	invariantEle.setAttribute("x", String.valueOf(state.getX()));
    	invariantEle.setAttribute("y", String.valueOf(state.getY() + 10));
    	invariantEle.appendChild(uppaalDoc.createTextNode(state.getInvariant()));    	
    }

    
    private void writeTransitions(Element automataEle, Transition transition, String locationID)
    {   				
    	//transition basic info
  
    	Element transitionEle = uppaalDoc.createElement("vertices");
    	transitionEle.setAttribute("id", transition.getId());
    	transitionEle.setAttribute("incomingTransitions",transition.getSource());
    	transitionEle.setAttribute("outgoingTransitions",transition.getTarget());
    	
    	System.out.print("33333333333333333333\n");
    	System.out.print(transition.getId());
    	System.out.print("\n");
    	//System.out.print(transition.());
    	System.out.print("\n");
    	System.out.print(transition.getSource());
    	System.out.print("\n");
    	System.out.print(transition.getTarget());
    	System.out.print("\n");
    	automataEle.appendChild(transitionEle);

/*
    	//transition source
    	Element transitionSourceEle = uppaalDoc.createElement("source");
    	transitionSourceEle.setAttribute("ref", transition.getSource());    				
    	transitionEle.appendChild(transitionSourceEle);


    	//transition target
    	Element transitionTargetEle = uppaalDoc.createElement("target");
    	transitionTargetEle.setAttribute("ref", transition.getTarget());    				
    	transitionEle.appendChild(transitionTargetEle);


    	//transition guard
    	Element transitionGuardEle = uppaalDoc.createElement("label");
    	transitionGuardEle.setAttribute("kind", "guard");
    	transitionGuardEle.setAttribute("x", String.valueOf(transition.getX()));
    	transitionGuardEle.setAttribute("y", String.valueOf(transition.getY() + 10));
    	transitionGuardEle.appendChild(uppaalDoc.createTextNode(transition.getGuard()));    				
    	transitionEle.appendChild(transitionGuardEle);


    	//transition synchronisation
    	Element transitionSynEle = uppaalDoc.createElement("label");
    	transitionSynEle.setAttribute("kind", "synchronisation");
    	transitionSynEle.setAttribute("x", String.valueOf(transition.getX()));
    	transitionSynEle.setAttribute("y", String.valueOf(transition.getY() + 20));
    	transitionSynEle.appendChild(uppaalDoc.createTextNode(transition.getSynchronisation()));    				
    	transitionEle.appendChild(transitionSynEle);


    	//transition assignment
    	Element transitionAssignmentEle = uppaalDoc.createElement("label");
    	transitionAssignmentEle.setAttribute("kind", "assignment");
    	transitionAssignmentEle.setAttribute("x", String.valueOf(transition.getX()));
    	transitionAssignmentEle.setAttribute("y", String.valueOf(transition.getY() + 30));
    	transitionAssignmentEle.appendChild(uppaalDoc.createTextNode(transition.getUpdate()));    				
    	transitionEle.appendChild(transitionAssignmentEle);


    	//transition nail (positions)
    	Element transitionNailEle = uppaalDoc.createElement("nail");
    	transitionNailEle.setAttribute("x", String.valueOf(transition.getX()));
    	transitionNailEle.setAttribute("y", String.valueOf(transition.getY())); 				
    	transitionEle.appendChild(transitionNailEle);  

    	transitionNailEle = uppaalDoc.createElement("nail");
    	transitionNailEle.setAttribute("x", String.valueOf(transition.getX() + 50));
    	transitionNailEle.setAttribute("y", String.valueOf(transition.getY() + 30)); 				
    	transitionEle.appendChild(transitionNailEle);    			

    }
    	*/    

	public String getUppaalFilePath() {
		return uppaalFilePath;
	}
	public void setUppaalFilePath(String uppaalFilePath) {
		this.uppaalFilePath = uppaalFilePath;
	}
	public Document getUppaalDoc() {
		return uppaalDoc;
	}
	public void setUppaalDoc(Document uppaalDoc) {
		this.uppaalDoc = uppaalDoc;
	}
	public Model getModel() {
		return model;
	}
	public void setModel(Model model) {
		this.model = model;
	}
}
