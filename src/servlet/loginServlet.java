package servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/16 0016.
 */
public class loginServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        proceess(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        proceess(req, resp);
    }

    private void proceess(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String sql = null;
        DBHelper db1 = null;
        ResultSet ret = null;
        String username = req.getParameter("userName");
        String paswword = req.getParameter("userPassword");

        resp.setContentType("text/html,charset=UTF-8");
        PrintWriter out = resp.getWriter();

        sql = "select count(id) from users where name='"+username+"'";//SQL语句
        db1 = new DBHelper(sql);//创建DBHelper对象
        try {
            ret = db1.pst.executeQuery();//执行语句，得到结果集
            String sendData;
            while (ret.next()) {
                sendData = ret.getString(1);
                if(Integer.parseInt(sendData) == 0){
                    out.println(Integer.parseInt(sendData));
                    System.out.println("用户名不存在");
                } else {
                    sql = "select count(id) from users where name='"+username+"' and password='"+paswword+"'";//SQL语句
                    db1 = new DBHelper(sql);//创建DBHelper对象
                    try {
                        ret = db1.pst.executeQuery();//执行语句，得到结果集
                        while (ret.next()) {
                            sendData = ret.getString(1);
                            System.out.println(sendData == String.valueOf(0));
                            if(Integer.parseInt(sendData) == 0){
                                out.println("-1");
                                System.out.println("密码不正确");
                            } else {
                                out.println("1");
                                System.out.println("登陆成功");
                            }
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            }
            ret.close();
            db1.close();//关闭连接
            out.flush();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
