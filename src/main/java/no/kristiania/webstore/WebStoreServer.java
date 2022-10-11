package no.kristiania.webstore;

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

public class WebStoreServer {

    private static final Logger logger = LoggerFactory.getLogger(WebStoreServer.class);

    private final Server server;

    public WebStoreServer (int port) throws IOException {
        server = new Server(port);
        server.setHandler(createWebApp());
    }

    private static WebAppContext createWebApp() throws IOException {
        var webContext = new WebAppContext();
        webContext.setContextPath("/");

        var resources = Resource.newClassPathResource("/webapp");
        var sourceDirectory = new File(resources.getFile().getAbsoluteFile()
                .toString()
                .replace('\\', '/')
                .replace("target/classes", "src/main/resources"));

        if (sourceDirectory.isDirectory()) {
            webContext.setBaseResource(Resource.newResource(sourceDirectory));
            webContext.setInitParameter(DefaultServlet.CONTEXT_INIT + "useFileMappedBuffer", "false");
        } else {
            webContext.setBaseResource(resources);
        }

        var jerseyServlet = webContext.addServlet(ServletContainer.class, "/api/*");
        jerseyServlet.setInitParameter("jersey.config.server.provider.packages", "no.kristiania.webstore");

        webContext.addFilter(new FilterHolder(new webstoreFilter()), "/*", EnumSet.of(DispatcherType.REQUEST));

        return webContext;
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
