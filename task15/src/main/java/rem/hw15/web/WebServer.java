package rem.hw15.web;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import rem.hw15.dbcommon.DBService;
import rem.hw15.domain.UserDataSet;
import rem.hw15.filter.AuthorizationFilter;
import rem.hw15.front.FrontService;
import rem.hw15.servlet.AdminServlet;
import rem.hw15.servlet.LoginServlet;

public class WebServer {
    private final static int PORT = 8080;
    private final static String STATIC = "/static";
    final DBService<UserDataSet> dbService;
    final FrontService frontService;

    public WebServer(DBService<UserDataSet> dbService, FrontService frontService) {
        this.dbService = dbService;
        this.frontService = frontService;
    }

    public void start() throws Exception {
        ResourceHandler resourceHandler = new ResourceHandler();
        Resource resource = Resource.newClassPathResource(STATIC);
        resourceHandler.setBaseResource(resource);

        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.addServlet(
                new ServletHolder(new AdminServlet(new TemplateProcessor(), dbService, frontService)),
                "/admin");
        contextHandler.addFilter(new FilterHolder(new AuthorizationFilter()), AdminServlet.pathSpec, null);
        contextHandler.addServlet(new ServletHolder(new LoginServlet()), "/login");

        Server server = new Server(PORT);
        server.setHandler(new HandlerList(resourceHandler, contextHandler));
        server.start();
        server.join();
    }
}
