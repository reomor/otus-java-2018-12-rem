package rem.hw16.messageserver.server.ws;

import org.eclipse.jetty.websocket.servlet.*;

public class WsMessagingServlet extends WebSocketServlet {
    @Override
    public void configure(WebSocketServletFactory webSocketServletFactory) {
        webSocketServletFactory.register(WsMessagingAdapter.class);
    }
}
