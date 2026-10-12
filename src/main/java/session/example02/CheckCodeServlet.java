package session.example02;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javax.imageio.ImageIO;

/**
 * 附加题：一次性验证码
 * 生成 4 位随机验证码图片，并把验证码文本存入 session（属性名 checkCode），
 * 供 LoginServlet（/loginServlet）登录时做一次性校验。
 */
@WebServlet("/CheckCodeServlet")
public class CheckCodeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int width = 100;
        int height = 32;

        // 在内存中创建图片
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        Random rand = new Random();

        // 白色背景
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);

        // 随机生成 4 位验证码并逐个画出
        String chars = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            char c = chars.charAt(rand.nextInt(chars.length()));
            code.append(c);
            g.setColor(new Color(rand.nextInt(150), rand.nextInt(150), rand.nextInt(150)));
            g.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 22));
            g.drawString(String.valueOf(c), 10 + i * 22, 24);
        }

        // 随机干扰线
        for (int i = 0; i < 10; i++) {
            g.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
            g.drawLine(rand.nextInt(width), rand.nextInt(height),
                    rand.nextInt(width), rand.nextInt(height));
        }
        g.dispose();

        // 验证码文本存入 session，供登录校验
        request.getSession().setAttribute("checkCode", code.toString());

        // 禁止浏览器缓存验证码图片
        response.setContentType("image/jpeg");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        // 输出图片
        ImageIO.write(image, "jpeg", response.getOutputStream());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
