package rem.hw12.servlet;

import rem.hw12.web.TemplateProcessor;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AdminServlet extends HttpServlet {
    private String DEFAULT_CONTENT_TYPE = "text/html;charset=utf-8";
    private String ADMIN_TEMPLATE_PAGE = "admin.html";
    private final TemplateProcessor templateProcessor;

    public AdminServlet(TemplateProcessor templateProcessor) {
        this.templateProcessor = templateProcessor;
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> model = new HashMap<>();
        String username = "empty";
        for (Cookie cookie : request.getCookies()) {
            if ("username".equals(cookie.getName())) {
                username = cookie.getValue();
            }
        }
        model.put("username", username);

        response.setContentType(DEFAULT_CONTENT_TYPE);
        response.getWriter().println(templateProcessor.getTemplatePage(ADMIN_TEMPLATE_PAGE, model));
        response.setStatus(HttpServletResponse.SC_OK);
    }
}