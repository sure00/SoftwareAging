package Y2U.Transformer;

import org.w3c.dom.Element;

import Y2U.DataStructure.Event;
import Y2U.DataStructure.Model;

public class U2YVariableTransformer extends U2YElementTransformer {

	private String variableInfo;
	
	public U2YVariableTransformer(Model model, String variableInfo) {
		super(model);
		this.variableInfo = variableInfo;
	}
	
	public void u2ytransform() {
		String[] declarationList = variableInfo.split("\n");
		String[] tmp;
		
		int beginIndex = 0;
		int endIndex = 0;
		String varName = "";
		String varType = "";
		String interfaceName = "";

		//add interface at the head of sentece.
		model.addSpecification("&#xD;&#xA;interface:");
		
		for(int i = 0; i < declarationList.length; i++){
			String line = declarationList[i].trim();
			//Ignore the comments
			
			if(line.length() == 0 || line.charAt(0) == '*'
					|| line.charAt(0) == '<'|| line.charAt(0) == '/' )
				continue;
			
			beginIndex = 0; 
			endIndex = line.indexOf(" "); 
			varType = line.substring(beginIndex, endIndex).trim();
						
			beginIndex = endIndex + 1;
			endIndex = line.indexOf(";"); 
			varName = line.substring(beginIndex, endIndex).trim();

			
			// Detect int variable
			if(varType.contains("int"))
			{
				varType ="int";
			}
		
			tmp = varName.split(",");
			
			beginIndex = 0;
			System.out.print("sure debug\n");
			System.out.print(tmp.length);
			for(int j = 0; j < tmp.length; j++) {
					varName = tmp[j];
					switch(varType) {
					case "int": varType = "integer";
						model.addSpecification("var " + varName + ":" + varType + "#xD;&#xA;");
						break;
					case "bool": varType = "boolean";
						model.addSpecification("var " + varName + ":" + varType + "#xD;&#xA;");
						break;
					case "chan": 
						interfaceName ="";
						model.addSpecification("in event " + varName + "&#xD;&#xA;");
						break;
					case "clock":
						model.addSpecification("var " + varName + ":" + varType + "#xD;&#xA;");
						break;
						
					default: varType = ""; break;
					}
				}
			}
		
		System.out.print("after running\n");
		System.out.print(model.getSpecificationList());
		System.out.print("specification lit\n");
	}
}