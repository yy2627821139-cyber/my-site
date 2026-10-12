package cn.gok.yjt1012;
import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import session.example02.UserDB; // 跨包引用 Session 作业的用户模拟库

@WebServlet("/registerServlet")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8"); // 修复中文用户名/密码乱码

        String email    = request.getParameter("email");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // 把注册账号密码存入用户模拟库，供 session.example02 登录校验使用（“账号密码和注册一致”）
        UserDB.register(username, password);

        // 存到 request 域
        request.setAttribute("email", email);
        request.setAttribute("username", username);

        // 转发给 JSP 显示
        request.getRequestDispatcher("/myjsp.jsp").forward(request, response);

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}