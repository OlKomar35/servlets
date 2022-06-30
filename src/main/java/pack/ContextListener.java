package pack;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


@WebListener
public class ContextListener implements ServletContextListener {
    private static final Logger logger = LogManager.getLogger(ContextListener.class); // Trace < Debug < Info < Warn < Error < Fatal

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        } catch (Exception e) {
            logger.error("driver Not Load " + e);
        }
        logger.info("Context initialization: " + context.getContextPath());
        String jdbcConnectionString = context.getInitParameter("jdbcConnectionString");
        String username = context.getInitParameter("dbUsername");
        String password = context.getInitParameter("dbPassword");
        if (isNotNullOrEmpty(jdbcConnectionString) ||
                isNotNullOrEmpty(username)) {
            logger.error("Connection string and DB username must be specified");
            return;
        }
        try {
            Connection connection = DriverManager.getConnection(jdbcConnectionString, username, password);
            context.setAttribute("jdbcConnection", connection);
        } catch (SQLException e) {
            logger.error("", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        Connection conn = (Connection) context.getAttribute("jdbcConnection");
        if (conn == null) {
            return;
        }
        try {
            conn.close();
        } catch (SQLException e) {
            logger.error("", e);
        }
    }

    private boolean isNotNullOrEmpty(String str) {
        return str != null && str.isEmpty();
    }
}
