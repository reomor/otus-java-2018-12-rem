package rem.hw16.frontend.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import rem.hw16.frontend.client.FrontendMessageServerClient;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;

public class ContextInit implements ServletContextListener {
    private FrontendMessageServerClient frontendMessageServerClient;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        frontendMessageServerClient.startClientLoop();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            frontendMessageServerClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Autowired
    public void setFrontendMessageServerClient(FrontendMessageServerClient frontendMessageServerClient) {
        this.frontendMessageServerClient = frontendMessageServerClient;
    }
}
