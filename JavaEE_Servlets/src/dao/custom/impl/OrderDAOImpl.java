package dao.custom.impl;

import Entity.Orders;
import dao.custom.OrderDAO;

import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

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
    public boolean add(Orders orders) {
        return false;
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
