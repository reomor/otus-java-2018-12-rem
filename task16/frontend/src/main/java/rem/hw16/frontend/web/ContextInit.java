package rem.hw16.frontend.web;

import org.springframework.beans.factory.annotation.Autowired;
import rem.hw16.frontend.client.FrontendMessageServerClient;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;

public class ContextInit implements ServletContextListener {
    private final FrontendMessageServerClient frontendMessageServerClient;

    @Autowired
    public ContextInit(FrontendMessageServerClient frontendMessageServerClient) {
        this.frontendMessageServerClient = frontendMessageServerClient;
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
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
}
