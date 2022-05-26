package dao.custom.impl;

import Entity.Customer;
import dao.custom.CustomerDAO;
import servlet.CustomerServlet;
import servlet.OrderPurchaseServlet;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDAOImpl implements CustomerDAO {

    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
    JsonObjectBuilder objectBuilder = Json.createObjectBuilder();

    @Override
    public JsonArrayBuilder getAll() throws SQLException {
        Connection connection = CustomerServlet.ds.getConnection();
        ResultSet rst = connection.prepareStatement("SELECT * FROM customer").executeQuery();
        while (rst.next()) {
            String id = rst.getString(1);
            String name = rst.getString(2);
            String address = rst.getString(3);
            int salary = rst.getInt(4);

            objectBuilder.add("id", id);
            objectBuilder.add("name", name);
            objectBuilder.add("address", address);
            objectBuilder.add("salary", salary);
            arrayBuilder.add(objectBuilder.build());
        }
        connection.close();
        return arrayBuilder;
    }

    @Override
    public JsonObjectBuilder generateID() throws SQLException {
        Connection connection = CustomerServlet.ds.getConnection();
        ResultSet rst2 = connection.prepareStatement("SELECT id FROM customer ORDER BY id DESC LIMIT 1").executeQuery();
        if (rst2.next()) {
            int temp = Integer.parseInt(rst2.getString(1).split("-")[1]);
            temp += 1;
            if (temp < 10) {
                objectBuilder.add("id", "C00-00" + temp);
            } else if (temp < 100) {
                objectBuilder.add("id", "C00-0" + temp);
            } else if (temp < 1000) {
                objectBuilder.add("id", "C00-" + temp);
            }
        } else {
            objectBuilder.add("id", "C00-000");
        }
        connection.close();
        return objectBuilder;
    }

    @Override
    public JsonArrayBuilder search(String id) throws SQLException {
        Connection connection = CustomerServlet.ds.getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT * FROM customer WHERE id LIKE ?");
        pstm.setObject(1, "%" + id + "%");
        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()) {
            String cusIDS = resultSet.getString(1);
            String cusNameS = resultSet.getString(2);
            String cusAddressS = resultSet.getString(3);
            int cusSalaryS = resultSet.getInt(4);

            objectBuilder.add("id", cusIDS);
            objectBuilder.add("name", cusNameS);
            objectBuilder.add("address", cusAddressS);
            objectBuilder.add("salary", cusSalaryS);

            arrayBuilder.add(objectBuilder.build());

            System.out.println(cusIDS + " " + cusNameS + " " + cusAddressS + " " + cusSalaryS);
        }
        connection.close();
        return arrayBuilder;
    }

    @Override
    public boolean add(Customer customer) throws SQLException {
        Connection connection = CustomerServlet.ds.getConnection();
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
        Connection connection = CustomerServlet.ds.getConnection();
        PreparedStatement pstm = connection.prepareStatement("DELETE FROM customer WHERE id=?");
        pstm.setObject(1, id);
        boolean b = pstm.executeUpdate() > 0;
        connection.close();
        return b;
    }

    @Override
    public boolean update(Customer customer) throws SQLException {
        Connection connection = CustomerServlet.ds.getConnection();
        PreparedStatement pstm = connection.prepareStatement("UPDATE customer SET name=?, address=?, salary=? WHERE id=?");
        pstm.setObject(1, customer.getName());
        pstm.setObject(2, customer.getAddress());
        pstm.setObject(3, customer.getSalary());
        pstm.setObject(4, customer.getId());
        boolean b = pstm.executeUpdate() > 0;
        connection.close();
        return b;
    }

    @Override
    public JsonArrayBuilder loadCusId() throws SQLException {
        Connection connection = OrderPurchaseServlet.ds.getConnection();
        ResultSet rst = connection.prepareStatement("SELECT id FROM customer").executeQuery();
        while (rst.next()) {
            String id = rst.getString(1);
            objectBuilder.add("id", id);
            arrayBuilder.add(objectBuilder.build());
        }
        connection.close();
        return arrayBuilder;
    }

    @Override
    public JsonArrayBuilder loadSelectCusDetails(String id) throws SQLException {
        Connection connection = OrderPurchaseServlet.ds.getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT * FROM customer WHERE id=?");
        pstm.setObject(1, id);
        ResultSet rst = pstm.executeQuery();
        if (rst.next()) {
            String name = rst.getString(2);
            String address = rst.getString(3);
            String salary = rst.getString(4);
            objectBuilder.add("name", name);
            objectBuilder.add("address", address);
            objectBuilder.add("salary", salary);
            arrayBuilder.add(objectBuilder.build());
        }
        connection.close();
        return arrayBuilder;
    }
}
