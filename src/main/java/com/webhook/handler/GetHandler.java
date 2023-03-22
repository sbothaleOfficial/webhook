package com.webhook.handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.webhook.model.User;
import com.webhook.repository.UserRepository;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public class GetHandler implements HttpHandler {
    private final UserRepository userRepository;
    private final Gson gson;

    public GetHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.gson = new Gson();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response;
        int responseCode;
        Map<String, String> queryParams = QueryParser.parse(exchange.getRequestURI().getQuery());
        if (queryParams.isEmpty()) {
            List<User> users = userRepository.getUsers();
            responseCode = 200;
            response = gson.toJson(users);
        } else if (queryParams.containsKey("id")) {
            Long id = Long.parseLong(queryParams.get("id"));
            User user = userRepository.getUserById(id);
            if (user == null) {
                responseCode = 404;
                response = "User not found";
            } else {
                responseCode = 200;
                response = gson.toJson(user);
            }
        } else if (queryParams.containsKey("name")) {
            String name = queryParams.get("name");
            List<User> users = userRepository.getUsersByName(name);
            if (users.isEmpty()) {
                responseCode = 404;
                response = "Users not found";
            } else {
                responseCode = 200;
                response = gson.toJson(users);
            }
        } else if (queryParams.containsKey("location")) {
            String location = queryParams.get("location");
            List<User> users = userRepository.getUsersByLocation(location);
            if (users.isEmpty()) {
                responseCode = 404;
                response = "Users not found";
            } else {
                responseCode = 200;
                response = gson.toJson(users);
            }
        } else {
            responseCode = 400;
            response = "Invalid query parameter";
        }
        exchange.sendResponseHeaders(responseCode, response.getBytes().length);
        OutputStream responseBody = exchange.getResponseBody();
        responseBody.write(response.getBytes());
        responseBody.close();
    }
}