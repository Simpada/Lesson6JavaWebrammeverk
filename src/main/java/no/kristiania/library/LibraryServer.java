package no.kristiania.library;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;

import java.net.MalformedURLException;
import java.net.URL;

public class LibraryServer {

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
}
