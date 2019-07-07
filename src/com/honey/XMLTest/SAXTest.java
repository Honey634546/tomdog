package com.honey.XMLTest;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;



/**
 * 
 * SAX操作XML
 * @author Honey
 *
 */

public class SAXTest {
	public static void main(String[] args) throws Exception, SAXException {
		SAXParserFactory factory=SAXParserFactory.newInstance();
		
		SAXParser saxParser=factory.newSAXParser();
		
		SAXHandel handel=new SAXHandel();
		
		saxParser.parse("src/com/honey/XMLTest/test.xml", handel);
	}
}

class SAXHandel extends DefaultHandler
{
	@Override
	public void startDocument() throws SAXException
	{
		super.startDocument();
		System.out.println("---解析开始-----");
	}
	
	@Override
	public void endDocument() throws SAXException
	{
		super.endDocument();
		System.out.println("-----解析结束---");
	}
	
	@Override
	public void startElement(String uri,String localName,String qName, Attributes attributes) throws SAXException
	{
		super.startElement(uri, localName, qName, attributes);
		if(qName.equals("student"))
		{
			System.out.println("----开始遍历student---");
		}
		else if(!qName.equals("student")&&!qName.equals("class"))
		{
			System.out.println("节点名称："+qName+"----");
		}
	}
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        if (qName.equals("student")){
            System.out.println(qName+"遍历结束");
            System.out.println("============结束遍历student=============");
        }
    }
	
	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        String value = new String(ch,start,length).trim();
        if (!value.equals("")) {
            System.out.println(value);
        }
    }

	
}
