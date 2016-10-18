import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 * Created by makisucruse on 2016/10/18.
 */
@WebServlet(name="MyFirstServlet",urlPatterns = "/myfirst")
public class MyFirstServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
//        System.out.println(getServletConfig().getInitParameter("name"));
//        Cookie cookie=new Cookie("name","123");
//        resp.addCookie(cookie);
        PrintWriter pt=resp.getWriter();
        pt.println(new Date());
        ServletContext sc=getServletContext();
//        RequestDispatcher rc=sc.getRequestDispatcher("/myfirst");
//        rc.forward(req,resp);
//        resp.sendRedirect("/another");

    }
}
