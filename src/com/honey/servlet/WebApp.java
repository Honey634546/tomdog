package com.honey.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.honey.filter.Filter;
import com.honey.servlet.Entity;
import com.honey.servlet.Mapping;

/**
 * 
 * 
 * @author Honey
 *
 */

public class WebApp {
	private static WebContext webcontext;
	private static WebContext webcontext1;

	private static Entity entity = new Entity();
	private static Mapping mapping = new Mapping();

	private static List<Entity> servlets = new ArrayList<Entity>();
	private static List<Mapping> sermappings = new ArrayList<Mapping>();

	private static List<Entity> filters = new ArrayList<Entity>();
	private static List<Mapping> filmappings = new ArrayList<Mapping>();
	private static List<String> welFileList = new ArrayList<String>();

	static {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse("WebContent/WEB-INF/web.xml");

			NodeList fileList = document.getElementsByTagName("welcome-file-list");
			getFileList(fileList);
//			System.out.println(welFileList);

			NodeList entitylist = document.getElementsByTagName("servlet");
			getServlet(entitylist);
//			for(int i=0;i<entitys.size();i++)
//			{
//				System.out.println(entitys.get(i).getName());
//				System.out.println(entitys.get(i).getClz());
//			}

			NodeList mappingList = document.getElementsByTagName("servlet-mapping");
			getServletMapping(mappingList);

			entitylist = document.getElementsByTagName("filter");
			getfilter(entitylist);

			mappingList = document.getElementsByTagName("filter-mapping");
			getFilterMapping(mappingList);

			webcontext = new WebContext(servlets, sermappings);
			webcontext1 = new WebContext(filters, filmappings);

//			for(int i=0;i<mappings.size();i++)
//			{
//				System.out.println(mappings.get(i).getName());
//				System.out.println(mappings.get(i).getPatterns());
//			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("配置xml文件失败");
		}

	}

	/**
	 * 找到welcome-file-list <welcome-file-list>
	 * <welcome-file>index.html</welcome-file>
	 * <welcome-file>index.htm</welcome-file> <welcome-file>index.jsp</welcome-file>
	 * <welcome-file>default.html</welcome-file>
	 * <welcome-file>default.htm</welcome-file>
	 * <welcome-file>default.jsp</welcome-file> </welcome-file-list>
	 * 
	 * @param list
	 */
	public static void getFileList(NodeList list) throws Exception {
		for (int i = 0; i < list.getLength(); i++) {
			Element element = (Element) list.item(i);
			NodeList childNodes = element.getChildNodes();
			for (int j = 0; j < childNodes.getLength(); j++) {
				if (childNodes.item(j).getNodeType() == Node.ELEMENT_NODE) {
					if (childNodes.item(j).getNodeName().equals("welcome-file")) {
						welFileList.add(childNodes.item(j).getFirstChild().getNodeValue());
					}
				}
			}
		}
	}

	/**
	 * 找到servlet <servlet> <description></description>
	 * <display-name>PetServlet</display-name>
	 * <servlet-name>PetServlet</servlet-name>
	 * <servlet-class>com.honey.server.PetServlet</servlet-class> </servlet>
	 * 
	 * @param list
	 */
	public static void getServlet(NodeList list) {
		for (int i = 0; i < list.getLength(); i++) {
			Element element = (Element) list.item(i);
			NodeList childNodes = element.getChildNodes();
			for (int j = 0; j < childNodes.getLength(); j++) {
				if (childNodes.item(j).getNodeType() == Node.ELEMENT_NODE) {
					if (childNodes.item(j).getNodeName().equals("servlet-name")) {
						entity.setName(childNodes.item(j).getFirstChild().getNodeValue());
					}
					if (childNodes.item(j).getNodeName().equals("servlet-class")) {
						entity.setClz(childNodes.item(j).getFirstChild().getNodeValue());
						servlets.add(entity);
						entity = new Entity();
					}
				}
			}
		}
	}

	/**
	 * 找到ervlet-mapping <servlet-mapping> <servlet-name>Login</servlet-name>
	 * <url-pattern>/Login</url-pattern> </servlet-mapping>
	 * 
	 * @param list
	 */
	public static void getServletMapping(NodeList list) {
		for (int i = 0; i < list.getLength(); i++) {
			Element element = (Element) list.item(i);
			NodeList childNodes = element.getChildNodes();
			for (int j = 0; j < childNodes.getLength(); j++) {
				if (childNodes.item(j).getNodeType() == Node.ELEMENT_NODE) {
					if (childNodes.item(j).getNodeName().equals("servlet-name")) {
						// 由于url-pattern存在多个，则在扫描到servlet-name时添加，
						// 这样导致第一个mapping是null，在最后删除
						sermappings.add(mapping);
						mapping = new Mapping();
						mapping.setName(childNodes.item(j).getFirstChild().getNodeValue());
					}
					if (childNodes.item(j).getNodeName().equals("url-pattern")) {
						mapping.addPattern(childNodes.item(j).getFirstChild().getNodeValue());
					}
				}
			}
		}
		sermappings.add(mapping);
		sermappings.remove(0);
	}

	/**
	 * <filter> <display-name>AccessFilter</display-name>
	 * <filter-name>AccessFilter</filter-name>
	 * <filter-class>com.honey.servlet.AccessFilter</filter-class> </filter>
	 * 
	 * @param list
	 */
	public static void getfilter(NodeList list) {
		for (int i = 0; i < list.getLength(); i++) {
			Element element = (Element) list.item(i);
			NodeList childNodes = element.getChildNodes();
			for (int j = 0; j < childNodes.getLength(); j++) {
				if (childNodes.item(j).getNodeType() == Node.ELEMENT_NODE) {
					if (childNodes.item(j).getNodeName().equals("filter-name")) {
						entity.setName(childNodes.item(j).getFirstChild().getNodeValue());
					}
					if (childNodes.item(j).getNodeName().equals("filter-class")) {
						entity.setClz(childNodes.item(j).getFirstChild().getNodeValue());
						filters.add(entity);
						entity = new Entity();
					}
				}
			}
		}
	}

	/**
	 * <filter-mapping> <filter-name>AccessFilter</filter-name>
	 * <url-pattern>/*</url-pattern> </filter-mapping>
	 * 
	 * @param list
	 */
	public static void getFilterMapping(NodeList list) {
		for (int i = 0; i < list.getLength(); i++) {
			Element element = (Element) list.item(i);
			NodeList childNodes = element.getChildNodes();
			for (int j = 0; j < childNodes.getLength(); j++) {
				if (childNodes.item(j).getNodeType() == Node.ELEMENT_NODE) {
					if (childNodes.item(j).getNodeName().equals("filter-name")) {
						// 由于url-pattern存在多个，则在扫描到servlet-name时添加，
						// 这样导致第一个mapping是null，在最后删除
						filmappings.add(mapping);
						mapping = new Mapping();
						mapping.setName(childNodes.item(j).getFirstChild().getNodeValue());
					}
					if (childNodes.item(j).getNodeName().equals("url-pattern")) {
						mapping.addPattern(childNodes.item(j).getFirstChild().getNodeValue());
					}
				}
			}
		}
		filmappings.add(mapping);
		filmappings.remove(0);
	}

	public static List<Entity> getEntitys() {
		return servlets;
	}

	public static List<Mapping> getMappings() {
		return sermappings;
	}

	public static List<String> getWelFileList() {
		return welFileList;
	}

	public static List<Entity> getFilters() {
		return filters;
	}

	public static List<Mapping> getFilmappings() {
		return filmappings;
	}

	/**
	 * 通过url获得对应的servlet
	 * 
	 * @param url
	 * @return
	 */
	public static HttpServlet getServletFromURL(String url) {
		String className = webcontext.getClz("/" + url);
		if (className == null)
			return null;
		Class<?> clz;
		try {
			clz = Class.forName(className);
			HttpServlet servlet = (HttpServlet) clz.getConstructor().newInstance();
			return servlet;
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
		return null;

	}
	
	
	public static Filter getFilterFromURL(String url) {
		String className = webcontext1.getClz("/" + url);
		if (className == null)
			return null;
		Class<?> clz;
		try {
			clz = Class.forName(className);
			Filter filter = (Filter) clz.getConstructor().newInstance();
			return filter;
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
		return null;

	}

}
