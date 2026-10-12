package session.example01;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 显示所有可购图书的列表
 */
@WebServlet("/ListBookServlet")
public class ListBookServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print("本站提供的书籍有：<br/>");
        for (Book book : BookDB.getAllBooks()) {
            // 点击“点击购买”超链接，把图书编号以 id 参数传给 PurchaseServlet
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
