package com.webhook.handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.webhook.model.User;
import com.webhook.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * Handles HTTP GET requests and returns user data based on the query parameters.
 */
public class GetHandler implements HttpHandler {
    private final UserRepository userRepository;
    private final Gson gson;
    private static final Logger logger = LogManager.getLogger(GetHandler.class);

    /**
     * Constructs a new instance of the GetHandler class with the specified UserRepository.
     *
     * @param userRepository The UserRepository instance to use.
     */
    public GetHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.gson = new Gson();
    }

    /**
     * Handles the HTTP GET request and returns the appropriate response based on the query parameters.
     *
     * @param exchange The HttpExchange instance representing the request and response objects.
     * @throws IOException if an error occurs while reading or writing the request/response.
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response;
        int responseCode;
        Map<String, String> queryParams = QueryParser.parse(exchange.getRequestURI().getQuery());
        logger.debug("Handling GET request with query parameters: {}", queryParams);
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
        logger.debug("Sending GET response with code {} and body: {}", responseCode, response);
        exchange.sendResponseHeaders(responseCode, response.getBytes().length);
        OutputStream responseBody = exchange.getResponseBody();
        responseBody.write(response.getBytes());
        responseBody.close();
    }
}