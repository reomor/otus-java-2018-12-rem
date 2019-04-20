package rem.hw12.servlet;

import rem.hw12.dbcommon.DBService;
import rem.hw12.domain.UserDataSet;
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
    private String ADMIN_TEMPLATE_PAGE = "admin.ftl";
    private final TemplateProcessor templateProcessor;
    private final DBService<UserDataSet> dbService;

    public AdminServlet(TemplateProcessor templateProcessor, DBService<UserDataSet> dbService) {
        this.templateProcessor = templateProcessor;
        this.dbService = dbService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> model = new HashMap<>();
        String username = "N/A";
        for (Cookie cookie : request.getCookies()) {
            if ("username".equals(cookie.getName())) {
                username = cookie.getValue();
                break;
            }
        }
        model.put("username", username);
        String userId = request.getParameter("id");
        if (userId != null && !userId.isBlank()) {
            final UserDataSet userDataSet = dbService.load(Integer.parseInt(userId));
            if (userDataSet != null) {
                model.put("user", userDataSet);
            }
        } else {
            model.put("numberOfUsers", dbService.loadAll().size());
        }

        response.setContentType(DEFAULT_CONTENT_TYPE);
        response.getWriter().println(templateProcessor.getTemplatePage(ADMIN_TEMPLATE_PAGE, model));
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
