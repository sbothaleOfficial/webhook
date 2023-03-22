package com.webhook.handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.webhook.model.User;
import com.webhook.repository.UserRepository;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class PostHandler implements HttpHandler {
    private final UserRepository userRepository;
    private final Gson gson;

    public PostHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.gson = new Gson();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response;
        int responseCode;
        InputStream requestBody = exchange.getRequestBody();
        User user = gson.fromJson(new String(requestBody.readAllBytes()), User.class);
        if (user.getId() == 0 || user.getName() == null || user.getLocation() == null) {
            responseCode = 400;
            response = "Invalid user object";
        } else {
            userRepository.addUser(user);
            responseCode = 200;
            response = "User added successfully";
        }
        exchange.sendResponseHeaders(responseCode, response.getBytes().length);
        OutputStream responseBody = exchange.getResponseBody();
        responseBody.write(response.getBytes());
        responseBody.close();
    }
}