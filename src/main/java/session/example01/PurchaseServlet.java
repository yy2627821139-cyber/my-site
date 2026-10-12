package session.example01;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * 将用户购买的图书信息保存至 session 对象中，
 * 并重定向到展示已购买图书列表的 CartServlet
 */
@WebServlet("/PurchaseServlet")
public class PurchaseServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 获取要购买的图书编号
        String id = request.getParameter("id");
        Book book = BookDB.getBook(id);

        // 获取 session 对象
        HttpSession session = request.getSession();

        // 从 session 中取出购物车（List 集合模拟购物车）
        @SuppressWarnings("unchecked")
        List<Book> cart = (List<Book>) session.getAttribute("cart");
        if (cart == null) {
            // 首次购买，为用户创建一个购物车(List 集合模拟购物车)
            cart = new ArrayList<Book>();
            // 将购物车存入 session 对象
            session.setAttribute("cart", cart);
        }
        // 将商品放入购物车
        cart.add(book);

        // 创建 Cookie 存放 session 的标识号，30分钟内即使浏览器关闭也能找回会话
        Cookie cookie = new Cookie("JSESSIONID", session.getId());
        cookie.setMaxAge(60 * 30);
        cookie.setPath("/");
        response.addCookie(cookie);

        // 页面重定向到显示已购买图书列表的 CartServlet
        response.sendRedirect(request.getContextPath() + "/CartServlet");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
