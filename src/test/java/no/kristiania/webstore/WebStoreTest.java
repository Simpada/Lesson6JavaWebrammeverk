package no.kristiania.webstore;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class WebStoreTest {


    private WebStoreServer server;

    @BeforeEach
    void setUp() throws Exception {
        server = new WebStoreServer(0);
        server.start();
    }

    private HttpURLConnection openConnection(String spec) throws IOException {
        return (HttpURLConnection) new URL(server.getUrl(), spec).openConnection();
    }

    @Test
    void shouldServerHomePage() throws IOException {
        var connection = openConnection("/");

        assertThat(connection.getResponseCode())
                .as(connection.getResponseCode() + " " + connection.getResponseMessage() + " for " + connection.getURL())
                .isEqualTo(200);
        assertThat(connection.getInputStream())
                .asString(StandardCharsets.UTF_8)
                .contains("<title>Simpimpin Goods and Wares</title>");
    }

    @Test
    void shouldListProducts() throws IOException {
        var connection = openConnection("api/products");

        assertThat(connection.getResponseCode())
                .as(connection.getResponseCode() + " " + connection.getResponseMessage() + " for " + connection.getURL())
                .isEqualTo(200);
        assertThat(connection.getInputStream())
                .asString(StandardCharsets.UTF_8)
                .contains("{\"productName\":\"Top Hat\",\"category\":\"HATS\", \"price\":\"1000000\"}");
    }

}
