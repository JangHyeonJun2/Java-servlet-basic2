package sec01.ex02;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

//@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        System.out.println("init 메서드 호출");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out = resp.getWriter();
        String user_id = req.getParameter("user_id");
        String user_pw = req.getParameter("user_pw");
        String user_address = req.getParameter("user_address");
        String user_email = req.getParameter("user_email");
        String user_hp = req.getParameter("user_hp");

        String data = "안녕하세요!<br> 로그인 하셨습니다.<br><br>";
        data+="<html><body>";
        data+="아이디: "+user_id;
        data+="<br>";
        data+="패스워드: "+user_pw;
        data+="<br>";
        data+="주소: "+user_address;
        data+="<br>";
        data+="이메일: "+user_email;
        data+="<br>";
        data+="전화번호: "+user_hp;
        data+="<br>";
        out.print(data);

        user_address = URLEncoder.encode(user_address,"utf-8");//GET방식으로 한글을 전송하기 위해 인코딩한다.
        out.print("<a href='/second?user_id="+user_id+"&user_pw="+user_pw+"&user_address="+user_address+"'>두 번째 서블릿으로 보내기</a>");//a 태그를 이용해 링크 클릭 시 서블릿
                                                                                                                            // /second로 다시 로그인 정보를 전송한다.
        data+="</body></html>";
    }

    @Override
    public void destroy() {
        System.out.println("destroy 메서드 호출");
    }
}
