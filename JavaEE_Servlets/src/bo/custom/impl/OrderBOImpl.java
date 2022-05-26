package bo.custom.impl;

import Entity.Orders;
import bo.custom.OrderBO;
import dao.DAOFactory;
import dao.custom.impl.OrderDAOImpl;
import dao.custom.impl.OrderDetailsDAOImpl;
import dto.OrderDTO;
import servlet.OrderPurchaseServlet;

import java.sql.Connection;
import java.sql.SQLException;

public class OrderBOImpl implements OrderBO {

    OrderDAOImpl orderDAO = (OrderDAOImpl) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    OrderDetailsDAOImpl orderDetailsDAO = (OrderDetailsDAOImpl) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDER_DETAILS);

    @Override
    public boolean addOrder(OrderDTO ordersDTO) {
        Connection connection = null;
        try {
            connection = OrderPurchaseServlet.ds.getConnection();
            connection.setAutoCommit(false);

            if (orderDAO.add(new Orders(ordersDTO.getOid(),ordersDTO.getDate(),ordersDTO.getCustomerID(),ordersDTO.getDiscount(),ordersDTO.getTotal(),ordersDTO.getSubtotal()))) {
                if (orderDetailsDAO.saveOrderDetails(ordersDTO.getOid(),ordersDTO.getOrderDetailsArrayList())) {
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
}
