import jdk.internal.util.xml.impl.Input;
import jdk.nashorn.internal.runtime.ECMAException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class  ServletListener  {
    Socket client;
    static ExecutorService executorService= Executors.newFixedThreadPool(20);  //静态线程池,防止多次创建爆内存
     public ServletListener (ServerSocket server){
         Runnable getHttp=()->{
             try{
                 InputStream inputStream=client.getInputStream();
                 HttpServletRequest request= new Myrequset1(inputStream);
                 OutputStream outputStream=client.getOutputStream();
                 HttpServletResponse response=new Myresponse(outputStream);
                 HttpServlet httpServlet=new MyServlet();
                 httpServlet.service(request,response);
                 client.close();
                 System.out.println("HTTP closed");
             } catch (Exception e){
                 e.printStackTrace();
             }
         };

         while (true) {
             try {
                 client=server.accept();
                 System.out.println("HTTP Linked");
                 executorService.submit(getHttp);
             } catch (Exception e){
                 e.printStackTrace();
             }
         }

     }
    public static void main(String[] args) throws IOException{
        ServerSocket server=new ServerSocket(3333);
        ServletListener servletListener=new ServletListener(server);
    }
}



