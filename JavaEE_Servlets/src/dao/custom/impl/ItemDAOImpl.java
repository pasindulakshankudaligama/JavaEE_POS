package dao.custom.impl;

import Entity.Item;
import dao.custom.ItemDAO;
import servlet.ItemServlet;
import servlet.OrderPurchaseServlet;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemDAOImpl implements ItemDAO {

    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
    JsonObjectBuilder objectBuilder = Json.createObjectBuilder();

    @Override
    public JsonArrayBuilder getAll() throws SQLException {
        Connection connection = ItemServlet.ds.getConnection();
        ResultSet rst = connection.prepareStatement("SELECT * FROM item").executeQuery();
        while (rst.next()) {
            String code = rst.getString(1);
            String description = rst.getString(2);
            int qtyOnHand = rst.getInt(3);
            int unitPrice = rst.getInt(4);

            objectBuilder.add("code", code);
            objectBuilder.add("description", description);
            objectBuilder.add("qtyOnHand", qtyOnHand);
            objectBuilder.add("unitPrice", unitPrice);
            arrayBuilder.add(objectBuilder.build());
        }
        connection.close();
        return arrayBuilder;
    }

    @Override
    public JsonObjectBuilder generateID() throws SQLException {
        Connection connection = ItemServlet.ds.getConnection();
        ResultSet rst2 = connection.prepareStatement("SELECT code FROM item ORDER BY code DESC LIMIT 1").executeQuery();
        if (rst2.next()) {
            int temp = Integer.parseInt(rst2.getString(1).split("-")[1]);
            System.out.println(temp);
            temp += 1;
            if (temp < 10) {
                objectBuilder.add("code", "I00-00" + temp);
            } else if (temp < 100) {
                objectBuilder.add("code", "I00-0" + temp);
            } else if (temp < 1000) {
                objectBuilder.add("code", "I00-" + temp);
            }

        } else {
            objectBuilder.add("code", "I00-000");
        }
        connection.close();
        return objectBuilder;
    }

    @Override
    public JsonArrayBuilder search(String id) throws SQLException {
        Connection connection = ItemServlet.ds.getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT * FROM item WHERE code LIKE ?");
        pstm.setObject(1, "%" + id + "%");
        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()) {
            String itemCodes = resultSet.getString(1);
            String itemDescriptions = resultSet.getString(2);
            int itemQtyOnHands = resultSet.getInt(3);
            int itemUnitPrices = resultSet.getInt(4);

            objectBuilder.add("code", itemCodes);
            objectBuilder.add("description", itemDescriptions);
            objectBuilder.add("qtyOnHand", itemQtyOnHands);
            objectBuilder.add("unitPrice", itemUnitPrices);
            arrayBuilder.add(objectBuilder.build());
        }
        connection.close();
        return arrayBuilder;
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

    @Override
    public JsonArrayBuilder loadItemId() throws SQLException {
        Connection connection = OrderPurchaseServlet.ds.getConnection();
        ResultSet rst = connection.prepareStatement("SELECT code FROM item").executeQuery();
        while (rst.next()) {
            String code = rst.getString(1);
            objectBuilder.add("code", code);
            arrayBuilder.add(objectBuilder.build());
        }
        connection.close();
        return arrayBuilder;
    }

    @Override
    public JsonArrayBuilder loadSelectItemDetails(String id) throws SQLException {
        Connection connection = OrderPurchaseServlet.ds.getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT * FROM item WHERE code=?");
        pstm.setObject(1, id);
        ResultSet rst = pstm.executeQuery();
        if (rst.next()) {
            String itemName = rst.getString(2);
            String qtyOnHand = rst.getString(3);
            String unitPrice = rst.getString(4);
            objectBuilder.add("itemName", itemName);
            objectBuilder.add("qtyOnHand", qtyOnHand);
            objectBuilder.add("unitPrice", unitPrice);
            arrayBuilder.add(objectBuilder.build());
        }
        connection.close();
        return arrayBuilder;
    }
}
