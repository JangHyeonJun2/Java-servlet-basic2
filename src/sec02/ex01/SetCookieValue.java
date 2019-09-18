package sec02.ex01;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Date;
@WebServlet("/set")
public class SetCookieValue extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out = resp.getWriter();
        Date date = new Date();



        Cookie c = new Cookie("cookieTest", URLEncoder.encode("JSP프로그래밍입니다.","utf-8"));
        c.setMaxAge(24*60*60);
        resp.addCookie(c);
        out.println("현재시간: "+date);
        out.println("현재 시간을 Cookie로 저장한다.");
    }
}
