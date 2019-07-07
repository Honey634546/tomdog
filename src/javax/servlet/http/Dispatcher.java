package javax.servlet.http;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.URLConnection;
import java.nio.file.Files;

import javax.servlet.RequestDispatcher;

import com.honey.filter.Filter;
import com.honey.servlet.HttpServlet;
import com.honey.servlet.WebApp;


/**
 * 
 * @author Honey
 *
 */

public class Dispatcher implements Runnable{
	private Socket client;
	private HttpServletRequest request;
	private HttpServletResponse response;	
	private String rootDir;
	public Dispatcher(Socket client,String rootDir) {
		// TODO Auto-generated constructor stub
		this.client=client;
		this.rootDir=rootDir;
		try {
			request=new HttpServletRequest(client);
			response=new HttpServletResponse(client);
			response.setReq(request);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				client.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
//			Filter filter=WebApp.getFilterFromURL(request.getUrl());
			boolean flag=false;
			try
			{
				flag=request.getUrl().substring(request.getUrl().lastIndexOf(".")).equals(".jsp");
			}catch (Exception e)
			{
				flag=false;
			}
			HttpServlet servlet=WebApp.getServletFromURL(request.getUrl());
			if(null!=servlet)
			{
				servlet.service(request, response);
				response.pushToBrowser(200);
			}
			else if (flag)
			{
				RequestDispatcher requestDispatcher=request.getRequestDispatcher(request.getUrl());
				requestDispatcher.forward(request, response);
			}
			else
			{
				String fileName=request.getUrl();
//				实现welcome-flie-list
//				if(fileName.equals(""))
//				{
//					List<String> welList=WebApp.getWelFileList();
//					for(int i=0;i<welList.size();i++)
//					{
//						fileName=welList.get(i);
//						String contentType=URLConnection.getFileNameMap().getContentTypeFor(fileName);
//						File theFile =new File(rootDir,fileName.substring(0,fileName.length()));
//						if(theFile.canRead())
//						{
//							byte[] theData=Files.readAllBytes(theFile.toPath());
//							response.setContentType(contentType);
//							response.print(theData);
//							response.pushToBrowser(200);
//						}
//					}
//				}
				String contentType=URLConnection.getFileNameMap().getContentTypeFor(fileName);
				File theFile =new File(rootDir,fileName.substring(0,fileName.length()));
				if(theFile.canRead()&&theFile.isFile())
				{
					byte[] theData=Files.readAllBytes(theFile.toPath());
					response.setContentType(contentType);
					response.print(new String(theData));
					response.pushToBrowser(200);
				}
				else if(theFile.isDirectory()&&theFile.canRead())
				{
					String body=createHTML(theFile);
					response.println(body);
					response.pushToBrowser(200);
				}
				else
					response.pushToBrowser(404);
			}
		}catch(Exception e)
		{
			try {
				response.pushToBrowser(500);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	public String createHTML(File file) throws IOException
	{
		StringBuilder stringhtml=new StringBuilder();
		File[] fileList=file.listFiles();
		stringhtml.append("<html><head>");
		stringhtml.append(" <meta http-equiv=\"content- type\" content=\"text/heml; charset=gb2312\">");
		stringhtml.append("<title>文件目录</title>");
		stringhtml.append("</head>");
		stringhtml.append("<body>");
		stringhtml.append("<h1>目录</h1>");
		stringhtml.append("<hr>");
		int i=0;
		while(i<fileList.length)	
		{
			String filePath=fileList[i].getPath();
			String href=fileList[i].getPath();
			href=href.replace(rootDir, "");
			System.out.println(rootDir);
			System.out.println(href);
			System.out.println(filePath);
			filePath=filePath.replace(file.getPath()+"\\", "");
			if(fileList[i].isFile())
				stringhtml.append("<a href='"+href+"'>"+filePath+"</a>").append("\t").append(fileList[i].length()).append("<br>");
			else
				stringhtml.append("<a href='"+href+"'>"+filePath+"</a>").append("<br>");
			i++;
		}
		stringhtml.append("</body></html>");
		return stringhtml.toString();
	}


}
