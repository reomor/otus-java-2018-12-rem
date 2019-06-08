package rem.hw16.messageserver;

import rem.hw16.messageserver.runner.ProcessRunner;
import rem.hw16.messageserver.runner.ProcessRunnerImpl;
import rem.hw16.messageserver.server.SocketServer;

import javax.management.*;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
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
    private static final String COMMAND_JETTY_START = SOURCE_JETTY_HOME + "\\bin\\jetty.sh";
    private static final int CLIENT_START_DELAY = 3;

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
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        ObjectName objectName = new ObjectName("rem.hw16.messageServer:type=Server");
        SocketServer socketServer = new SocketServer();
        mBeanServer.registerMBean(socketServer, objectName);

        deployWar(SOURCE_WAR, SOURCE_JETTY_WEBAPPS);
        //startClient(executorService, COMMAND_JETTY_START);
        startClient(executorService, COMMAND_DBSERVICE_START);
        socketServer.start();
        executorService.shutdown();
    }

    private void startClient(ScheduledExecutorService executorService, String command) {
        executorService.schedule(() -> {
            try {
                final ProcessRunner processRunner = new ProcessRunnerImpl();
                processRunnerList.add(processRunner);
                processRunner.start(command);
            } catch (IOException e) {
                logger.log(Level.WARNING, "Process started with exception(" + e.getLocalizedMessage() + ")");
            }
        }, CLIENT_START_DELAY, TimeUnit.SECONDS);
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
