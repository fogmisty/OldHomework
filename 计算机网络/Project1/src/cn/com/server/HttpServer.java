package cn.com.server;  
  
import java.io.File;
import java.io.IOException;
import java.io.InputStream;  
import java.io.OutputStream;  
import java.net.InetAddress;  
import java.net.ServerSocket;  
import java.net.Socket;  
  
public class HttpServer {  

    public static final String WEB_ROOT = System.getProperty("user.dir")+File.separator+"webroot";  
      
    private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";  
      
    private boolean shutdown = false;  
      
    public static void main(String[] args) throws IOException {  
        HttpServer server = new HttpServer();  
        server.await();  
    }  
    
    public void await() throws IOException{  
        ServerSocket serverSocket = null;  
        int port = 8888;
        
        try {  
            serverSocket = new ServerSocket(port,1,InetAddress.getByName("127.0.0.1"));  
        }
        catch (Exception e) {  
            e.printStackTrace();  
            System.exit(0);  
        } 
        
        while(!shutdown){  
            Socket socket = null;  
            InputStream input = null;  
            OutputStream output = null;  
            try {  
                socket = serverSocket.accept();  
                input = socket.getInputStream();  
                output = socket.getOutputStream();  
                //创建请求对象和解析 
                Request request=new Request(input);  
                request.parse();  
                  
                //创建响应对象
                Response response = new Response(output);  
                response.setRequest(request);  
                response.sendStaticResource();  
            }
            catch (Exception e) {  
                e.printStackTrace();  
                continue;  
            } finally{
            	output.close();
            }
         }  
     }  
 }  