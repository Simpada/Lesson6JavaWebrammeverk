package no.kristiania.webstore;

import jakarta.json.Json;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

import java.io.StringReader;
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

    @POST
    public Response addProduct(String body) {
        var jsonProduct = Json.createReader(new StringReader(body)).readObject();
        var product = new product(
                jsonProduct.getString("Product Name"),
                productCategory.valueOf(jsonProduct.getString("Category")),
                jsonProduct.getInt("Price")
        );
        products.add(product);

        return Response.ok().build();
    }

}
