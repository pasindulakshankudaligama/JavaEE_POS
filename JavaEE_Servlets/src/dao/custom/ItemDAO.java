package dao.custom;

import Entity.Item;
import dao.CrudDAO;

import javax.json.JsonArrayBuilder;
import java.sql.SQLException;

public interface ItemDAO extends CrudDAO<Item, String> {
    JsonArrayBuilder loadItemId() throws SQLException;

    JsonArrayBuilder loadSelectItemDetails(String id);

}
