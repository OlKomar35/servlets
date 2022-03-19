package pack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

@WebServlet(name = "JdbcServlet", urlPatterns = "/jdbc_servlet")
public class JdbcServlet extends HttpServlet {
    private Logger logger = LoggerFactory.getLogger(JdbcServlet.class);
    private Connection conn;

    @Override
    public void init() throws ServletException {
        ServletContext context = getServletContext();
        conn = (Connection) context.getAttribute("jdbcConnection");
        if (conn == null) {
            throw new ServletException("No JDBC connection in Servlet Context");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Get all tables");
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SHOW TABLES;");
            while (rs.next()) {
                String tableName = rs.getString(0);
                resp.getWriter().println("<p> " + tableName + "</p>");
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }


}
