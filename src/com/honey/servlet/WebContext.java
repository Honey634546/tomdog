package com.honey.servlet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.honey.servlet.Entity;
import com.honey.servlet.Mapping;

/**
 * 
 * 处理servlet映射
 * @author Honey
 *
 */

public class WebContext {
	private List<Entity> entitys=null;
	private List<Mapping> mappings=null;
	
	//key--> servlet-name      value--> servlet-class
	private Map<String,String> entityMap=new HashMap<String,String>();	
	//key -->url-pattern     value-->servlet-name
	private Map<String,String> MappingMap=new HashMap<String,String>();
	
	
	public WebContext(List<Entity> entitys,List<Mapping> mappings) {
		// TODO Auto-generated constructor stub
		this.entitys=entitys;
		this.mappings=mappings;
		
		for(Entity entity:entitys)
		{
			entityMap.put(entity.getName(),entity.getClz());
		}
		for(Mapping mapping:mappings)
		{
			for(String pattren:mapping.getPatterns())
			{
				MappingMap.put(pattren,mapping.getName());
			}

		}
	}
	
	
	/**
	 * 通过url找class
	 * @param pattern  url-pattern--->servlet-name---->servlet-class
	 * @return   
	 * 
	 */
	public String getClz(String pattern)
	{

		String name=MappingMap.get(pattern);
		return entityMap.get(name);
		
	}
}
