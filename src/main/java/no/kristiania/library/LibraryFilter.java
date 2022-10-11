package no.kristiania.library;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class LibraryFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(LibraryFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        logger.debug("Request {} {}", req.getMethod(), req.getRequestURI());

        chain.doFilter(request, response);

    }
}
