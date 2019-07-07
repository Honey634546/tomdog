package com.honey.XMLTest;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.honey.servlet.Entity;
import com.honey.servlet.Mapping;

/**
 * Dom操作xml
 * @author Honey
 * @Date 
 *
 */
public class DOMTest2 {
	private static Entity entity=new Entity();
	private static Mapping mapping=new Mapping();
	
	private static List<Entity> entitys=new ArrayList<Entity>();
	private static List<Mapping> mappings=new ArrayList<Mapping>();
	private static List<String> welFileList=new ArrayList<String>();
	
	public static void main(String[] args) throws Exception
	{
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder=factory.newDocumentBuilder();
			Document document=builder.parse("src/com/honey/XMLTest/web.xml");
			
//			NodeList fileList=document.getElementsByTagName("welcome-file-list");
//			getFileList(fileList);
//			System.out.println(welFileList);
			
//			NodeList entitylist=document.getElementsByTagName("servlet");
//			for(int i=0;i<entitys.size();i++)
//			{
//				System.out.println(entitys.get(i).getName());
//				System.out.println(entitys.get(i).getClz());
//			}
			
			NodeList mappingList=document.getElementsByTagName("servlet-mapping");
			getServletMapping(mappingList);
			for(int i=0;i<mappings.size();i++)
			{
				System.out.println(mappings.get(i).getName());
				System.out.println(mappings.get(i).getPatterns());
			}
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 找到welcome-file-list
	<welcome-file-list>
	    <welcome-file>index.html</welcome-file>
	    <welcome-file>index.htm</welcome-file>
	    <welcome-file>index.jsp</welcome-file>
	    <welcome-file>default.html</welcome-file>
	    <welcome-file>default.htm</welcome-file>
	    <welcome-file>default.jsp</welcome-file>
  	</welcome-file-list>
	 * @param list
	 */
	public static void getFileList(NodeList list)throws Exception
	{
		for(int i=0;i<list.getLength();i++)
		{
			Element element=(Element) list.item(i);
			NodeList childNodes=element.getChildNodes();
			for(int j=0;j<childNodes.getLength();j++)
			{
				if(childNodes.item(j).getNodeType()==Node.ELEMENT_NODE)
				{
					if(childNodes.item(j).getNodeName().equals("welcome-file"))
					{
						welFileList.add(childNodes.item(j).getFirstChild().getNodeValue());
					}
				}
			}
		}
	}
	
	
	/**
	 * 找到servlet
	<servlet>
    	<description></description>
    	<display-name>PetServlet</display-name>
    	<servlet-name>PetServlet</servlet-name>
    	<servlet-class>com.honey.server.PetServlet</servlet-class>
    </servlet>
	 * @param list
	 */
	public static void getServlet(NodeList list)
	{
		for(int i=0;i<list.getLength();i++)
		{
			Element element=(Element) list.item(i);
			NodeList childNodes=element.getChildNodes();
			for(int j=0;j<childNodes.getLength();j++)
			{
				if(childNodes.item(j).getNodeType()==Node.ELEMENT_NODE)
				{
					if(childNodes.item(j).getNodeName().equals("servlet-name"))
					{
						entity.setName(childNodes.item(j).getFirstChild().getNodeValue());
					}
					if(childNodes.item(j).getNodeName().equals("servlet-class"))
					{
						entity.setClz(childNodes.item(j).getFirstChild().getNodeValue());
						entitys.add(entity);
						entity=new Entity();
					}
				}
			}
		}
	}
	
	/**
	 * 找到ervlet-mapping
	<servlet-mapping>
    	<servlet-name>Login</servlet-name>
    	<url-pattern>/Login</url-pattern>
  	</servlet-mapping>
	 * @param list
	 */
	public static void getServletMapping(NodeList list)
	{
		for(int i=0;i<list.getLength();i++)
		{
			Element element=(Element) list.item(i);
			NodeList childNodes=element.getChildNodes();
			for(int j=0;j<childNodes.getLength();j++)
			{
				if(childNodes.item(j).getNodeType()==Node.ELEMENT_NODE)
				{
					if(childNodes.item(j).getNodeName().equals("servlet-name"))
					{
						//由于url-pattern存在多个，则在扫描到servlet-name时添加，
						//这样导致第一个mapping是null，在最后删除
						mappings.add(mapping);
						mapping=new Mapping();
						mapping.setName(childNodes.item(j).getFirstChild().getNodeValue());
					}
					if(childNodes.item(j).getNodeName().equals("url-pattern"))
					{
						mapping.addPattern(childNodes.item(j).getFirstChild().getNodeValue());
					}
				}
			}
		}
		mappings.remove(0);
	}

	
	
	public static List<Entity> getEntitys() {
		return entitys;
	}
	


	public static List<Mapping> getMappings() {
		return mappings;
	}
	


	public static List<String> getWelFileList() {
		return welFileList;
	}

	
	
	
	
	
	
}
