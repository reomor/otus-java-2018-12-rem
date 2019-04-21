package rem.hw12.servlet;

import rem.hw12.dbcommon.DBService;
import rem.hw12.domain.AddressDataSet;
import rem.hw12.domain.PhoneDataSet;
import rem.hw12.domain.UserDataSet;
import rem.hw12.web.TemplateProcessor;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminServlet extends HttpServlet {
    public static String pathSpec = "/admin";
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
        }
        model.put("numberOfUsers", dbService.loadAll().size());

        response.getWriter().println(templateProcessor.getTemplatePage(ADMIN_TEMPLATE_PAGE, model));
        ServletUtil.setResponseStatusOK(response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final String username = request.getParameter("name");
        final String ageString = request.getParameter("age");
        int age = 0;
        if (ageString != null && !ageString.isBlank()) {
            age = Integer.parseInt(ageString);
        }
        final String street = request.getParameter("street");
        final String[] phones = request.getParameterValues("phones");
        final UserDataSet userDataSet = new UserDataSet(username, age, new AddressDataSet(street));
        List<PhoneDataSet> phoneDataSetList = new ArrayList<>();
        for (String phone : phones) {
            phoneDataSetList.add(new PhoneDataSet(phone));
        }
        userDataSet.setPhones(phoneDataSetList);
        dbService.save(userDataSet);
        ServletUtil.setResponseStatusOK(response);
        response.sendRedirect(pathSpec);
    }
}
