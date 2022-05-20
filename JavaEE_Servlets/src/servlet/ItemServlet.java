package servlet;

import javax.annotation.Resource;
import javax.json.*;
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

@WebServlet(urlPatterns = "/item")
public class ItemServlet extends HttpServlet {

    @Resource(name = "java:comp/env/jdbc/pool")
    DataSource ds;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String code = req.getParameter("code");
        String description = req.getParameter("description");
        String qtyOnHand = req.getParameter("qtyOnHand");
        String unitPrice = req.getParameter("unitPrice");

        PrintWriter writer = resp.getWriter();
        Connection connection = null;

        try {
            connection = ds.getConnection();
            PreparedStatement pstm = connection.prepareStatement("INSERT INTO item VALUES(?,?,?,?)");
            pstm.setObject(1, code);
            pstm.setObject(2, description);
            pstm.setObject(3, qtyOnHand);
            pstm.setObject(4, unitPrice);

            if (pstm.executeUpdate() > 0) {
                JsonObjectBuilder response = Json.createObjectBuilder();
                resp.setStatus(HttpServletResponse.SC_CREATED);
                response.add("status", 200);
                response.add("message", "Successfully added");
                response.add("data", "");
                writer.print(response.build());

            }

        } catch (SQLException e) {
            JsonObjectBuilder response = Json.createObjectBuilder();
            resp.setStatus(HttpServletResponse.SC_OK);
            response.add("status", 400);
            response.add("message", "error");
            response.add("data", e.getLocalizedMessage());
            writer.print(response.build());
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

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
            connection = ds.getConnection();

            switch (option) {
                case "GETALL":
                    ResultSet rst = connection.prepareStatement("SELECT * FROM item").executeQuery();
                    while (rst.next()) {
                        String code = rst.getString(1);
                        String description = rst.getString(2);
                        int qtyOnHand = rst.getInt(3);
                        double unitPrice = rst.getDouble(4);

                        objectBuilder.add("code", code);
                        objectBuilder.add("description", description);
                        objectBuilder.add("qtyOnHand", qtyOnHand);
                        objectBuilder.add("unitPrice", unitPrice);
                        arrayBuilder.add(objectBuilder.build());
                    }
                    response.add("status", 200);
                    response.add("message", "Done");
                    response.add("data", arrayBuilder.build());
                    writer.print(response.build());
                    break;

                case "GENID":
                    ResultSet rst2 = connection.prepareStatement("SELECT code FROM item ORDER BY code DESC LIMIT 1").executeQuery();
                    if (rst2.next()) {
                        int temp = Integer.parseInt(rst2.getString(1).split("-")[1]);
                        temp += 1;
                        if (temp < 10) {
                            objectBuilder.add("code", "I00-00" + temp);
                        } else if (temp < 100) {
                            objectBuilder.add("code", "I00-0" + temp);
                        } else if (temp < 1000) {
                            objectBuilder.add("code", "I00-" + temp);
                        } else {
                            objectBuilder.add("code", "I00-000");
                        }
                        response.add("data", objectBuilder.build());
                        response.add("message", "Done");
                        response.add("status", 200);
                        writer.print(response.build());
                        break;
                    }
                case "SEARCH":
                    String code = req.getParameter("code");
                    PreparedStatement pstm = connection.prepareStatement("SELECT * FROM item WHERE code LIKE ?");
                    pstm.setObject(1, "%" + code + "%");
                    ResultSet resultSet = pstm.executeQuery();

                    while (resultSet.next()) {
                        String itemCodes = resultSet.getString(1);
                        String itemDescriptions = resultSet.getString(2);
                        String itemQtyOnHands = resultSet.getString(3);
                        String itemUnitPrices = resultSet.getString(4);

                        resp.setStatus(HttpServletResponse.SC_OK);

                        objectBuilder.add("code", itemCodes);
                        objectBuilder.add("description", itemDescriptions);
                        objectBuilder.add("qtyOnHand", itemQtyOnHands);
                        objectBuilder.add("unitPrice", itemUnitPrices);
                        arrayBuilder.add(objectBuilder.build());
                    }
                    response.add("data", arrayBuilder.build());
                    response.add("massage", "Done");
                    response.add("status", "200");

                    writer.print(response.build());
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();

        String itemCodeUpdate = jsonObject.getString("code");
        String itemDesUpdate = jsonObject.getString("description");
        String itemQTYUpdate = jsonObject.getString("qtyOnHand");
        String itemUnitPriceUpdate = jsonObject.getString("unitPrice");
        PrintWriter writer = resp.getWriter();
        Connection connection = null;

        try {
            connection = ds.getConnection();
            PreparedStatement pstm = connection.prepareStatement("UPDATE item SET description=?, qtyOnHand=?, unitPrice=? WHERE code=?");
            pstm.setObject(1,itemDesUpdate);
            pstm.setObject(2,itemQTYUpdate);
            pstm.setObject(3,itemUnitPriceUpdate);
            pstm.setObject(4,itemCodeUpdate);

            if (pstm.executeUpdate() > 0) {
                JsonObjectBuilder response = Json.createObjectBuilder();
                resp.setStatus(HttpServletResponse.SC_CREATED);//201
                response.add("status", 200);
                response.add("message", "Successfully Updated");
                response.add("data", "");
                writer.print(response.build());
            }

        } catch (SQLException e) {
            JsonObjectBuilder response = Json.createObjectBuilder();
            response.add("status", 400);
            response.add("message", "Error");
            response.add("data", e.getLocalizedMessage());
            writer.print(response.build());
            resp.setStatus(HttpServletResponse.SC_OK); //200
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
