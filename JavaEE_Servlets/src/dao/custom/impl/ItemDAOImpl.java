package dao.custom.impl;

import Entity.Item;
import dao.custom.ItemDAO;

import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

public class ItemDAOImpl implements ItemDAO {
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
    public boolean add(Item item) {
        return false;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public boolean update(Item item) {
        return false;
    }
}
