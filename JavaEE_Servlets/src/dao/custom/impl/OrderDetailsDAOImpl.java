package dao.custom.impl;

import Entity.OrderDetails;
import dao.DAOFactory;
import dao.custom.ItemDAO;
import dao.custom.OrderDetailsDAO;
import servlet.OrderPurchaseServlet;

import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsDAOImpl implements OrderDetailsDAO {
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ITEM);
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
    public boolean add(OrderDetails orderDetails) {
   return false;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public boolean update(OrderDetails orderDetails) {
        return false;
    }

    @Override
    public boolean saveOrderDetails(String id, ArrayList<OrderDetails> dtos) throws SQLException {
        for (OrderDetails items : dtos) {
            Connection connection = OrderPurchaseServlet.ds.getConnection();
            PreparedStatement pstm = connection.prepareStatement("INSERT INTO orderdetails VALUES(?,?,?,?,?)");
            pstm.setObject(1, items.getOid());
            pstm.setObject(2, items.getItemCode());
            pstm.setObject(3, items.getQty());
            pstm.setObject(4, items.getUnitPrice());
            pstm.setObject(5, items.getTotal());

            if (pstm.executeUpdate() > 0) {
                if (itemDAO.updateQty(items.getItemCode(),items.getQty())) {

                } else {
                    connection.close();
                    return false;
                }
            } else {
                connection.close();
                return false;
            }

        }
        return true;
    }

}
