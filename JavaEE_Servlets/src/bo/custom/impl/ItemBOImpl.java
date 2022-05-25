package bo.custom.impl;

import Entity.Item;
import bo.custom.ItemBO;
import dao.DAOFactory;
import dao.custom.impl.ItemDAOImpl;
import dto.ItemDTO;

import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.sql.SQLException;

public class ItemBOImpl implements ItemBO {

    ItemDAOImpl itemDAO = (ItemDAOImpl) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ITEM);

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
    public boolean addItem(ItemDTO itemDTO) throws SQLException {
        Item item = new Item(itemDTO.getCode(), itemDTO.getDescription(), itemDTO.getQtyOnHand(), itemDTO.getUnitPrice());
        return itemDAO.add(item);
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
