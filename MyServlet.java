import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

public class MyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cname=null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver init");
        } catch (ClassNotFoundException e){
            System.out.println("not found");
            e.printStackTrace();
        }
        try {

            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/mydb", "root", "123456");
            System.out.println("connecting...");
            Statement stat=connection.createStatement();
            ResultSet resultSet= stat.executeQuery("SELECT * FROM course where cid=02");
            while(resultSet.next()){
              cname=resultSet.getString("cname");
            }
            System.out.println(cname);
            stat.close();
            connection.close();
            System.out.println("connect break!");
            String header = "HTTP/1.1 200 OK!Yuan\r\n";
            PrintWriter printWriter = resp.getWriter();
            printWriter.println(header);
            resp.flushBuffer();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       this.doGet(req,resp);
    }
}
