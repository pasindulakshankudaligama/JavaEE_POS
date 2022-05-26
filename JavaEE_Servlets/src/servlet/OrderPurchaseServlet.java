package servlet;

import bo.BOFactory;
import bo.custom.impl.CustomerBOImpl;
import bo.custom.impl.ItemBOImpl;
import bo.custom.impl.OrderBOImpl;

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

@WebServlet(urlPatterns = "/order")
public class OrderPurchaseServlet extends HttpServlet {
    @Resource(name = "java:comp/env/jdbc/pool")
    public static DataSource ds;
    Connection connection = null;

    CustomerBOImpl customerBO = (CustomerBOImpl) BOFactory.getBoFactory().getBO(BOFactory.BoTypes.CUSTOMER);
    ItemBOImpl itemBO = (ItemBOImpl) BOFactory.getBoFactory().getBO(BOFactory.BoTypes.ITEM);
    OrderBOImpl ordersBO = (OrderBOImpl) BOFactory.getBoFactory().getBO(BOFactory.BoTypes.ORDER);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        JsonObjectBuilder response = Json.createObjectBuilder();
        PrintWriter writer = resp.getWriter();

        try {
           /* connection = ds.getConnection();
            ResultSet rst;
            PreparedStatement pstm;*/
            String option = req.getParameter("option");
            System.out.println(option);
            switch (option) {
                case "LOADCUSIDS":
                  /*  rst = connection.prepareStatement("SELECT id FROM customer").executeQuery();
                    while (rst.next()) {
                        String id = rst.getString(1);
                        objectBuilder.add("id", id);
                        arrayBuilder.add(objectBuilder.build());
                    }*/
                    response.add("data", customerBO.loadAllCusIDs());
                    response.add("message", "Done");
                    response.add("status", 200);
                    writer.print(response.build());
                    break;

                case "LOADITEMIDS":
                  /*  rst = connection.prepareStatement("SELECT code FROM item").executeQuery();
                    while (rst.next()) {
                        String code = rst.getString(1);
                        objectBuilder.add("code", code);
                        arrayBuilder.add(objectBuilder.build());
                    }*/
                    response.add("data", itemBO.loadAllItemIDs());
                    response.add("message", "Done");
                    response.add("status", 200);
                    writer.print(response.build());
                    break;

                case "SELECTEDCUSDETAILS":
                    String cusId = req.getParameter("cusId");
                    /*pstm = connection.prepareStatement("SELECT * FROM customer WHERE id=?");
                    pstm.setObject(1, cusId);
                     rst = pstm.executeQuery();
                    if (rst.next()) {
                        String name = rst.getString(2);
                        String address = rst.getString(3);
                        String salary = rst.getString(4);
                        objectBuilder.add("name", name);
                        objectBuilder.add("address", address);
                        objectBuilder.add("salary", salary);
                        arrayBuilder.add(objectBuilder.build());
                    }*/
                    response.add("data", customerBO.loadSelectedCusData(cusId));
                    response.add("message", "Done");
                    response.add("status", 200);
                    writer.print(response.build());
                    break;

                case "SELECTEDITEMDETAILS":
                    String itemCode = req.getParameter("itemCode");
                    /*pstm = connection.prepareStatement("SELECT * FROM item WHERE code=?");
                    pstm.setObject(1, itemCode);
                    rst = pstm.executeQuery();
                    if (rst.next()) {
                        String itemName = rst.getString(2);
                        String qtyOnHand = rst.getString(3);
                        String unitPrice = rst.getString(4);
                        objectBuilder.add("itemName", itemName);
                        objectBuilder.add("qtyOnHand", qtyOnHand);
                        objectBuilder.add("unitPrice", unitPrice);
                        arrayBuilder.add(objectBuilder.build());
                    }*/
                    response.add("data", itemBO.loadSelectedItemData(itemCode));
                    response.add("message", "Done");
                    response.add("status", 200);
                    writer.print(response.build());
                    break;
            }

        } catch (SQLException e) {
            response.add("data", e.getLocalizedMessage());
            response.add("message", "error");
            response.add("status", 400);
            writer.print(response.build());
        } /*finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }*/

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();
        System.out.println(jsonObject);

        JsonObject order = jsonObject.getJsonObject("order");
        System.out.println(order);
        JsonArray orderDetail = jsonObject.getJsonArray("orderDetail");

        System.out.println("before if");
        JsonObjectBuilder response = Json.createObjectBuilder();
        if (saveOrder(order, orderDetail)) {
            System.out.println("true");
            resp.setStatus(HttpServletResponse.SC_CREATED);//201
            response.add("status", 200);
            response.add("message", "Order Successful");
            response.add("data", "");
        } else {
            System.out.println("false");
            resp.setStatus(HttpServletResponse.SC_OK);//201
            response.add("status", 400);
            response.add("message", "Order Not Successful");
            response.add("data", "");
        }
        writer.print(response.build());
    }
    public boolean saveOrder(JsonObject order, JsonArray orderDetail) {

        try {
            connection = ds.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement pstm = connection.prepareStatement("INSERT INTO orders VALUES(?,?,?,?,?,?)");
            pstm.setObject(1, order.getString("orderId"));
            pstm.setObject(2, order.getString("orderDate"));
            pstm.setObject(3, order.getString("customer"));
            pstm.setObject(4, order.getInt("discount"));
            pstm.setObject(5, order.getString("total"));
            pstm.setObject(6, order.getString("subTotal"));

            if (pstm.executeUpdate() > 0) {
                if (saveOrderDetails(order.getString("orderId"), orderDetail)) {
                    connection.commit();
                    return true;
                } else {
                    connection.rollback();
                    return false;
                }
            } else {
                connection.rollback();
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    public boolean saveOrderDetails(String oid, JsonArray orderDetail) throws SQLException {
        for (JsonValue orderDetails : orderDetail) {
            JsonObject orderDetailJsonObj = orderDetails.asJsonObject();
            PreparedStatement pstm = connection.prepareStatement("INSERT INTO orderdetails VALUES(?,?,?,?,?)");
            pstm.setObject(1, oid);
            pstm.setObject(2, orderDetailJsonObj.getString("itemCode"));
            pstm.setObject(3, orderDetailJsonObj.getString("itemQty"));
            pstm.setObject(4, orderDetailJsonObj.getString("itemPrice"));
            pstm.setObject(5, orderDetailJsonObj.getString("itemTotal"));


            if (pstm.executeUpdate() > 0) {
                if (updateItem(orderDetailJsonObj.getString("itemCode"), orderDetailJsonObj.getString("itemQty"))) {

                } else {
                    return false;
                }
            } else {
                return false;
            }

        }
        return true;
    }
    public boolean updateItem(String ItemCode, String itemQty) throws SQLException {
        PreparedStatement pstm = connection.prepareStatement("UPDATE item SET qtyOnHand=(qtyOnHand-?) WHERE code=?");
        pstm.setObject(1, itemQty);
        pstm.setObject(2, ItemCode);
        return pstm.executeUpdate() > 0;
    }
}
