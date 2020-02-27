package sec05.ex01;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
/*
 * DB연결 속성? 값들은 META-INF에 Resource로 저장 되어 꺼내지게 된다.
 */
public class MemberDAO {
    private DataSource dataFactory;
//    private Connection con;
//    private PreparedStatement pstmt;

    public MemberDAO() {
        try{
            Context ctx = new InitialContext();
            Context envContext = (Context) ctx.lookup("java:/comp/env");
            dataFactory = (DataSource) envContext.lookup("jdbc/mysql");
            System.out.println("db연결 성공!!");
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    public boolean isExisted(MemberVO memberVO){
        Connection con;
        PreparedStatement pstmt;
        boolean result = false;
        String id = memberVO.getId();
        String pwd = memberVO.getPwd();

        try{
            con = dataFactory.getConnection();
            String query = "select if(count(*) = 1,'true','false') as result from member2 where id=? and pwd=?";

            pstmt = con.prepareStatement(query);
            pstmt.setString(1,id);
            pstmt.setString(2,pwd);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            result = Boolean.parseBoolean(rs.getString("result"));
            System.out.println("result="+result);

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return result;
    }
}
