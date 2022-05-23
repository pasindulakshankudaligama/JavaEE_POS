package servlet;

import javax.annotation.Resource;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/order")
public class OrderPurchaseServlet extends HttpServlet {
    @Resource(name = "java:comp/env/jdbc/pool")
    DataSource ds;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        JsonObjectBuilder response = Json.createObjectBuilder();
        PrintWriter writer = resp.getWriter();
        Connection connection = null;

     try {
      connection = ds.getConnection();
      ResultSet rst;
      PreparedStatement pstm;
      String option = req.getParameter("option");
      switch (option){
          case "LOADCUSIDS":
               rst = connection.prepareStatement("SELECT id FROM customer").executeQuery();
              while (rst.next()) {
                  String id = rst.getString(1);
                  objectBuilder.add("id",id);
                  arrayBuilder.add(objectBuilder.build());
              }
              response.add("data",arrayBuilder.build());
              response.add("message","Done");
              response.add("status",200);
              writer.print(response.build());
              break;
      }

     } catch (SQLException e) {
      e.printStackTrace();
     }finally {
         try {
             connection.close();
         } catch (SQLException e) {
             e.printStackTrace();
         }
     }

    }
}
