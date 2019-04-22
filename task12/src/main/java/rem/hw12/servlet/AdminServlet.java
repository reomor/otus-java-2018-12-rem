package rem.hw12.servlet;

import rem.hw12.dbcommon.DBService;
import rem.hw12.domain.AddressDataSet;
import rem.hw12.domain.PhoneDataSet;
import rem.hw12.domain.UserDataSet;
import rem.hw12.web.TemplateProcessor;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminServlet extends AbstractServlet {
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
        model.put("numberOfUsers", dbService.loadAll().size());

        String userId = request.getParameter("id");
        if (userId != null && !userId.isBlank()) {
            final UserDataSet userDataSet = dbService.load(Integer.parseInt(userId));
            if (userDataSet != null) {
                model.put("user", userDataSet);
            }
        }
        response.getWriter().println(templateProcessor.getTemplatePage(ADMIN_TEMPLATE_PAGE, model));
        setResponseStatusOK(response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final UserDataSet userDataSet = getUserDataSetFromRequest(request);
        if (userDataSet != null) {
            dbService.save(userDataSet);
            setResponseStatusOK(response);
        } else {
            setResponseStatusNotAcceptable(response);
        }
        response.sendRedirect(pathSpec);
    }

    private UserDataSet getUserDataSetFromRequest(HttpServletRequest request) {
        final String username = request.getParameter("name");
        if (username == null || username.isBlank()) {
            return null;
        }
        final String ageString = request.getParameter("age");
        int age = 0;
        if (ageString != null && !ageString.isBlank()) {
            age = Integer.parseInt(ageString);
        } else {
            return null;
        }
        final String street = request.getParameter("street");
        if (street == null || street.isBlank()) {
            return null;
        }
        final String[] phones = request.getParameterValues("phones");
        final UserDataSet userDataSet = new UserDataSet(username, age, new AddressDataSet(street));
        List<PhoneDataSet> phoneDataSetList = new ArrayList<>();
        for (String phone : phones) {
            if (!phone.isBlank()) {
                phoneDataSetList.add(new PhoneDataSet(phone));
            }
        }
        userDataSet.setPhones(phoneDataSetList);
        return userDataSet;
    }
}
