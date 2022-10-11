package no.kristiania.webstore;

import jakarta.json.Json;
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
                .contains("{\"Product Name\":\"Top Hat\",\"Category\":\"HATS\",\"Price\":1000000}");
    }

    @Test
    void shouldAddProducts() throws IOException {
        var postConnection = openConnection("/api/products");
        postConnection.setRequestMethod("POST");
        postConnection.setDoOutput(true);
        postConnection.getOutputStream().write(
                Json.createObjectBuilder()
                        .add("productName", "Red Brick")
                        .add("category", String.valueOf(productCategory.BRICKS))
                        .add("price", 15)
                        .build()
                        .toString()
                        .getBytes(StandardCharsets.UTF_8)
        );

        assertThat(postConnection.getResponseCode())
                .as(postConnection.getResponseCode() + " " + postConnection.getResponseMessage() + " for " + postConnection.getURL())
                .isEqualTo(200);

        var connection = openConnection("/api/books");
        assertThat(connection.getInputStream())
                .asString(StandardCharsets.UTF_8)
                .contains("{\"Product Name\":\"Red Brick\",\"Category\":\"BRICKS\",\"Price\":15}");
    }

}
