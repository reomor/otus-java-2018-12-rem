package rem.hw15.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

public class AbstractServlet extends HttpServlet {
    protected static String DEFAULT_CONTENT_TYPE = "text/html;charset=utf-8";

    protected void setResponseStatusOK(HttpServletResponse response) {
        response.setContentType(DEFAULT_CONTENT_TYPE);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    protected void setResponseStatusNotAcceptable(HttpServletResponse response) {
        response.setContentType(DEFAULT_CONTENT_TYPE);
        response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
    }
}
