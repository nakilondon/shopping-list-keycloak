package net.lewes.shoppinglist.services;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.lewes.shoppinglist.domain.Product;
import net.lewes.shoppinglist.domain.ProductResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class ProductService {
    public Product findProduct(String barcode)
    {
        String url = "https://world.openfoodfacts.org/api/v2/product/" + barcode;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = null;
        ProductResponse productResponse = null;

        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Product unknownProduct = new Product(barcode, "Unknown barcode: " + barcode);
            System.out.println("Unknown product: " + unknownProduct);
            return unknownProduct;
        }

        // get the information
        String responseString = response.body();

        try {
            productResponse = new ObjectMapper()
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                    .readValue(responseString, ProductResponse.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Product unknownProduct = new Product(barcode, "Unknown barcode: " + barcode);
            System.out.println("Unknown product: " + unknownProduct);
            return unknownProduct;
        }

        if (productResponse.getProduct() == null)
        {
            Product unknownProduct = new Product(barcode, "Unknown barcode: " + barcode);
            System.out.println("Unknown product: " + unknownProduct);
            return unknownProduct;
        }

        return productResponse.getProduct();
    }
}
