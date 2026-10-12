package session.example01;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * 展示用户已经购买的图书列表（购物车）
 */
@WebServlet("/CartServlet")
public class CartServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession();
        @SuppressWarnings("unchecked")
        List<Book> cart = (List<Book>) session.getAttribute("cart");

        if (cart == null || cart.isEmpty()) {
            out.print("对不起，您还没有购买任何书籍！<br/>");
            out.print("<a href='ListBookServlet'>去购买</a>");
            return;
        }

        out.print("您购买的书本有：<br/>");
        for (Book book : cart) {
            out.print(book.getName() + "<br/>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
