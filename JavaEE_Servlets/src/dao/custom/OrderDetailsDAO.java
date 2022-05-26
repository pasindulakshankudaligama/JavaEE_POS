package dao.custom;

import Entity.OrderDetails;
import dao.CrudDAO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDetailsDAO extends CrudDAO<OrderDetails, String> {
    boolean saveOrderDetails(String id, ArrayList<OrderDetails> dtos) throws SQLException;
}
