
package javax.servlet.http;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
import java.util.Map;

import javax.servlet.RequestDispatcher;

import com.honey.JSPservlet.JSPContainer;
import com.honey.filter.Filter;
import com.honey.filter.FilterChain;
import com.honey.servlet.HttpServlet;
import com.honey.servlet.Servlet;
import com.honey.servlet.WebApp;


/**
 * 
 * @author Honey
 *
 */
public class HttpServletResponse {

	private PrintWriter out;
//	private OutputStream raw;
//	private Writer out;
	private Socket client;
	private String ContentType;
	//内容
	private StringBuilder content;
	//头信息
	private StringBuilder headInfo;
	//内容长度
	private int len;
	private final String BLANK =" ";
	private final  String CRLF = "\r\n";
	private HttpServletRequest req;
	
	private HttpServletResponse() {
		content =new StringBuilder();
		headInfo=new StringBuilder();
		len =0;
	}
	public HttpServletResponse(Socket client) {
		this();
		try {
			this.client=client;
//			raw=new BufferedOutputStream(client.getOutputStream());
			out=new PrintWriter(client.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
			headInfo = null;
		}
	}
	
	public HttpServletResponse(OutputStream os) {
		this();
		out=new PrintWriter(os);
	}
	
	public HttpServletResponse print(Object info) {
//		len+=datas.length;
//		this.datas=datas;
		content.append((String)info);
		len+=((String) info).getBytes().length;
		return this;
	}

	
	public	HttpServletResponse println(Object info) {
		content.append(info).append(CRLF);
		len+=(info+CRLF).getBytes().length;
		return this;
	}
	
	public void pushToBrowser(int code) throws IOException {
		if(null ==headInfo) {
			code = 505;
		}
		createHeadInfo(code);
		if(code==500)
		{
			String body=new StringBuilder("<HTML>\r\n")
					.append("<HEAD><title>Not Implemented</title>\r\n")
					.append("</head>\r\n")
					.append("<body>\r\n")
					.append("<h1>HTTP Error 501:Not Implemented</h1>\r\n")
					.append("</body></html>\r\n")
					.toString();
			out.write(body);
			out.flush();
		}
		else if(code==404)
		{
			String body=new StringBuilder("<HTML>\r\n")
					.append("<HEAD><title>File Not Found</title>\r\n")
					.append("</head>\r\n")
					.append("<body>\r\n")
					.append("<h1>HTTP Error 404:File Not Found</h1>\r\n")
					.append("</body></html>\r\n")
					.toString();
			out.write(body);
			out.flush();
		}
		else
		{
			out.write(headInfo.toString());
			out.write(content.toString());
			out.flush();
			out.close();
//			raw.write(datas);
//			raw.flush();
			client.close();
		}

	}
	
	
	private void createHeadInfo(int code) {
		headInfo.append("HTTP/1.1").append(BLANK);
		headInfo.append(code).append(BLANK);
		switch(code) {
			case 200:
				headInfo.append("OK").append(CRLF);
				break;
			case 404:
				headInfo.append("NOT FOUND").append(CRLF);
				break;	
			case 500:
				headInfo.append("SERVER ERROR").append(CRLF);
				break;	
		}
		headInfo.append("Date:").append(new Date()).append(CRLF);
		headInfo.append("Server:").append("Honey Server/0.0.1;charset=utf-8").append(CRLF);
		headInfo.append("Content-type:").append(ContentType).append(CRLF);
		headInfo.append("Content-length:").append(len).append(CRLF);
		headInfo.append(CRLF);
		System.out.println("返回头");
		System.out.println(headInfo);
	}
	public String getContentType() {
		return ContentType;
	}
	public void setContentType(String contentType) {
		ContentType = contentType;
	}
	public PrintWriter getWriter() {
		return out;
	}

	private void filt(Servlet servlet,String url,HttpServletRequest req) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		FilterChain chain = new FilterChain();
  	  	chain.setServlet(servlet);
  	  	
//  	  	ServletContext context = WebApp.getContext();
//  	  	Map<String,String> urlmap = WebApp.getFilterUrl();
  	  	Filter filter=WebApp.getFilterFromURL(url);
  	  	chain.addFilter(filter);
  	  	try {
			chain.doFilter(req, this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void sendRedirect(String fileName)
	{
		JSPContainer jsp;
		

		try {
//			HttpServletRequest req = new HttpServletRequest(this.client.getInputStream());
			jsp = new JSPContainer(fileName,req);
			jsp.contain();	
			String className=fileName.replace(".", "_");
			Class<?> clz;
			String root="com.honey.servlet."+className;
			clz = Class.forName(root);
			HttpServlet servlet=(HttpServlet)clz.getConstructor().newInstance();
			System.out.println(servlet);
			if(null!=servlet)
			{
				servlet.service(req, this);
//				this.pushToBrowser(200);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	

	}

	public HttpServletRequest getReq() {
		return req;
	}
	public void setReq(HttpServletRequest req) {
		// TODO Auto-generated method stub
		this.req = req;
	}
	
	
}
