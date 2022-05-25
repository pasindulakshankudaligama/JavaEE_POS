package bo.custom;

import bo.SuperBO;
import dto.CustomerDTO;

import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.sql.SQLException;

public interface CustomerBO extends SuperBO {
    JsonArrayBuilder getAllCustomer() throws SQLException;

    JsonObjectBuilder generateCustomerID();

    JsonArrayBuilder searchCustomer(String id);

    boolean addCustomer(CustomerDTO customerDTO) throws SQLException;

    boolean deleteCustomer(String id) throws SQLException;

    boolean updateCustomer(CustomerDTO customerDTO) throws SQLException;
}
