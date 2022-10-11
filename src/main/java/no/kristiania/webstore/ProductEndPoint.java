package no.kristiania.webstore;

import jakarta.json.Json;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

@Path("/products")
public class ProductEndPoint {

    private static final List<product> products = new ArrayList<>();
    static {
        var exampleProduct = new product("Top Hat", productCategory.HATS, 1000000);
        products.add(exampleProduct);
    }

    @GET
    public Response getAllProducts() {
        var result = Json.createArrayBuilder();
        for (var product : products) {
            result.add(Json.createObjectBuilder()
                    .add("Product Name", product.productName())
                    .add("Category", product.category().toString())
                    .add("Price", product.price())
            );
        }

        return Response.ok(result.build().toString()).build();
    }

}
