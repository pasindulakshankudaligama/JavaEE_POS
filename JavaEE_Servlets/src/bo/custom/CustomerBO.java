package bo.custom;

import bo.SuperBO;
import dto.CustomerDTO;

import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

public interface CustomerBO extends SuperBO {
    JsonArrayBuilder getAllCustomer();

    JsonObjectBuilder generateCustomerID();

    JsonArrayBuilder searchCustomer(String id);

    boolean addCustomer(CustomerDTO customerDTO);

    boolean deleteCustomer(String id);

    boolean updateCustomer(CustomerDTO customerDTO);
}
