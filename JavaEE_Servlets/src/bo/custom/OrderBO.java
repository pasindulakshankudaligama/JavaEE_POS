package bo.custom;

import bo.SuperBO;
import dto.OrderDTO;

import javax.json.JsonObjectBuilder;
import java.sql.SQLException;

public interface OrderBO extends SuperBO {
    boolean addOrder(OrderDTO ordersDTO);

    JsonObjectBuilder generateOrderID() throws SQLException;
}
