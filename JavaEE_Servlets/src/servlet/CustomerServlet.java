package servlet;

import bo.BOFactory;
import bo.custom.impl.CustomerBOImpl;
import dto.CustomerDTO;

import javax.annotation.Resource;
import javax.json.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/customer")
public class CustomerServlet extends HttpServlet {

    @Resource(name = "java:comp/env/jdbc/pool")
    public static DataSource ds;

    CustomerBOImpl customerBO = (CustomerBOImpl) BOFactory.getBoFactory().getBO(BOFactory.BoTypes.CUSTOMER);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String address = req.getParameter("address");
        String salary = req.getParameter("salary");
        CustomerDTO customerDTO = new CustomerDTO(id, name, address, Integer.parseInt(salary));
        PrintWriter writer = resp.getWriter();
//        Connection connection = null;

        try {
            if (customerBO.addCustomer(customerDTO)) {
                JsonObjectBuilder response = Json.createObjectBuilder();
                resp.setStatus(HttpServletResponse.SC_CREATED);
                response.add("status", 200);
                response.add("message", "Successfully added");
                response.add("data", "");
                writer.print(response.build());
            }

        } catch (SQLException e) {
            JsonObjectBuilder response = Json.createObjectBuilder();
            resp.setStatus(HttpServletResponse.SC_OK);
            response.add("status", 400);
            response.add("message", "error");
            response.add("data", e.getLocalizedMessage());
            writer.print(response.build());
            e.printStackTrace();
        } /*finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }*/

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder(); // json array
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        JsonObjectBuilder response = Json.createObjectBuilder();
        PrintWriter writer = resp.getWriter();
        Connection connection = null;
        try {
            String option = req.getParameter("option");
            connection = ds.getConnection();

            switch (option) {
                case "GETALL":
                   /* ResultSet rst = connection.prepareStatement("SELECT * FROM customer").executeQuery();
                    while (rst.next()) {
                        String id = rst.getString(1);
                        String name = rst.getString(2);
                        String address = rst.getString(3);
                        int salary = rst.getInt(4);

                        objectBuilder.add("id", id);
                        objectBuilder.add("name", name);
                        objectBuilder.add("address", address);
                        objectBuilder.add("salary", salary);
                        arrayBuilder.add(objectBuilder.build());
                    }*/
                    response.add("data", customerBO.getAllCustomer());
                    response.add("status", 200);
                    response.add("message", "Done");
                    writer.print(response.build());
                    break;

                case "GENID":
                    ResultSet rst2 = connection.prepareStatement("SELECT id FROM customer ORDER BY id DESC LIMIT 1").executeQuery();
                    if (rst2.next()) {
                        int temp = Integer.parseInt(rst2.getString(1).split("-")[1]);
                        temp += 1;
                        if (temp < 10) {
                            objectBuilder.add("id", "C00-00" + temp);
                        } else if (temp < 100) {
                            objectBuilder.add("id", "C00-0" + temp);
                        } else if (temp < 1000) {
                            objectBuilder.add("id", "C00-" + temp);
                        }
                    } else {
                        objectBuilder.add("id", "C00-000");
                    }
                    response.add("data", objectBuilder.build());
                    response.add("message", "Done");
                    response.add("status", 200);
                    writer.print(response.build());
                    break;

                case "SEARCH":
                    String id = req.getParameter("id");
                    PreparedStatement pstm = connection.prepareStatement("SELECT * FROM customer WHERE id LIKE ?");
                    pstm.setObject(1, "%" + id + "%");
                    ResultSet resultSet = pstm.executeQuery();

                    while (resultSet.next()) {
                        String cusIDS = resultSet.getString(1);
                        String cusNameS = resultSet.getString(2);
                        String cusAddressS = resultSet.getString(3);
                        int cusSalaryS = resultSet.getInt(4);

                        resp.setStatus(HttpServletResponse.SC_OK);//201

                        objectBuilder.add("id", cusIDS);
                        objectBuilder.add("name", cusNameS);
                        objectBuilder.add("address", cusAddressS);
                        objectBuilder.add("salary", cusSalaryS);

                        arrayBuilder.add(objectBuilder.build());

                        System.out.println(cusIDS + " " + cusNameS + " " + cusAddressS + " " + cusSalaryS);
                    }
                    response.add("data", arrayBuilder.build());
                    response.add("massage", "Done");
                    response.add("status", "200");

                    writer.print(response.build());
                    break;

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        //we have to get updated data from JSON format
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();

        String cusIDUpdate = jsonObject.getString("id");
        String cusNameUpdate = jsonObject.getString("name");
        String cusAddressUpdate = jsonObject.getString("address");
        String cusSalaryUpdate = jsonObject.getString("salary");
        CustomerDTO customerDTO = new CustomerDTO(cusIDUpdate, cusNameUpdate, cusAddressUpdate, Integer.parseInt(cusSalaryUpdate));
        PrintWriter writer = resp.getWriter();

//        Connection connection = null;
        try {
          /*  connection = ds.getConnection();
            PreparedStatement pstm = connection.prepareStatement("UPDATE customer SET name=?, address=?, salary=? WHERE id=?");
            pstm.setObject(1, cusNameUpdate);
            pstm.setObject(2, cusAddressUpdate);
            pstm.setObject(3, cusSalaryUpdate);
            pstm.setObject(4, cusIDUpdate);*/

            if (customerBO.updateCustomer(customerDTO)) {
                JsonObjectBuilder response = Json.createObjectBuilder();
                resp.setStatus(HttpServletResponse.SC_CREATED);//201
                response.add("status", 200);
                response.add("message", "Successfully Updated");
                response.add("data", "");
                writer.print(response.build());
            }

        } catch (SQLException e) {
            JsonObjectBuilder response = Json.createObjectBuilder();
            response.add("status", 400);
            response.add("message", "Error");
            response.add("data", e.getLocalizedMessage());
            writer.print(response.build());
            resp.setStatus(HttpServletResponse.SC_OK); //200
        }/* finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }*/
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String customerID = req.getParameter("customerID");
        JsonObjectBuilder dataMsgBuilder = Json.createObjectBuilder();
        PrintWriter writer = resp.getWriter();
//        Connection connection = null;
        try {
            /*connection = ds.getConnection();
            PreparedStatement pstm = connection.prepareStatement("DELETE FROM customer WHERE id=?");
            pstm.setObject(1, customerID);*/

            if (customerBO.deleteCustomer(customerID)) {
                resp.setStatus(HttpServletResponse.SC_OK); //200
                dataMsgBuilder.add("data", "");
                dataMsgBuilder.add("massage", "Customer Deleted");
                dataMsgBuilder.add("status", "200");
                writer.print(dataMsgBuilder.build());
            }
        } catch (SQLException e) {
            dataMsgBuilder.add("status", 400);
            dataMsgBuilder.add("message", "Error");
            dataMsgBuilder.add("data", e.getLocalizedMessage());
            writer.print(dataMsgBuilder.build());
            resp.setStatus(HttpServletResponse.SC_OK); //200
        } /*finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }*/
    }
}
