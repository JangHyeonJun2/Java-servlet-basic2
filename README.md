# Java-servlet-basic2

# 9.1 쿠기와 세션 알아보기

#### 웹 페이지 연결 기능

**보통 웹 프로그램에서 사용되는 정보는 서블릿의 비즈니스 로직 처리 기능을 이용해 데이터베이스에서 가져온다.그러나 동시 사용자 수가 많아지면 데이터베이스 연동속도도 영향을 받게 되므로 정보의 종류에 따라 어떤 정보들은 클라이언트 PC나 서버의 메모리에 저장해두고 사용하면 좀 더 프로그램을 빠르게 실행시킬 수 있다. 이 장에서는 그 방법과 함께 서블릿이 로그인 시 사용자의 로그인 상태를 일정하게 유지시키는 기능에 대해 살펴 보겠다.**

------

#### 9.1.1 세션 트래킹

쇼핑몰을 이용하는 일반 사용자들은 로그인 상태를 각각의 웹 페이지들이 자동적으로 알고 있을 것이라 생각한다. 그러나 실제 HTTP 프로토콜 방식으로 통신하는 웹 페이지들은 서로 어떤 정보도 공유하지 않는다. 

사용자 입장에서 웹 페이지 사이의 상태나 정보를 공유하려면 프로그래머가 **세션트래킹** 이라는 웹 페이지 연결 기능을 구현해야 한다.

![스크린샷 2019-09-05 오후 9.45.57](/Users/janghyeonjun/Desktop/스크린샷 2019-09-05 오후 9.45.57.png)

HTTP 프로토콜은 서버-클라이언트 통신 시 stateless 방식으로 통신을 합니다. 즉, 브라우저에서 새 웹 페이지를 열면 기존의 웹 페이지나 서블릿에 관한 어떤 연결 정보도 새 웹 페이지에서는 알 수 없다.

### 정리하면

HTTP프로토콜은 각 웹 페이지의 상태나 정보를 다른 페이지들과 공유하지 않는 stateless 방식으로 통신을 한다. 따라서 웹 페이지나 서블릿끼리 상태나 정보를 공유하려면 웹 페이지를 연동하는 방법은 아래와 같다.

- <hidden> 태그: HTML의 <hidden> 태그를 이용해 웹 페이지들 사이의 정보를 공유한다.
- URL Rewriting: GET 방식으로 URL 뒤에 정보를 붙여서 다른 페이지로 전송한다.
- 쿠기: 클라이언트 PC의 Cookie 파일에 정보를 저장한 후 웹 페이지들이 공유한다.
- 세션: 서버 메모리에 정보를 저장한 후 웹 페이지들이 공유한다.

------

# 9.2 <hidden> 태그와 URL Rewriting 이용해 웹 페이지 연동하기

<hidden> 태그는 브라우저에는 표시되지 않지만 미리 저장된 정보를 서블릿을 전송할 수 있다.

1.login.html을 다음과 같이 작성한다.로그인창에서 ID와 비밀번호를 입력하면 미리 <hidden>태그에 저장된 주소,이메일 ,휴대폰 번호를 서블릿으로 전송한다.

```html
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>로그인 창</title>
</head>
<body>
    <form name="frmLogin" method="post" action="login" enctype="UTF-8">
        아이디:<input type="text" name="user_id">
        비밀번호:<input type="password" name="user_pw"><br>
        <input type="submit" value="로그인">
        <input type="reset" value="다시 입력">
        <input type="hidden" name="user_address" value="서울시 성북구">
        <input type="hidden" name="user_email" value="test@naver.com">
        <input type="hidden" name="user_hp" value="010-1111-1111">
    </form>
</body>
</html>
```

2.LoginServlet클래스를 다음과 같이 작성한다. getParameter()메서드를 이용해 전송된 회원 정보를 가져온 후 브라우저로 다시 출력한다.

```java
package sec01.ex01;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
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
        data+="</body></html>";
        out.print(data);
    }

    @Override
    public void destroy() {
        System.out.println("destroy 메서드 호출");
    }
}

```

#### 9.2.2 URL Rewriting을 이용한 세션 트래킹 연습

- 이번에는 URL Rewriting을 이용해 로그인창에서 입력 받은 ID와 비밀번호를 다른 서블릿으로 전송하여 로그인 상태를 확인해 보겠다.

  **1.**LoginServlet.java 를 다음과 같이 작성한다. 로그인창에서 입력받은 ID와 비밀번호를 <a> 태그의 두 번째 서블릿으로 보내기를 클릭하면 로그인창에서 입력한 ID와 비밀번호 그리고 다른 정보들을 GET방식을 이용해 두 번째 서블릿으로 전송한다.

  ```java
  package sec01.ex02;
  
  import javax.servlet.ServletException;
  import javax.servlet.annotation.WebServlet;
  import javax.servlet.http.HttpServlet;
  import javax.servlet.http.HttpServletRequest;
  import javax.servlet.http.HttpServletResponse;
  import java.io.IOException;
  import java.io.PrintWriter;
  import java.net.URLEncoder;
  
  @WebServlet("/login")
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
  
  ```

  **2.**SecondServlet.java를 다음과 같이 작성한다.첫 번째 서블릿에서 전송한 데이터 중 ID와 비밀번호를 가져왔으면 이미 첫 번째 서블릿에서 로그인한 것이므로 로그인 상태를 유지하도록 한다.

  ```java
  package sec01.ex02;
  
  import javax.servlet.ServletException;
  import javax.servlet.annotation.WebServlet;
  import javax.servlet.http.HttpServlet;
  import javax.servlet.http.HttpServletRequest;
  import javax.servlet.http.HttpServletResponse;
  import java.io.IOException;
  import java.io.PrintWriter;
  
  @WebServlet("/second")
  public class SecondServlet extends HttpServlet {
      @Override
      public void init() throws ServletException {
          System.out.println("init 메서드 호출");
      }
  
      @Override
      protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
          req.setCharacterEncoding("utf-8");
          resp.setContentType("text/html;charset=utf-8");
          PrintWriter out = resp.getWriter();
  
          /*
           * 첫 번째 서블릿에서 전송한 로그인 정보를 가져온다
           */
          String user_id = req.getParameter("user_id");
          String user_pw = req.getParameter("user_pw");
          String user_address = req.getParameter("user_address");
  
          out.println("<html><body>");
          if(user_id!=null && user_id.length() != 0){
              out.println("이미 로그인 상태입니다.<br><br>");
              out.println("첫 번째 서블릿에서 넘겨준 아이디 : " + user_id+"<br>");
              out.println("첫 번째 서블릿에서 넘겨준 비밀번호: "+user_pw+"<br>");
              out.println("첫 번째 서블릿에서 넘겨준 주소: " +user_address+"<br>");
              out.println("</body></html>");
          }else {
              out.println("로그인 하지 않았습니다.<br><br>");
              out.println("다시 로그인하세요!");
              out.println("<a href='login.html'>로그인 창으로 이동하기</>");
          }
      }
  
      @Override
      public void destroy() {
          System.out.println("destroy 메서드 호출");
      }
  }
  
  ```

  주석추가

