package com.honey.XMLTest;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Dom²Ù×÷xml
 * @author Honey
 * @Date 
 *
 */
public class DOMTest {
	public static void main(String[] args) throws Exception
	{
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder=factory.newDocumentBuilder();
			Document document=builder.parse("src/com/honey/XMLTest/test.xml");
			NodeList list=document.getElementsByTagName("student");
			element(list);
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public static void element(NodeList list)
	{
		for(int i=0;i<list.getLength();i++)
		{
			Element element=(Element) list.item(i);
			NodeList childNodes=element.getChildNodes();
			for(int j=0;j<childNodes.getLength();j++)
			{
				if(childNodes.item(j).getNodeType()==Node.ELEMENT_NODE)
				{
					System.out.print(childNodes.item(j).getNodeName()+":");
					System.out.println(childNodes.item(j).getFirstChild().getNodeValue());
				}
			}
		}
	}

}
