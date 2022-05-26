package dao.custom.impl;

import Entity.Orders;
import dao.custom.OrderDAO;
import servlet.OrderPurchaseServlet;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderDAOImpl implements OrderDAO {
    @Override
    public JsonArrayBuilder getAll() {
        return null;
    }

    @Override
    public JsonObjectBuilder generateID() throws SQLException {
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        Connection connection = OrderPurchaseServlet.ds.getConnection();
        ResultSet rst = connection.prepareStatement("SELECT oid FROM orders ORDER BY oid DESC LIMIT 1").executeQuery();
        if (rst.next()) {
            int tempId = Integer.parseInt(rst.getString(1).split("-")[1]);
            tempId += 1;
            if (tempId < 10) {
                objectBuilder.add("oId", "O00-00" + tempId);
            } else if (tempId < 100) {
                objectBuilder.add("oId", "O00-0" + tempId);
            } else if (tempId < 1000) {
                objectBuilder.add("oId", "O00-" + tempId);
            }
        } else {
            objectBuilder.add("oId", "O00-000");
        }
        connection.close();
        return objectBuilder;
    }

    @Override
    public JsonArrayBuilder search(String id) {
        return null;
    }

    @Override
    public boolean add(Orders orders) throws SQLException {
        Connection connection = OrderPurchaseServlet.ds.getConnection();
        connection.setAutoCommit(false);
        PreparedStatement pstm = connection.prepareStatement("INSERT INTO orders VALUES(?,?,?,?,?,?)");
        pstm.setObject(1, orders.getOid());
        pstm.setObject(2, orders.getDate());
        pstm.setObject(3, orders.getCustomerID());
        pstm.setObject(4, orders.getDiscount());
        pstm.setObject(5, orders.getTotal());
        pstm.setObject(6, orders.getSubtotal());

        boolean b = pstm.executeUpdate() > 0;
        connection.close();
        return b;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public boolean update(Orders orders) {
        return false;
    }
}
