package sec05.ex01;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doHandle(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doHandle(req,resp);
    }

    public void doHandle(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException{
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out = resp.getWriter();
        HttpSession session = req.getSession();
        String user_id = req.getParameter("user_id");
        String user_pwd = req.getParameter("user_pw");

        MemberVO memberVO = new MemberVO();
        memberVO.setId(user_id);
        memberVO.setPwd(user_pwd);

        MemberDAO dao = new MemberDAO();
        boolean result = dao.isExisted(memberVO);

        if(result){
            HttpSession session1 = req.getSession();
            session1.setAttribute("isLogon",true);
            session1.setAttribute("login.id",user_id);
            session1.setAttribute("login.pw",user_pwd);

            out.print("<html><body>");
            out.print("안녕하세요 "+user_id+"님!!!<br>");
            out.print("<a href='show'>회원정보 보기</a>");
            out.print("</body></html>");
        }else {
            out.print("<html><body><center>회원 아이디가 틀립니다.");
            out.print("<a href='login3.html'>다시 로그인 하기</a>");
            out.print("</body></html>");
        }
    }
}
