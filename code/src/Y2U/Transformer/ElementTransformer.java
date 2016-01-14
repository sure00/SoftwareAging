package Y2U.Transformer;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import Y2U.DataStructure.Model;

public class ElementTransformer
{		
	protected Model model;
	
	
	public ElementTransformer(Model model) {
		super();
		this.model = model;
	}
	
	public void transform(Document yakinduDoc) {
		Element rootEle = yakinduDoc.getDocumentElement();

		//transform variable declarations
		Element modelEle = (Element)rootEle.getElementsByTagName("sgraph:Statechart").item(0);
		if (modelEle != null) {
			String declaration = modelEle.getAttribute("specification");

			VariableTransformer variableTransformer = new VariableTransformer(model, declaration);
			variableTransformer.transform();			
		}

		//transform automata 
		NodeList automataEleList = rootEle.getElementsByTagName("regions");
		if(automataEleList != null) {       	     	

			for(int i = 0; i < automataEleList.getLength(); i++) {

				Element automataEle = (Element)automataEleList.item(i);

				AutomataTransformer automataTransformer = new AutomataTransformer(model, automataEle);
				automataTransformer.transform();        		        		
			}  
		}
	}

	public Model getModel() {
		return model;
	}
	public void setModel(Model model) {
		this.model = model;
	}	
}