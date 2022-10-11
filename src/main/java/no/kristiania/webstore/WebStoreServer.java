package no.kristiania.webstore;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;

public class WebStoreServer {

    private static final Logger logger = LoggerFactory.getLogger(WebStoreServer.class);

    private final Server server;

    public WebStoreServer (int port) {
        this.server = new Server(port);

    }

    public URL getUrl() throws MalformedURLException {
        return server.getURI().toURL();
    }

    public void start() throws Exception {
        server.start();
    }

    public static void main(String[] args) throws Exception {
        var server = new WebStoreServer(8080);
        server.start();
        logger.warn("Server starting at {}", server.getUrl());
    }

}
