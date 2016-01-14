package Y2U.Transformer;

import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import Y2U.DataStructure.Automata;
import Y2U.DataStructure.Model;
import Y2U.DataStructure.State;
import Y2U.DataStructure.Transition;

public class U2YTransitionTransformer extends U2YElementTransformer{
	private Element transitionEle;
	private Automata automata;
	private int ID;

	public U2YTransitionTransformer(Model model, Element transitionEle, Automata automata, int ID) {
		super(model);
		this.transitionEle = transitionEle;
		this.automata = automata;
		this.ID = ID;
	}	
	
	public void transform() {	
		String transitionID = "TRANSITION" + String.valueOf(ID);
		Transition transition = null;	
		
		//transitionID = transitionEle.getAttribute("xmi:id").trim();

		//transition = automata.findTransition(transitionID);
		
		transition = new Transition();
		automata.addTransition(transition);	
		
		transition.setId(transitionID);
		
		//state in going transitions
		NodeList ingoingTransitionEleList = transitionEle.getElementsByTagName("source");
		Element sourceElet = (Element)ingoingTransitionEleList.item(0);
		String src_ref = sourceElet.getAttribute("ref").trim();
		transition.setSource(src_ref.trim());
		
		//state out going transitions
		NodeList outgoingTransitionEleList = transitionEle.getElementsByTagName("target");
		Element targetElet = (Element)outgoingTransitionEleList.item(0);
		String des_ref = targetElet.getAttribute("ref").trim();
		transition.setTarget(des_ref.trim());
		
		//kind transition 
		NodeList labelTransitionEleList = transitionEle.getElementsByTagName("label");
		for(int i = 0; i < labelTransitionEleList.getLength(); i++) {        	
			Element labelElet = (Element)labelTransitionEleList.item(i);

			//StateTransformer stateTransformer = new StateTransformer(model, stateEle, automata);
			//stateTransformer.transform();

			String kind = labelElet.getAttribute("kind").trim();
			String content  = labelElet.getTextContent();
						
			if(kind == "synchronisation")
			{
				transition.setSynchronisation(content);
			}
			if (kind == "guard")
			{
				transition.setGuard(content);				
			}
			if (kind == "assignment")
			{
				transition.setassignment(content);				
			}
			if (kind == "invariant")
			{
				transition.setassignment(content);				
			}
			//transition.setTarget(des_ref.trim());		
			System.out.print("transition\n");
			System.out.print(src_ref);
			System.out.print("\n");
			System.out.print(des_ref);
			System.out.print("\n");
			System.out.print(kind);
			System.out.print("\n");
			//System.out.print(id);
			System.out.print("transition end\n");
		}		
	}
}
