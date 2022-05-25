package dao;

import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.sql.SQLException;

public interface CrudDAO<S, Id> extends SuperDAO {

    JsonArrayBuilder getAll();

    JsonObjectBuilder generateID();

    JsonArrayBuilder search(String id);

    boolean add(S s) throws SQLException;

    boolean delete(String id);

    boolean update(S s);
}
