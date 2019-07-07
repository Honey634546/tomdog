package javax.servlet.http;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.honey.ui.ServerGUI;



/**
 * 
 *  服务器
 * @author Honey
 * 
 *
 */
public class Server {
	
	private ServerSocket serverSocket ;
	private boolean isRunning;
//	private File root;
	String rootDir;
	int port;
	
	/**
	 * 
	 * @param rootDir 文件根路径
	 * @param port    服务器端口
	 */
	public Server(String rootDir,int port)
	{
		this.rootDir=rootDir;
		this.port=port;
	}

	
	public static void main(String[] args) {
//		Server server = new Server("WebContent",80);
//		server.start();
		new ServerGUI();
	}

	
	 
	public void start() {
		try {
			serverSocket =  new ServerSocket(port);
			isRunning=true;
			ServerGUI.addlog("服务器已启动\n端口号为"+port+",根目录为"+rootDir);
			receive();
		} catch (IOException e) {
			e.printStackTrace();
			stop();
		}
	}

	public void receive() {
		while(isRunning)
		{
			try {
				System.out.println("waiting for connection...");
				Socket client = serverSocket.accept();
				System.out.println("get a connect from "+client.getLocalAddress());
				new Thread(new Dispatcher(client,rootDir)).start();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("server is fail");
				ServerGUI.addlog("server is fail");
			}
		}
	}

	public void stop() {
		isRunning=false;
		try {
			this.serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
