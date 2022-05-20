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
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/item")
public class ItemServlet extends HttpServlet {

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
            String option = req.getParameter("option");
            connection  = ds.getConnection();

            switch (option){
                case "GETALL" :
                    ResultSet rst = connection.prepareStatement("SELECT * FROM item").executeQuery();
                    while (rst.next()) {
                        String code = rst.getString(1);
                        String description = rst.getString(2);
                        int qtyOnHand = rst.getInt(3);
                        double unitPrice = rst.getDouble(4);

                        objectBuilder.add("code",code);
                        objectBuilder.add("description",description);
                        objectBuilder.add("qtyOnHand",qtyOnHand);
                        objectBuilder.add("unitPrice",unitPrice);
                        arrayBuilder.add(objectBuilder.build());
                    }
                    response.add("status",200);
                    response.add("message","Done");
                    response.add("data",arrayBuilder.build());
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
