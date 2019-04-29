package rem.hw14.executor;

import org.hibernate.Session;

@FunctionalInterface
public interface SessionHandler {
    void handle(Session session);
}
