package rem.hw12.servlet;

import javax.servlet.http.HttpServletResponse;

public class ServletUtil {
    public static String DEFAULT_CONTENT_TYPE = "text/html;charset=utf-8";
    private ServletUtil() {}

    public static void setResponseStatusOK(HttpServletResponse response) {
        response.setContentType(DEFAULT_CONTENT_TYPE);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
