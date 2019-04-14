package rem.hw12.executor;

import org.hibernate.Session;

@FunctionalInterface
public interface SessionHandler {
    void handle(Session session);
}
