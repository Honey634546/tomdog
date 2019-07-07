package javax.servlet;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.honey.JSPservlet.JSPContainer;
import com.honey.servlet.HttpServlet;

public class RequestDispatcher {

	private String fileName;
	public RequestDispatcher(String fileName)
	{
		this.fileName=fileName;
		
	}
	
	
	public void forward(HttpServletRequest req,HttpServletResponse resp)
	{
		JSPContainer jsp;
		try {
			jsp = new JSPContainer(fileName,req);
			jsp.contain();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String className=fileName.replace(".", "_");
		Class<?> clz;

		try {
			String root="com.honey.servlet."+className;
			clz = Class.forName(root);
			HttpServlet servlet=(HttpServlet)clz.getConstructor().newInstance();
			System.out.println(servlet);
			if(null!=servlet)
			{
				servlet.service(req, resp);
				resp.pushToBrowser(200);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}

		}
}
