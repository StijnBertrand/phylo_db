import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;



import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.util.*; 





public class DBM {
	private String path = "/home/stijn/workspace/PhyloDBManager/phylomons/creatures.xml";
	private ArrayList<Phylomon> phylomons = new ArrayList<Phylomon>();
	
	public DBM(){
		try 
		{
			File file = new File(path);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);
			
			doc.getDocumentElement().normalize();
			
			NodeList nodes = doc.getElementsByTagName("phylomon");	 
			//nu enkel nog de lijst van phylomon initialiseren
			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					Phylomon current = new Phylomon(element);
					phylomons.add(current);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
		
	public void save(){
		Phylomon current;
		Iterator<Phylomon> itt;
		try {		 
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.newDocument();
			
			// root element
			Element rootElement = doc.createElement("resources");
			doc.appendChild(rootElement);
			
			for(itt = phylomons.iterator(); itt.hasNext(); ){
				current =itt.next();
				Element phylomon = current.toElement(doc,rootElement);
				//de phylomon toevoegen aan de root
				rootElement.appendChild(phylomon);
			}

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(path));
	 
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(source, result);
	 
		  } catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		  } catch (TransformerException tfe) {
			tfe.printStackTrace();
		  }
	}
		
	public void addPhylomon(Phylomon phylomon){
		phylomons.add(phylomon);	
	}		
	
	public Phylomon getPhylomon(int index)throws nonExistingPhylomonException{
		if(index<phylomons.size()){
			return phylomons.get(index);
		}else{
			throw new nonExistingPhylomonException();
		}
	}
	
	public ListIterator<Phylomon> getPhylomonIterator(int index){
		return phylomons.listIterator(index);
	}
	
	public ListIterator<Phylomon> getPhylomonIterator(){
		return phylomons.listIterator();
	}

	
	
	public static String getValue(String tag, Element element) {
		NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();
		Node node = (Node) nodes.item(0);
		return node.getNodeValue();
	}
	
	private class nonExistingPhylomonException extends Exception{
		public nonExistingPhylomonException() {  
		    super();
		  }
	}

}	

