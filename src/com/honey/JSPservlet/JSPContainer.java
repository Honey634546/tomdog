package com.honey.JSPservlet;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

/**
 * 
 * @author Honey
 *
 */

public class JSPContainer {
	
	private OutputStream os;
	private PrintWriter pw;
	private HttpServletRequest req;
	private String JSPFile;
	private String rootDir;
	private String newName;
	public JSPContainer(String JSPFile,HttpServletRequest req) throws Exception
	{
		this.JSPFile=JSPFile;
		this.rootDir=System.getProperty("user.dir");
		this.req=req;
		init();
	}
	
//	public void contain() throws Exception
//	{
//		String method=req.getMethod();
//		method=method.substring(0, 1).toUpperCase() + method.substring(1);
//		pw.println("public void do"+method+"(HttpServletRequest request, HttpServletResponse response ) { ");
//		BufferedReader br=new BufferedReader(new FileReader(rootDir+"\\WebContent\\"+JSPFile));
//		String line=null;
//		String rexEx="<%=.*?%>";
//		line=br.readLine();
//		line=br.readLine();
//		while((line=br.readLine())!=null)
//		{
//			Pattern pattern=Pattern.compile(rexEx);
//			Matcher matcher=pattern.matcher(line);
//			List<Integer> index=new ArrayList<Integer>();
//			index.add(0);
//			int temp=0;
//			while(matcher.find())
//			{
//				index.add(line.indexOf("<%=",temp));
//				index.add(line.indexOf("%>",temp));
//				temp=line.indexOf("%>")+1;
//			}
//			index.add(line.length()-1);
////			System.out.println(index);
//			int i=0;
//			for(;i<index.size()-2;)
//			{
//				String tmp1=line.substring(index.get(i++),index.get(i));
//				tmp1=tmp1.replace("%>", "");
////				System.out.println(tmp1);
//				pw.println("response.println(\'"+tmp1+"\');");
//				String tmp2=line.substring(index.get(i++),index.get(i));
//				tmp2=tmp2.replace("<%=", "");
//				pw.println("response.println("+tmp2+");");
////				System.out.println(tmp2);
//			}
//			String tmp=line.substring(index.get(i));
//			tmp=tmp.replace("%>", "");
////			System.out.println(tmp);
//			pw.println("response.println(\'"+tmp+"\');");
//		}
//
//		pw.println("}");
//		pw.println("}");
//		pw.close();
//		os.close();
//		complierAndRun();
//	}
	
	public void contain() throws Exception
	{
		String method=req.getMethod();
		method=method.substring(0, 1).toUpperCase() + method.substring(1);
		pw.println("public void do"+method+"(HttpServletRequest request, HttpServletResponse response ) { ");
		pw.println("PrintWriter out=response.getWriter();");
		BufferedReader br=new BufferedReader(new FileReader(rootDir+"\\WebContent\\"+JSPFile));
		String str=br.readLine();
		String line=null;
//		str+="\r\n";
		while((line=br.readLine())!=null)
		{
			str+="\r\n"+line;
		}
		boolean inhtml=false;
//		System.out.println(str.length());
		for(int i=0;i<str.length();i++)
		{
//			System.out.println(i);
			if(i+4<str.length()&&str.substring(i, i+4).equals("<%--"))
			{
				if(inhtml)
				{
					pw.append("\");\n");
					inhtml=false;
				}
				i=str.indexOf("--%>",i)+1;
			}
			else if(i+3<str.length()&&str.substring(i, i+3).equals("<%@"))
			{
				if(inhtml)
				{
					pw.append("\");\n");
					inhtml=false;
				}
				i=str.indexOf("%>",i)+1;
			}
			else if(i+3<str.length()&&str.substring(i, i+3).equals("<%="))
			{
				if(inhtml)
				{
					pw.append("\");\n");
					inhtml=false;
				}
				pw.append("response.println("+str.substring(i+3, str.indexOf("%>", i))+");\n");
				i=str.indexOf("%>",i)+1;
			}
			else if(i+2<str.length()&&str.substring(i, i+2).equals("<%"))
			{
				if(inhtml)
				{
					pw.append("\");\n");
					inhtml=false;
				}
				pw.append(str.substring(i+2, str.indexOf("%>", i))+"\n");
				i=str.indexOf("%>",i)+1;
			}
			else
			{
				if(i+2<str.length()&&str.substring(i, i+2).equals("\r\n"))
				{
					i=i+1;
				}
				else
				{
					if(!inhtml)
					{
						pw.append("response.println(\""+str.substring(i, i+1));
						inhtml=true;
					}
					else
					{
						if(str.substring(i,i+1).equals("\""))
						{
							pw.append("\\");
						}
						pw.append(str.substring(i, i+1));
					}
				}
			}
		}
		pw.append("\");\n");
		pw.append("}\n");
		pw.append("}");
		pw.close();
		os.close();
		complierAndRun();
	}
	
	 public void complierAndRun(){
		   try {
					
			 System.out.println(System.getProperty("user.dir"));
			 //动态编译
			 JavaCompiler javac = ToolProvider.getSystemJavaCompiler();
			 int status = javac.run(null, null, null, "-d", System.getProperty("user.dir")+"\\bin",System.getProperty("user.dir")+"\\src\\com\\honey\\servlet\\"+newName);
			 if(status!=0){
				 System.out.println("没有编译成功！");
			 }
			 }catch (Exception e)
		   {
				 System.out.println(e);
		   }
		  }
	
	public void init() throws Exception
	{
		newName=JSPFile.replace(".", "_");
		newName+=".java";
		os = new FileOutputStream(System.getProperty("user.dir")+"\\src\\com\\honey\\servlet\\"+newName);
		pw=new PrintWriter(os);
		pw.println("package com.honey.servlet;");
		pw.println("import javax.servlet.http.HttpServletRequest;");
		pw.println("import java.io.PrintWriter;");
		pw.println("import javax.servlet.http.HttpServletResponse;");
		pw.println("public class "+JSPFile.replace(".", "_")+" extends HttpServlet { ");
	}
	
	public static void main(String[] args) throws Exception
	{
//		JSPContainer jsp=new JSPContainer("test4.jsp","E:\\eclipse-workspace\\Server7\\WebContent\\");
//		jsp.contain();
	}
	
	
	
	

}
