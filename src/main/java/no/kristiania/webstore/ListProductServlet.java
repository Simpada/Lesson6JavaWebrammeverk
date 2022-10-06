package no.kristiania.webstore;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class ListProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException{
        var exampleProduct = new Product();
        exampleProduct.setProductName("A dapper top hat");
        exampleProduct.setCategory(ProductCategory.HATS);
        exampleProduct.setPrice(100);
        var products = List.of(exampleProduct);

        JsonArrayBuilder result = Json.createArrayBuilder();
        for (Product product : products) {
            result.add(Json.createObjectBuilder()
                    .add("Product: ", product.getProductName())
                    .add("Category: ", product.getCategory().toString().toLowerCase(Locale.ROOT))
                    .add("Price: ", product.getPrice())
            );
        }
        res.getWriter().write(result.build().toString());
    }

}
