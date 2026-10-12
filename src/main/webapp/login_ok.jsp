<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.net.URLDecoder" %>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>登录成功</title></head>
<body class="main">
<div id="divcontent" align="center" style="padding:40px">
    <h1>登录成功，欢迎你：<%= request.getAttribute("username") %></h1>
    <p>下面读取客户端 cookie，验证"记住用户名和密码"已生效：</p>
    <%
        String cu = "", cp = "";
        Cookie[] cs = request.getCookies();
        if (cs != null) {
            for (Cookie c : cs) {
                if ("username".equals(c.getName())) cu = URLDecoder.decode(c.getValue(), "UTF-8");
                if ("password".equals(c.getName())) cp = URLDecoder.decode(c.getValue(), "UTF-8");
            }
        }
    %>
    已保存的用户名：<%= cu %><br>
    已保存的密码：<%= cp %><br>
    <p><a href="client/login.html">退出并重新登录（可看到已回填）</a></p>
</div>
</body>
</html>
