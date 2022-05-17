package listeners;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.SQLException;

@WebListener
public class MyListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        BasicDataSource bds = new BasicDataSource();
        bds.setDriverClassName("com.mysql.jdbc.Driver");
        bds.setUrl("jdbc:mysql://localhost:3306/company");
        bds.setUsername("root");
        bds.setPassword("ijse");
        bds.setMaxTotal(10); // how many connections
        bds.setInitialSize(10); // how many connection we should initialize

        ServletContext servletContext = servletContextEvent.getServletContext();// a common place for all servlet
        servletContext.setAttribute("bds", bds); // store the pool inside the Servlet Context
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        try {
            ServletContext servletContext = servletContextEvent.getServletContext();
            BasicDataSource bds = (BasicDataSource) servletContext.getAttribute("bds");
            bds.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
