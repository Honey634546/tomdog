package com.honey.reflexTest;

import java.lang.reflect.InvocationTargetException;

/**
 * 
 * @author Honey
 *
 */

public class Reflex {
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException
	{
		Iphone iphone=new Iphone();
		
		
		Class<?> clz=null;
		//����һ    ����.getClass()
		clz=iphone.getClass();
		System.out.println(clz);
		//������    ����.class
		clz=null;
		clz=Iphone.class;
		System.out.println(clz);
		//������    
		clz=null;
		clz=Class.forName("com.honey.reflexTest.Iphone");
		System.out.println(clz);
		
		Object iphone2 =(Iphone)clz.getConstructor().newInstance();
		System.out.println(iphone2);
		
	}
}