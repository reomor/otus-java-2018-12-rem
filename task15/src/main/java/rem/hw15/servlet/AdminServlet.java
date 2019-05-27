package rem.hw15.servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import rem.hw15.dbcommon.DBService;
import rem.hw15.domain.AddressDataSet;
import rem.hw15.domain.PhoneDataSet;
import rem.hw15.domain.UserDataSet;
import rem.hw15.front.FrontService;
import rem.hw15.web.TemplateProcessor;

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
    public static String pathSpec = "admin";
    private String ADMIN_TEMPLATE_PAGE = "admin.ftl";
    private TemplateProcessor templateProcessor;
    private DBService<UserDataSet> dbService;
    private FrontService frontService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    @Autowired
    public void setTemplateProcessor(TemplateProcessor templateProcessor) {
        this.templateProcessor = templateProcessor;
    }

    @Autowired
    public void setDbService(DBService<UserDataSet> dbService) {
        this.dbService = dbService;
    }

    @Autowired
    public void setFrontService(FrontService frontService) {
        this.frontService = frontService;
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
        model.put("cachedUsers", frontService.getUserDataSetCollection());

        String userId = request.getParameter("id");
        if (userId != null && !userId.isEmpty()) {
            final int parsedId = Integer.parseInt(userId);
            final UserDataSet userDataSet = dbService.load(parsedId);
            if (userDataSet != null) {
                model.put("user", userDataSet);
            }
            frontService.handleRequest(parsedId);
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
        if (username == null || username.isEmpty()) {
            return null;
        }
        final String ageString = request.getParameter("age");
        int age = 0;
        if (ageString != null && !ageString.isEmpty()) {
            age = Integer.parseInt(ageString);
        } else {
            return null;
        }
        final String street = request.getParameter("street");
        if (street == null || street.isEmpty()) {
            return null;
        }
        final String[] phones = request.getParameterValues("phones");
        final UserDataSet userDataSet = new UserDataSet(username, age, new AddressDataSet(street));
        List<PhoneDataSet> phoneDataSetList = new ArrayList<>();
        for (String phone : phones) {
            if (!phone.isEmpty()) {
                phoneDataSetList.add(new PhoneDataSet(phone));
            }
        }
        userDataSet.setPhones(phoneDataSetList);
        return userDataSet;
    }
}
