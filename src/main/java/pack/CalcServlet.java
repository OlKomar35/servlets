package pack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "CalcServlet", urlPatterns = "/sum")
public class CalcServlet extends HttpServlet {
    private static Logger logger = LoggerFactory.getLogger(CalcServlet.class);

    // GET http://localhost:8080/jee/sum?a=10&b=20
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().printf("<html><body>");
        PrintWriter out = resp.getWriter();
        int firstNumber = Integer.parseInt(req.getParameter("a"));
        int secondNumber = Integer.parseInt(req.getParameter("b"));
        int sum = firstNumber + secondNumber;

        if(sum>10){
//            resp.sendRedirect(req.getContextPath() + "/first");                           // редирект через HTTP на сервлет - /first
            getServletContext().getRequestDispatcher("/first").forward(req,  resp);       // редирект внутри сервера без выхода на клиента
        }else{
            out.printf("<h2>"+req.getContextPath()+"</h2>");
        }

        out.printf("<h1>" + String.format("Sum Elements %d + %d = %d", firstNumber, secondNumber, sum) + "</h1>");
        out.printf("<h2>результат сложения</h2>");
        out.printf("</body></html>");
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws
            ServletException, IOException {
        logger.info("New POST request");
        resp.getWriter().printf("<h1>New POST request with body %s</h1>",
                readAllLines(req.getReader()));
    }

    public String readAllLines(BufferedReader reader) throws IOException {
        StringBuilder content = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            content.append(line);
            content.append(System.lineSeparator());
        }
        return content.toString();
    }
}
