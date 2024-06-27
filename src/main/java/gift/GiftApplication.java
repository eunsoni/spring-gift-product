package gift;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class GiftApplication {
    public static void main(String[] args) throws IOException {
        ProductController productController = new ProductController();

        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/api/products", new HttpProductHandler(productController));
        server.start();

        System.out.println("Server is listening on port 8000");
    }
}

