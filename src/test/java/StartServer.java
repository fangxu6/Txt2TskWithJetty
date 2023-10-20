import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class StartServer {
    public static void main(String[] args) throws Exception {

        Server server = new Server(8080);

        WebAppContext webApp = new WebAppContext();
        webApp.setServer(server);
        webApp.setContextPath("/");
        webApp.setWar("src/main/webapp/");

        server.setHandler(webApp);
        server.start();
        server.join();
    }
}
