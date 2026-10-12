package session.example02;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

// 导入 example01 的图书类，实现第2-3题合并调试：登录成功后首页展示商品列表
import session.example01.Book;
import session.example01.BookDB;

/**
 * 显示网站首界面：
 * 已登录 -> 显示欢迎信息和用户名，并列出本站提供的书籍
 * 未登录 -> 跳转到 login.html
 */
@WebServlet("/IndexServlet")
public class IndexServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // 从 session 中取出登录时保存的用户对象
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            // 未登录，跳转到电商风格的统一登录入口
            response.sendRedirect(request.getContextPath() + "/client/login.html");
            return;
        }

        out.print("您已登录，欢迎你，" + user.getUsername() + "！"
                + "<a href='LogoutServlet'>退出</a>  ");
        out.print("本站提供的书籍有：<br/>");
        for (Book book : BookDB.getAllBooks()) {
            out.print(book.getName()
                    + "<a href='PurchaseServlet?id=" + book.getId() + "'>点击购买</a><br/>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
