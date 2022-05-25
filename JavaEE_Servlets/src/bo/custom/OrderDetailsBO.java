package bo.custom;

import bo.SuperBO;

import javax.json.JsonArrayBuilder;

public interface OrderDetailsBO extends SuperBO {
    JsonArrayBuilder fetchAllOrderDetails();
}
