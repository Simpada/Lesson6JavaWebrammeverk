package no.kristiania.library;

import jakarta.servlet.DispatcherType;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.EnumSet;

public class LibraryServer {

    private static final Logger logger = LoggerFactory.getLogger(LibraryServer.class);

    private final Server server;

    public LibraryServer(int port) throws IOException {
        server = new Server(port);
        server.setHandler(createWebApp());
    }

    private static WebAppContext createWebApp() throws IOException {
        var webContext = new WebAppContext();
        webContext.setContextPath("/");

        var resources = Resource.newClassPathResource("/webappDemo");
        var sourceDirectory = new File(resources.getFile().getAbsoluteFile().toString()
                .replace('\\', '/')
                .replace("target/classes","src/main/resources"));

        if (sourceDirectory.isDirectory()) {
            webContext.setBaseResource(Resource.newResource(sourceDirectory));
            // Unlocks files when using jetty IMPORTANT
            webContext.setInitParameter(DefaultServlet.CONTEXT_INIT + "useFileMappedBuffer", "false");
        } else {
            webContext.setBaseResource(resources);
        }


        var jerseyServlet = webContext.addServlet(ServletContainer.class, "/api/*");
        // Might not be necessary
        //jerseyServlet.setInitOrder(0);
        jerseyServlet.setInitParameter("jersey.config.server.provider.packages", "no.kristiania.library");

        webContext.addFilter(new FilterHolder(new LibraryFilter()), "/*", EnumSet.of(DispatcherType.REQUEST));

        return webContext;
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
