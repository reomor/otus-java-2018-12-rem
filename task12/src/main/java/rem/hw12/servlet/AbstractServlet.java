package rem.hw12.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

public class AbstractServlet extends HttpServlet {
    public static String DEFAULT_CONTENT_TYPE = "text/html;charset=utf-8";

    public void setResponseStatusOK(HttpServletResponse response) {
        response.setContentType(DEFAULT_CONTENT_TYPE);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
