package dao.custom.impl;

import Entity.Item;
import dao.custom.ItemDAO;
import servlet.ItemServlet;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ItemDAOImpl implements ItemDAO {

    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
    JsonObjectBuilder objectBuilder = Json.createObjectBuilder();

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
    public boolean add(Item item) throws SQLException {
        Connection connection = ItemServlet.ds.getConnection();
        PreparedStatement pstm = connection.prepareStatement("INSERT INTO item VALUES(?,?,?,?)");
        pstm.setObject(1, item.getCode());
        pstm.setObject(2, item.getDescription());
        pstm.setObject(3, item.getQtyOnHand());
        pstm.setObject(4, item.getUnitPrice());
        boolean b = pstm.executeUpdate() > 0;
        connection.close();
        return b;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        Connection connection = ItemServlet.ds.getConnection();
        PreparedStatement pstm = connection.prepareStatement("DELETE FROM item WHERE code=?");
        pstm.setObject(1, id);
        boolean b = pstm.executeUpdate() > 0;
        connection.close();
        return b;
    }

    @Override
    public boolean update(Item item) throws SQLException {
        Connection connection = ItemServlet.ds.getConnection();
        PreparedStatement pstm = connection.prepareStatement("UPDATE item SET description=?, qtyOnHand=?, unitPrice=? WHERE code=?");
        pstm.setObject(1, item.getDescription());
        pstm.setObject(2, item.getQtyOnHand());
        pstm.setObject(3, item.getUnitPrice());
        pstm.setObject(4, item.getCode());
        boolean b = pstm.executeUpdate() > 0;
        connection.close();
        return b;
    }
}
