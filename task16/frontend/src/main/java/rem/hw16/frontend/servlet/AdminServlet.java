package rem.hw16.frontend.servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import rem.hw16.dbserver.domain.AddressDataSet;
import rem.hw16.dbserver.domain.PhoneDataSet;
import rem.hw16.dbserver.domain.UserDataSet;
import rem.hw16.frontend.front.FrontService;
import rem.hw16.frontend.web.TemplateProcessor;

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
    private static final String ADMIN_TEMPLATE_PAGE = "admin.ftl";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_USER = "user";
    private static final String KEY_CACHEDUSERS = "cachedUsers";

    private TemplateProcessor templateProcessor;
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
    public void setFrontService(FrontService frontService) {
        this.frontService = frontService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> model = new HashMap<>();
        String username = "N/A";
        for (Cookie cookie : request.getCookies()) {
            if (KEY_USERNAME.equals(cookie.getName())) {
                username = cookie.getValue();
                break;
            }
        }
        model.put(KEY_USERNAME, username);
        model.put(KEY_CACHEDUSERS, frontService.getUserDataSetCollection());

        String userId = request.getParameter("id");
        if (userId != null && !userId.isEmpty()) {
            final int parsedId = Integer.parseInt(userId);
            final UserDataSet userDataSet = frontService.getUserData(parsedId);
            if (userDataSet != null) {
                model.put(KEY_USER, userDataSet);
            }
            frontService.requestById(parsedId);
        }
        response.getWriter().println(templateProcessor.getTemplatePage(ADMIN_TEMPLATE_PAGE, model));
        setResponseStatusOK(response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final UserDataSet userDataSet = getUserDataSetFromRequest(request);
        if (userDataSet != null) {
            frontService.requestToSave(userDataSet);
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
