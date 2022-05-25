package bo.custom;

import bo.SuperBO;
import dto.ItemDTO;

import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.sql.SQLException;

public interface ItemBO extends SuperBO {
    JsonArrayBuilder getAllItems();

    JsonObjectBuilder generateItemID();

    JsonArrayBuilder searchItem(String id);

    boolean addItem(ItemDTO itemDTO) throws SQLException;

    boolean deleteItem(String id) throws SQLException;

    boolean updateItem(ItemDTO itemDTO);
}
