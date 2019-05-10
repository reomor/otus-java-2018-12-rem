package rem.hw14.web;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import rem.hw14.dbcommon.DBService;
import rem.hw14.domain.AddressDataSet;
import rem.hw14.domain.UserDataSet;
import rem.hw14.filter.AuthorizationFilter;
import rem.hw14.front.FrontService;
import rem.hw14.front.FrontServiceImpl;
import rem.hw14.hibernate.DBServiceHibernateImpl;
import rem.hw14.messaging.MessageChannel;
import rem.hw14.messaging.core.Address;
import rem.hw14.messaging.core.MessageSystem;
import rem.hw14.servlet.AdminServlet;
import rem.hw14.servlet.LoginServlet;

public class WebServer {
    private final static int PORT = 8080;
    private final static String STATIC = "/static";

    public void start() throws Exception {
        MessageSystem messageSystem = new MessageSystem();
        MessageChannel messageChannel = new MessageChannel(messageSystem);
        final DBService<UserDataSet> dbService = new DBServiceHibernateImpl(messageChannel, new Address("DB"));
        final FrontService frontService = new FrontServiceImpl(messageChannel, new Address("FRONT"));
        messageSystem.start();

        dbService.save(new UserDataSet("First", 1, new AddressDataSet("Lenina, 1")));

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
