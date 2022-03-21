package pack;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name = "FirstServlet", urlPatterns = "/first")
public class FirstServlet extends HttpServlet {
    private static Logger logger = LoggerFactory.getLogger(FirstServlet.class);
    private List<Product> listProducts;

    public FirstServlet() {
        listProducts = new ArrayList<>();
        for(int i=0; i < 10; i++){
            listProducts.add(new Product(i,"Продукт # "+i, (double) i * 2));
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().printf("<html>");
        getServletContext().getRequestDispatcher("/header.html").include(req, resp);     // при помощи диспатчера подгружается контент с др ресурса
        for(Product product: listProducts){
            resp.getWriter().printf("<div class=\"product\"><div class=\"product_img\"></div> id: "+ product.getId()+ " наименование: "  + product.getTitle()+ " цена: " + product.getPrice() + "</div>");
        }
        getServletContext().getRequestDispatcher("/footer.html").include(req, resp);
        resp.getWriter().close();
    }

    @Override
    public void init() throws ServletException {
        logger.debug("Init");
    }
}