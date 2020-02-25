package sec04.ex01;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

//@WebServlet("/login")
public class SessionTest5 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doHandle(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doHandle(req,resp);
    }

    public void doHandle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out = resp.getWriter();
        HttpSession session = req.getSession();
        String user_id = req.getParameter("user_id");
        String user_pwd = req.getParameter("user_pwd");
        if(session.isNew()){
            if(user_id != null){
                session.setAttribute("user_id",user_id);
                String url=resp.encodeURL("login");//변수 url에 encodURL()을 이용해 응답 시 미리 jsessionID를 저장합니다.
                out.println("<a href="+url+">로그인 상태 확인</a>"); //로그인 상태 확인 클릭시 jsessionId를 서블릿으로 다시 전송한다.
            }else {
                out.println("<a href='login2.html'>다시 로그인하시오!!</a>");
                session.invalidate();
            }
        }else {
            user_id = (String)session.getAttribute("user_id");
            if(user_id != null && user_id.length() != 0){
                out.print("안녕하세요"+user_id+"님!!!");
            }else {
                out.print("<a href='login2.html'>다시 로그인하세요</a>");
                session.invalidate();
            }
        }
    }
}
