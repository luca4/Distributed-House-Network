package core;

import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;

public class Main {

    public static void main(String[] args){
        HttpServer server = null;
        try {
            server = HttpServerFactory.create("http://localhost:1337/");
        } catch (IOException e) {
            System.err.println("Errore nell'avvio del server");
            System.exit(1);
        }
        server.start();

        System.out.println("Server running\n");

        System.out.println("Hit return to stop...");
        try {
            System.in.read();
        } catch (IOException e) { e.printStackTrace();  }
        System.out.println("Stopping server");
        server.stop(0);
        System.out.println("Server stopped");
    }
}
