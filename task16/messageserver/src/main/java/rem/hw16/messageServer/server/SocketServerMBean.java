package rem.hw16.messageServer.server;

import java.io.IOException;

public interface SocketServerMBean {
    boolean getRunning();

    void setRunning(boolean running);
}
