package dao.custom.impl;

import Entity.Customer;
import dao.custom.CustomerDAO;

import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

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
    public boolean add(Customer customer) {
        return false;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public boolean update(Customer customer) {
        return false;
    }
}
