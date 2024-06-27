package gift;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class HttpProductHandler implements HttpHandler {
    private final ProductController productController;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public HttpProductHandler(ProductController productController) {
        this.productController = productController;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            String method = exchange.getRequestMethod();
            System.out.println("Received " + method + " request");
            switch (method) {
                case "GET":
                    handleGetRequest(exchange);
                    break;
                default:
                    exchange.sendResponseHeaders(405, -1); // Method Not Allowed
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            exchange.sendResponseHeaders(500, -1); // Internal Server Error
        }
    }

    private void handleGetRequest(HttpExchange exchange) throws IOException {
        List<Product> products = productController.getProducts();
        String responseJson = objectMapper.writeValueAsString(products);

        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, responseJson.getBytes().length);

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(responseJson.getBytes());
        }
    }

}
