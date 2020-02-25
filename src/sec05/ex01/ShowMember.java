package sec05.ex01;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/show")
public class ShowMember extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out = resp.getWriter();
        String id="";
        String pwd ="";
        Boolean isLogon = false;
        HttpSession session = req.getSession(false); //이미 세션이 존재하면 세션을 반환하고,없으면 null을 반환한다.
        if(session != null){
            isLogon=(Boolean)session.getAttribute("isLogon");//isLogOn속성을 가져와 로그인 상태를 확인한다.
            if(isLogon == true){
                id = (String)session.getAttribute("login.id");
                pwd = (String)session.getAttribute("login.pw");
                out.print("<html><body>");
                out.print("아이디:"+id+"<br>");
                out.print("비밀번호:"+pwd+"<br>");
                out.print("</body></html>");
            }else {
                resp.sendRedirect("login3.html");
            }
        }else {
            resp.sendRedirect("login3.html");
        }
    }
}
