package com.honey.XMLTest;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;



/**
 * 
 * SAX����XML
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
		System.out.println("---������ʼ-----");
	}
	
	@Override
	public void endDocument() throws SAXException
	{
		super.endDocument();
		System.out.println("-----��������---");
	}
	
	@Override
	public void startElement(String uri,String localName,String qName, Attributes attributes) throws SAXException
	{
		super.startElement(uri, localName, qName, attributes);
		if(qName.equals("student"))
		{
			System.out.println("----��ʼ����student---");
		}
		else if(!qName.equals("student")&&!qName.equals("class"))
		{
			System.out.println("�ڵ����ƣ�"+qName+"----");
		}
	}
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        if (qName.equals("student")){
            System.out.println(qName+"��������");
            System.out.println("============��������student=============");
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
