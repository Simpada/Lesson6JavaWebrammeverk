package no.kristiania.library;

import org.eclipse.jetty.server.Server;

import java.net.MalformedURLException;
import java.net.URL;

public class LibraryServer {

    private final Server server;

    public LibraryServer(int port) {
        server = new Server(0);
    }

    public URL getURL() throws MalformedURLException {

        return server.getURI().toURL();
    }

    public void start() throws Exception {
        server.start();
    }
}