package sec05.ex01;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
/*
 * WebServlet에서 로그인 이름은 각  프로젝트마다 있기 때문에 전에 프로젝트에서 썻던 것은 주석처리를 해야한다.
 */
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
        /*
         * 로그인창에서 전송된 ID와 비밀번호를 가져옵니다.
         */
        String user_id = req.getParameter("user_id");
        String user_pwd = req.getParameter("user_pw");
        /*
         * MemberVO객체를 생성하고 속성에 ID와 비밀번호를 설정한다.
         */
        MemberVO memberVO = new MemberVO();
        memberVO.setId(user_id);
        memberVO.setPwd(user_pwd);

        MemberDAO dao = new MemberDAO();
        boolean result = dao.isExisted(memberVO);//MemberDAO의 isExisted() 메서드를 호출하면서 memberVO를 전달

        if(result){
            HttpSession session1 = req.getSession();
            session1.setAttribute("isLogon",true);//조회한 결과가 true이면 isLogon속성을 true로 세션에 저장한다.
            session1.setAttribute("login.id",user_id);//login.id라는 이름으로 user_id를 login.id변수에 저장한다는 의미
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
