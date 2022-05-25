package dao.custom.impl;

import Entity.OrderDetails;
import dao.custom.OrderDetailsDAO;

import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

public class OrderDetailsDAOImpl implements OrderDetailsDAO {
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
}
