package dao.custom.impl;

import Entity.Orders;
import dao.custom.OrderDAO;
import servlet.OrderPurchaseServlet;

import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderDAOImpl implements OrderDAO {
    @Override
    public JsonArrayBuilder getAll() {
        return null;
    }

    @Override
    public JsonObjectBuilder generateID() {
        return null;
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
