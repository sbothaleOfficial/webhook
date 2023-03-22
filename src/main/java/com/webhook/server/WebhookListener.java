package com.webhook.server;

import com.sun.net.httpserver.HttpServer;
import com.webhook.handler.GetHandler;
import com.webhook.handler.PostHandler;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.webhook.repository.UserRepository;

public class WebhookListener {

    private static final int PORT = 8000;

    private HttpServer server;
    private UserRepository userRepository;

    public WebhookListener(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void start() throws IOException {
        server = HttpServer.create(new InetSocketAddress(PORT), 0);
        server.createContext("/get", new GetHandler(userRepository));
        server.createContext("/post", new PostHandler(userRepository));
        server.setExecutor(null);
        server.start();
        System.out.println("Webhook server started on port " + PORT);
    }

    public void stop() {
        if (server != null) {
            server.stop(0);
            System.out.println("Webhook server stopped");
        }
    }
}
