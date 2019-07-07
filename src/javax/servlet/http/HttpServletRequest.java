package javax.servlet.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;

import com.honey.session.Session;
import com.honey.ui.ServerGUI;

/**
 * 
 * @aim 封装请求信息
 * @author Honey
 *
 */
public class HttpServletRequest {
	

	private RequestDispatcher requestDispatcher;
//	请求头信息
	private String requestInfo;
//	请求方法
	private String method; 
//	请求文件
	private String url; 
//	参数
	private String queryStr;
	
	private Enumeration<?> HeaderNames;
	private HashMap<String,String> map;
	private Session session = new Session();
//	换行符
	private final  String CRLF = "\r\n";
	private Map<String,List<String>> parameterMap;
	private Map<String,String> attribute;
	
	public HttpServletRequest(Socket client) throws IOException
	{
		this(client.getInputStream());
	}
	
	public HttpServletRequest(InputStream is)
	{
		parameterMap = new HashMap<String,List<String>>();
		attribute=new HashMap<String, String>();
		byte[] datas = new byte[1024*1024*1024];
		int len;
		try {
			len = is.read(datas);
			this.requestInfo = new String(datas,0,len);
			System.out.println(requestInfo);
			ServerGUI.addlog(requestInfo.substring(0, requestInfo.indexOf(CRLF)));
			parseRequestInfo();
			HeaderEnum();
		} catch (IOException e) {
//			e.printStackTrace();
//			return ;
		}
	}
	
	public String getHeader(String key)
	{
		return map.get(key);
	}
	
	@SuppressWarnings("unchecked")
	private void HeaderEnum()
	{
		map=new HashMap<String, String>();
		String tmp=requestInfo;
		tmp=tmp.substring(tmp.indexOf("\r\n")+2);
		String[] keyValues=tmp.split("\r\n");
		for(String queryStr:keyValues) {
			//2、再次分割字符串  =
			String[] kv = queryStr.split(":");
			kv =Arrays.copyOf(kv, 2);
			//获取key和value
			String key = kv[0];
			String value = kv[1]==null?null:decode( kv[1],"utf-8");
			//存储到map中
			map.put(key, value);
		}
		HeaderNames= Collections.enumeration(map.keySet());
		
		
	}
	
	private void parseRequestInfo() 
	{
		this.method = this.requestInfo.substring(0, this.requestInfo.indexOf("/")).toLowerCase();
		this.method=this.method.trim();
		System.out.println("method--->"+method);
		int startIdx = this.requestInfo.indexOf("/")+1;
		int endIdx = this.requestInfo.indexOf("HTTP/");
		this.url = this.requestInfo.substring(startIdx, endIdx);	
		this.url=this.url.trim();
		System.out.println("url--->"+url);
		int queryIdx =this.url.indexOf("?");	
		if(queryIdx>=0) {
			String[] urlArray = this.url.split("\\?");
			this.url =urlArray[0];
			queryStr =urlArray[1];
		}
		if(method.equals("post")) {
//			System.out.println("this is post------");
			String qStr =this.requestInfo.substring(this.requestInfo.lastIndexOf(CRLF)).trim();
			if(null==queryStr) {
				queryStr =qStr;
			}else { 
				queryStr +="&"+qStr;
			}
		}
		queryStr = null==queryStr?"":queryStr;
		System.out.println("method"+"-->"+"url"+"-->"+"queryStr");
		System.out.println(method+"-->"+url+"-->"+queryStr);
		convertMap();
		
	}
	
	
	
	//处理请求参数为Map
		private void convertMap() {
			//1、分割字符串 &
			String[] keyValues =this.queryStr.split("&");
			for(String queryStr:keyValues) {
				//2、再次分割字符串  =
				String[] kv = queryStr.split("=");
				kv =Arrays.copyOf(kv, 2);
				//获取key和value
				String key = kv[0];
				String value = kv[1]==null?null:decode( kv[1],"utf-8");
				//存储到map中
				if(!parameterMap.containsKey(key)) { //第一次
					parameterMap.put(key, new ArrayList<String>());
				}
				parameterMap.get(key).add(value);			
			}
		}
		/**
		 * 处理中文
		 * @return
		 */
		private String decode(String value,String enc) {
			try {
				return java.net.URLDecoder.decode(value, enc);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		/**
		 * 通过name获取对应的多个值
		 * @param key
		 * @return
		 */
		public String[] getParameterValues(String key) {
			List<String> values = this.parameterMap.get(key);
			if(null==values || values.size()<1) {
				return null;
			}
			return values.toArray(new String[0]);
		}
		/**
		 * 通过name获取对应的一个值
		 * @param key
		 * @return
		 */
		public String getParameter(String key) {
			String []  values =getParameterValues(key);
			return values ==null?null:values[0];
		}

	public String getMethod() {
		return method;
	}
	
	public void setAttribute(String key,String value)
	{
		attribute.put(key, value);
	}
	
	public String getAttribute(String key)
	{
		return attribute.get(key);
	}


	public String getUrl() {
		return url;
	}

	public RequestDispatcher getRequestDispatcher(String fileName) {
		requestDispatcher=new RequestDispatcher(fileName);
		return requestDispatcher;
	}

	public Enumeration<?> getHeaderNames() {
		return HeaderNames;
	}
	
	public Session getSession()
	{
		return session;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	



	
	
	
	

}
