package bo.custom.impl;

import bo.custom.ItemBO;
import dto.ItemDTO;

import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

public class ItemBOImpl implements ItemBO {
    @Override
    public JsonArrayBuilder getAllItems() {
        return null;
    }

    @Override
    public JsonObjectBuilder generateItemID() {
        return null;
    }

    @Override
    public JsonArrayBuilder searchItem(String id) {
        return null;
    }

    @Override
    public boolean addItem(ItemDTO itemDTO) {
        return false;
    }

    @Override
    public boolean deleteItem(String id) {
        return false;
    }

    @Override
    public boolean updateItem(ItemDTO itemDTO) {
        return false;
    }
}
