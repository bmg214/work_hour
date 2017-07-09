package servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by Administrator on 2017/4/16 0016.
 */
public class userQueryServlet extends HttpServlet{
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
        String username = req.getParameter("username");

        resp.setContentType("text/html,charset=UTF-8");
        PrintWriter out = resp.getWriter();

        sql = "select u.*, e.* from users u inner join employees e on u.employee=e.id where u.name='"+username+"'";//SQL语句
        db1 = new DBHelper(sql);//创建DBHelper对象
        try {
            ret = db1.pst.executeQuery();//执行语句，得到结果集
            while (ret.next()) {
                out.println(ret);
            }
            ret.close();
            db1.close();//关闭连接
            out.flush();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
