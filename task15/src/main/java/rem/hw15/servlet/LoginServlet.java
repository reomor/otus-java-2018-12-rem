package rem.hw15.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginServlet extends AbstractServlet {
    private final int SESSION_EXPIRE_INTERVAL_IN_SEC = 60;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
    }

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

        if ("admin".equals(username) && "admin".equals(password)) {
            response.sendRedirect(AdminServlet.pathSpec);
        }
    }
}
