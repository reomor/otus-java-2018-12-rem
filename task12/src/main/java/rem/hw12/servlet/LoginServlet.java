package rem.hw12.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    private final int SESSION_EXPIRE_INTERVAL_IN_SEC = 60;
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println(username + " " + password);
        Cookie cookie = new Cookie("username", username);
        cookie.setMaxAge(SESSION_EXPIRE_INTERVAL_IN_SEC);
        response.addCookie(cookie);
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(SESSION_EXPIRE_INTERVAL_IN_SEC);
        if ("admin".equals(username)) {
            response.sendRedirect("/admin");
        }
    }
}
