package rem.hw16.messageserver;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import rem.hw16.messageserver.runner.ProcessRunner;
import rem.hw16.messageserver.runner.ProcessRunnerImpl;
import rem.hw16.messageserver.server.SocketServer;
import rem.hw16.messageserver.server.WebSocketServer;
import rem.hw16.messageserver.server.ws.WsServerMessageServerClient;

import javax.management.*;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerMain {
    private static final Logger logger = Logger.getLogger(ServerMain.class.getName());
    private static final String COMMAND_DBSERVICE_START = "java -jar dbserver\\target\\dbserver.jar";
    private static final String SOURCE_JETTY_HOME = "C:\\jetty-distribution-9.4.18.v20190429";
    private static final String SOURCE_JETTY_WEBAPPS = SOURCE_JETTY_HOME + "\\webapps";
    private static final String SOURCE_WAR = "frontend\\target\\frontend.war";
    private static final int CLIENT_START_DELAY_IN_SEC = 2;

    private List<ProcessRunner> processRunnerList = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        new ServerMain().start();
    }

    public void start() throws Exception {
        /*
        final ExecutorService logThreadExecutor = Executors.newSingleThreadExecutor();
        logThreadExecutor.submit(() -> {
            while (true) {
                for (ProcessRunner processRunner : processRunnerList) {
                    System.out.println(processRunner.getOutput());
                }
                Thread.sleep(1_000);
            }
        });
        logThreadExecutor.shutdown();
        //*/
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(3);

        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        ObjectName objectName = new ObjectName("rem.hw16.messageServer:type=Server");
        final SocketServer socketServer = context.getBean("socketServer", SocketServer.class);
        final WebSocketServer webSocketServer = context.getBean("webSocketServer", WebSocketServer.class);

        mBeanServer.registerMBean(socketServer, objectName);
        //deployWar(SOURCE_WAR, SOURCE_JETTY_WEBAPPS);
        //startClient(executorService, COMMAND_JETTY_START);
        startClient(executorService, () -> context.getBean("wsServerMessageServerClient", WsServerMessageServerClient.class).startClientLoop());
        startServer(executorService, webSocketServer);
        startClient(executorService, COMMAND_DBSERVICE_START);
        socketServer.start();
        executorService.shutdown();
    }

    private void startServer(ScheduledExecutorService executorService, WebSocketServer webSocketServer) {
        startServer(executorService, webSocketServer, CLIENT_START_DELAY_IN_SEC + 1);
    }

    private void startServer(ScheduledExecutorService executorService, WebSocketServer webSocketServer, int serverStartDelayInSec) {
        executorService.schedule(webSocketServer::start, serverStartDelayInSec, TimeUnit.SECONDS);
    }

    private void startClient(ScheduledExecutorService executorService, Runnable runnable) {
        executorService.schedule(runnable, CLIENT_START_DELAY_IN_SEC, TimeUnit.SECONDS);
    }

    private void startClient(ScheduledExecutorService executorService, String command) {
        startClient(executorService, command, CLIENT_START_DELAY_IN_SEC);
    }

    private void startClient(ScheduledExecutorService executorService, String command, int clientStartDelayInSec) {
        executorService.schedule(() -> {
            try {
                final ProcessRunner processRunner = new ProcessRunnerImpl();
                processRunnerList.add(processRunner);
                processRunner.start(command);
            } catch (IOException e) {
                logger.log(Level.WARNING, "Process started with exception(" + e.getLocalizedMessage() + ")");
            }
        }, clientStartDelayInSec, TimeUnit.SECONDS);
    }

    private void deployWar(String warPath, String jettyHomeWarPath) {
        try {
            System.out.println(new java.io.File( "." ).getCanonicalPath());
            Files.copy(Paths.get(warPath),
                    Paths.get(jettyHomeWarPath + "\\root.war"),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error(" + e + ") " +
                    "copying war(" + warPath + ") to " +
                    "jetty home(" + jettyHomeWarPath + ")");
        }
    }
}
