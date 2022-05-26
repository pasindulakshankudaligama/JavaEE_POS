package dao.custom;

import Entity.Customer;
import dao.CrudDAO;

import javax.json.JsonArrayBuilder;
import java.sql.SQLException;

public interface CustomerDAO extends CrudDAO<Customer,String> {
    JsonArrayBuilder loadCusId() throws SQLException;

    JsonArrayBuilder loadSelectCusDetails(String id) throws SQLException;
}
