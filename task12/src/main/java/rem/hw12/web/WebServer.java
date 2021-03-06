package rem.hw12.web;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import rem.hw12.dbcommon.DBService;
import rem.hw12.domain.AddressDataSet;
import rem.hw12.domain.UserDataSet;
import rem.hw12.filter.AuthorizationFilter;
import rem.hw12.hibernate.DBServiceHibernateImpl;
import rem.hw12.servlet.AdminServlet;
import rem.hw12.servlet.LoginServlet;

public class WebServer {
    private final static int PORT = 8080;
    private final static String STATIC = "/static";

    public void start() throws Exception {
        final DBService<UserDataSet> dbService = new DBServiceHibernateImpl();
        dbService.save(new UserDataSet("First", 1, new AddressDataSet("Lenina, 1")));
        ResourceHandler resourceHandler = new ResourceHandler();
        Resource resource = Resource.newClassPathResource(STATIC);
        resourceHandler.setBaseResource(resource);

        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.addServlet(
                new ServletHolder(new AdminServlet(new TemplateProcessor(), dbService)),
                "/admin");
        contextHandler.addFilter(new FilterHolder(new AuthorizationFilter()), AdminServlet.pathSpec, null);
        contextHandler.addServlet(new ServletHolder(new LoginServlet()), "/login");

        Server server = new Server(PORT);
        server.setHandler(new HandlerList(resourceHandler, contextHandler));
        server.start();
        server.join();
    }
}
