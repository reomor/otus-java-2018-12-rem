package rem.hw16.messageserver;

import rem.hw16.messageserver.server.SocketServer;

import javax.management.*;
import java.lang.management.ManagementFactory;

public class ServerMain {
    public static void main(String[] args) throws Exception {
        new ServerMain().start();
    }

    public void start() throws Exception {
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        ObjectName objectName = new ObjectName("rem.hw16.messageServer:type=Server");
        SocketServer socketServer = new SocketServer();
        mBeanServer.registerMBean(socketServer, objectName);

        socketServer.start();
    }
}
