package sec03.ex03;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

@WebServlet("/sess3")
public class SessionTest3 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out = resp.getWriter();
        int a = 30;
        HttpSession session = req.getSession();//getSession()을 호출하여 최초 요청 시 세션 객체를 새로 생성하거나 기존 세션을 반환한다.
        out.println("세션 아이디:"+session.getId()+"<br>"); // 생성된 세션 객체의 id를 가져옵니다.
        out.println("최초 세션 생성 시각"+new Date(session.getCreationTime())+"<br>");//최초 세션 객체 생성 시간을 가져온다.
        out.println("최근 세션 접근 시각: "+new Date(session.getLastAccessedTime())+"<br>");
        out.println("기본 세션 유효 시간 : " +session.getMaxInactiveInterval()+"<br>");
        session.setMaxInactiveInterval(5); //유효시간을 5초로 설정
        out.println("세션 유효 시간: " +session.getMaxInactiveInterval()+"<br>");
        if(session.isNew()){
            out.println("새 세션이 만들어졌습니다.");
        }
        session.invalidate(); //invalidate()를 호출하여 생성된 세션 객체를 강제로 삭제한다.
    }
}
