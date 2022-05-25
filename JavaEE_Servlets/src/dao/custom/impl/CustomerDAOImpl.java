package dao.custom.impl;

import Entity.Customer;
import dao.custom.CustomerDAO;

import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static servlet.CustomerServlet.ds;

public class CustomerDAOImpl implements CustomerDAO {
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
    public boolean add(Customer customer) throws SQLException {
        Connection connection = ds.getConnection();
        PreparedStatement pstm = connection.prepareStatement("INSERT INTO customer VALUES(?,?,?,?)");
        pstm.setObject(1, customer.getId());
        pstm.setObject(2, customer.getName());
        pstm.setObject(3, customer.getAddress());
        pstm.setObject(4, customer.getSalary());
        boolean b = pstm.executeUpdate() > 0;
        connection.close();
        return b;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        Connection connection = ds.getConnection();
        PreparedStatement pstm = connection.prepareStatement("DELETE FROM customer WHERE id=?");
        pstm.setObject(1, id);
        boolean b = pstm.executeUpdate() > 0;
        connection.close();
        return b;
    }

    @Override
    public boolean update(Customer customer) throws SQLException {
        Connection connection = ds.getConnection();
        PreparedStatement pstm = connection.prepareStatement("UPDATE customer SET name=?, address=?, salary=? WHERE id=?");
        pstm.setObject(1, customer.getName());
        pstm.setObject(2, customer.getAddress());
        pstm.setObject(3, customer.getSalary());
        pstm.setObject(4, customer.getId());
        boolean b = pstm.executeUpdate() > 0;
        connection.close();
        return b;
    }
}
