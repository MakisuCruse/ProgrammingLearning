import javax.lang.model.element.Name;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.dsig.spec.XPathType;
import java.io.IOException;

/**
 * Created by makisucruse on 2016/10/18.
 */
@WebFilter(filterName = "filter",urlPatterns = "/myfirst")
public class MyFirstFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("init..");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest hsr=(HttpServletRequest)request;
        System.out.println(hsr.getRequestURI());
        ServletContext sc=request.getServletContext();
        RequestDispatcher rd=sc.getRequestDispatcher("/myfirst");
        rd.forward(request,response);
    }

    @Override
    public void destroy() {
        System.out.println("destroy..");
    }
}
