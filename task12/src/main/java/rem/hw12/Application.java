package rem.hw12;

import rem.hw12.web.WebServer;

public class Application {
    public static void main(String[] args) {
        WebServer server = new WebServer();
        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
