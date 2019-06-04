package rem.hw16.frontend.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthorizationFilter implements Filter {
    private static int HTTP_STATUS_UNAUHTORIZED = 401;
    private ServletContext servletContext;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.servletContext = filterConfig.getServletContext();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse responce = (HttpServletResponse) servletResponse;

        String uri = request.getRequestURI();
        servletContext.log("Requested Resource:" + uri);
        HttpSession session = request.getSession(false);

        if (session == null) {
            responce.setStatus(HTTP_STATUS_UNAUHTORIZED);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
