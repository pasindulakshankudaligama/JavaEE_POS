package bo.custom;

import bo.SuperBO;
import dto.ItemDTO;

import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

public interface ItemBO extends SuperBO {
    JsonArrayBuilder getAllItems();

    JsonObjectBuilder generateItemID();

    JsonArrayBuilder searchItem(String id);

    boolean addItem(ItemDTO itemDTO);

    boolean deleteItem(String id);

    boolean updateItem(ItemDTO itemDTO);
}
