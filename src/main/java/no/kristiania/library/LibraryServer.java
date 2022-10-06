package no.kristiania.library;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;

public class LibraryServer {

    private static final Logger logger = LoggerFactory.getLogger(LibraryServer.class);

    private final Server server;

    public LibraryServer(int port) {
        server = new Server(port);

        var webContext = new WebAppContext();
        webContext.setContextPath("/");
        webContext.setBaseResource(Resource.newClassPathResource("/webappDemo"));
        server.setHandler(webContext);
    }

    public URL getURL() throws MalformedURLException {

        return server.getURI().toURL();
    }

    public void start() throws Exception {
        server.start();
    }

    public static void main(String[] args) throws Exception {
        var server = new LibraryServer(8080);
        server.start();
        logger.warn("Server starting at {}", server.getURL());
    }
}
