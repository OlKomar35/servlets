package pack;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ThirdServlet", urlPatterns = "/three")
public class ThirdServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(ThirdServlet.class); // Trace < Debug < Info < Warn < Error < Fatal


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Log: Third");
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.printf("<html><body><h1>Third request</h1></body></html>");
        out.close();
    }
}
