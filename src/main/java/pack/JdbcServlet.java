package pack;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    private static final Logger logger = LogManager.getLogger(JdbcServlet.class); // Trace < Debug < Info < Warn < Error < Fatal

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
            ResultSet rs = stmt.executeQuery("SELECT * FROM analityc.statistic;");
            while (rs.next()) {
                String name = rs.getString(2);
                resp.getWriter().println("<p> " + name + "</p>");
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }


}
