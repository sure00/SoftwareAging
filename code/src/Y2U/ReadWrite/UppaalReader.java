package Y2U.ReadWrite;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import Y2U.DataStructure.Model;

public class UppaalReader {

	private String UppaalFilePath;	
	private Document UppaalDoc;
	private Object errorHandler;	
	
	public UppaalReader(String UppaalFilePath, Model model) {
		super();
		this.UppaalFilePath = UppaalFilePath;
		
		UppaalDoc = null;
	}
	
	public Document read()
    {   	     
		UppaalDoc = parse();

		return UppaalDoc;
    }
	//Load and parse XML file into DOM 
    private Document parse() { 
    	Document document = null; 
    	try { 
    		//DOM parser instance 
    		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
    		DocumentBuilder builder = docBuilderFactory.newDocumentBuilder();
    		
    		if (this.errorHandler != null){
    			builder.setErrorHandler((ErrorHandler) this.errorHandler);
    		}
    		else {
    			builder.setErrorHandler(new DefaultHandler());
    		}
    		
    		//parse an XML file into a DOM tree 
    		File file = new File(UppaalFilePath);
    		document = builder.parse(file);
    		//document = docBuilder.parse(file); 
    	} catch (ParserConfigurationException e) { 
    		e.printStackTrace();  
    	} catch (SAXException e) { 
    		e.printStackTrace(); 
    	} catch (IOException e) { 
    		e.printStackTrace(); 
    	} 
    	return document; 
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
	
}
