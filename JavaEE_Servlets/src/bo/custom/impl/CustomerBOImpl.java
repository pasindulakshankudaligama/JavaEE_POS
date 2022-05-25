package bo.custom.impl;

import bo.custom.CustomerBO;
import dto.CustomerDTO;

import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

public class CustomerBOImpl implements CustomerBO {
    @Override
    public JsonArrayBuilder getAllCustomer() {
        return null;
    }

    @Override
    public JsonObjectBuilder generateCustomerID() {
        return null;
    }

    @Override
    public JsonArrayBuilder searchCustomer(String id) {
        return null;
    }

    @Override
    public boolean addCustomer(CustomerDTO customerDTO) {
        return false;
    }

    @Override
    public boolean deleteCustomer(String id) {
        return false;
    }

    @Override
    public boolean updateCustomer(CustomerDTO customerDTO) {
        return false;
    }
}
