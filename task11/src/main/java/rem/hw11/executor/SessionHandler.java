package rem.hw11.executor;

import org.hibernate.Session;

@FunctionalInterface
public interface SessionHandler {
    void handle(Session session);
}
