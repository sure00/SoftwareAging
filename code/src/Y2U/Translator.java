package Y2U;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import Y2U.DataStructure.Model;
import Y2U.ReadWrite.UppaalWriter;
import Y2U.ReadWrite.YakinduReader;
import Y2U.ReadWrite.YakinduWriter;
import Y2U.ReadWrite.UppaalReader;
import Y2U.Transformer.ElementTransformer;
import Y2U.Transformer.U2YElementTransformer;

public class Translator
{
	
	private String yakinduFilePath;	
	private String UppaalFilePath;
	private Model model;
	private YakinduReader Yakindureader;
	private UppaalWriter Uppaalwriter;
	private YakinduWriter Yakinduwriter;
	private UppaalReader Uppaalreader;
	private ElementTransformer elementTransformer;
	private U2YElementTransformer UppaalelementTransformer;
	private Layout layout;
	
	
	public Translator(String inputFilePath, String outFilePath, int mode) {
		super();
		if (mode ==1){
			this.yakinduFilePath = inputFilePath;
			this.UppaalFilePath = outFilePath;
		}
		else {
			this.yakinduFilePath = outFilePath;
			this.UppaalFilePath = inputFilePath;
		}
		model = new Model();
		Yakindureader = new YakinduReader(yakinduFilePath, model);
		Uppaalwriter = new UppaalWriter(UppaalFilePath, model);
		Yakinduwriter = new YakinduWriter(yakinduFilePath, model);
		Uppaalreader = new UppaalReader(UppaalFilePath, model);
		
		elementTransformer = new ElementTransformer(model);
		UppaalelementTransformer = new U2YElementTransformer(model);
		layout = new Layout(model);
	}

	public void Y2Utranslate() {
		
		Document yakinduDoc = Yakindureader.read();

		elementTransformer.transform(yakinduDoc);
		
		layout.setLocations();
		
		Uppaalwriter.write();
	}	
	
	public void U2Ytranslate() {
		
		Document UppaalDoc = Uppaalreader.read();

		UppaalelementTransformer.transform(UppaalDoc);

		layout.setLocations();

		Yakinduwriter.write();
	}	
}