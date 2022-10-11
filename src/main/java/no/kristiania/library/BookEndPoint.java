package no.kristiania.library;

import jakarta.json.Json;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@Path("/books")
public class BookEndPoint {

    private static final List<Book> books = new ArrayList<>();
    static {
        var exampleBook = new Book("Java in a nutshell", "David Flanagan");
        books.add(exampleBook);
    }

    @GET
    public Response getAllBooks() {

        var result = Json.createArrayBuilder();
        for (var book : books) {
            result.add(Json.createObjectBuilder()
                    .add("title", book.title())
                    .add("author", book.author())
            );
        }

        return Response.ok(result.build().toString()).build();
    }

    @POST
    public Response addBook(String body) {
        var jsonBook = Json.createReader(new StringReader(body)).readObject();
        var book = new Book(
                jsonBook.getString("author"),
                jsonBook.getString("title")
        );
        books.add(book);

        return Response.ok().build();
    }

}
