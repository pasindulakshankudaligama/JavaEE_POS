package bo.custom.impl;

import Entity.Customer;
import bo.custom.CustomerBO;
import dao.DAOFactory;
import dao.custom.impl.CustomerDAOImpl;
import dto.CustomerDTO;

import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.sql.SQLException;

public class CustomerBOImpl implements CustomerBO {
    CustomerDAOImpl customerDAO = (CustomerDAOImpl) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
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
    public boolean addCustomer(CustomerDTO customerDTO) throws SQLException {
        Customer customer = new Customer(customerDTO.getId(), customerDTO.getName(), customerDTO.getAddress(), customerDTO.getSalary());
        return customerDAO.add(customer);
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
