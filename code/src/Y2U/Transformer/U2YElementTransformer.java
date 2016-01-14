package Y2U.Transformer;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import Y2U.DataStructure.Model;

public class U2YElementTransformer {
	protected Model model;
	
	public U2YElementTransformer(Model model) {
		super();
		this.model = model;
	}
	
	public void transform(Document uppaalDoc) {
		Element rootEle = uppaalDoc.getDocumentElement();
		
		//transform variable declarations
		Element modelEle = (Element)rootEle.getElementsByTagName("declaration").item(0);
		
		String declaration = modelEle.getTextContent();
		
		if (declaration != null){
			
			U2YVariableTransformer variableTransformer = new U2YVariableTransformer(model, declaration);
			variableTransformer.u2ytransform();
		}
		
		//transform automata 
		NodeList automataEleList = rootEle.getElementsByTagName("template");

		if(automataEleList.getLength() != 0) {
			for(int i = 0; i < automataEleList.getLength(); i++) {

				Element automataEle = (Element)automataEleList.item(i);

				U2YAutomataTransformer u2yautomataTransformer = new U2YAutomataTransformer(model, automataEle);
				u2yautomataTransformer.transform();        		        		
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
