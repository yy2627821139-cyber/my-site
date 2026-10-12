package cn.gok.yjt1012;

import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import session.example02.User;
import session.example02.UserDB;

/**
 * 合并后的统一登录入口（原 session.example02.LoginServlet1 已并入此处）。
 * 功能：
 *  - 用 UserDB 校验用户名/密码（账号来自模拟库，不写死）
 *  - 登录成功：把 User 存入 session，并重定向到网站首界面 IndexServlet
 *  - 登录失败：提示登录失败信息
 *  - “记住我”：把用户名/密码写入客户端 cookie（7天），登录页可回填
 *  - 一次性验证码：login.html 带验证码参数时校验（取出后立即删除，附加题）
 */
@WebServlet(name = "loginServlet", urlPatterns = "/loginServlet")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String remember = request.getParameter("remember"); // 勾选才传 "on"
        String code = request.getParameter("code");          // 验证码（必填，一次性）

        HttpSession session = request.getSession();

        // ===== 一次性验证码校验（必填，取出即删，防止重放）=====
        String checkCode = (String) session.getAttribute("checkCode");
        session.removeAttribute("checkCode"); // 一次性：无论成功失败，取出后立即删除
        if (code == null || code.trim().isEmpty()) {
            out.print("请输入验证码！<a href='client/login.html'>重新登录</a>");
            return;
        }
        if (checkCode == null || !checkCode.equalsIgnoreCase(code.trim())) {
            out.print("验证码错误！<a href='client/login.html'>重新登录</a>");
            return;
        }

        // ===== 用户名密码校验（查模拟库，不再写死）=====
        if (UserDB.validate(username, password)) {
            // 登录成功，保存用户信息到 session 对象
            User user = new User(username, password);
            session.setAttribute("user", user);

            if ("on".equals(remember)) {
                // 勾选“记住我”：把用户名、密码写入客户端 cookie，有效期 7 天
                Cookie uCookie = new Cookie("username", URLEncoder.encode(username, "UTF-8"));
                Cookie pCookie = new Cookie("password", URLEncoder.encode(password, "UTF-8"));
                int maxAge = 7 * 24 * 60 * 60; // 秒
                uCookie.setMaxAge(maxAge);
                pCookie.setMaxAge(maxAge);
                uCookie.setPath("/");   // 全站可读
                pCookie.setPath("/");
                response.addCookie(uCookie);
                response.addCookie(pCookie);
            } else {
                // 未勾选：把已存在的 cookie 有效期置 0 = 删除
                Cookie[] cookies = request.getCookies();
                if (cookies != null) {
                    for (Cookie c : cookies) {
                        if ("username".equals(c.getName()) || "password".equals(c.getName())) {
                            c.setMaxAge(0);
                            c.setPath("/");
                            response.addCookie(c);
                        }
                    }
                }
            }

            // 登录成功跳转到网站首界面
            response.sendRedirect(request.getContextPath() + "/IndexServlet");
        } else {
            // 登录失败，提示登录失败信息
            out.print("登录失败，用户名或密码错误！<a href='client/login.html'>重新登录</a>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // GET 直接访问时，跳转到登录页（client 下的电商登录入口）
        response.sendRedirect(request.getContextPath() + "/client/login.html");
    }
}
