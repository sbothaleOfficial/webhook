package com.webhook.handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.webhook.model.User;
import com.webhook.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Handler for POST requests to add a new user to the repository.
 */
public class PostHandler implements HttpHandler {
    private final UserRepository userRepository;
    private final Gson gson;
    private static final Logger logger = LogManager.getLogger(PostHandler.class);

    /**
     * Creates a new PostHandler with the specified UserRepository.
     *
     * @param userRepository the UserRepository to use
     */
    public PostHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.gson = new Gson();
    }

    /**
     * Handles a POST request by adding the user specified in the request body to the UserRepository.
     * Responds with a status code and message indicating success or failure.
     *
     * @param exchange the HttpExchange object representing the current HTTP request
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response;
        int responseCode;
        InputStream requestBody = exchange.getRequestBody();
        User user = gson.fromJson(new String(requestBody.readAllBytes()), User.class);
        logger.debug("Handling POST request with body: {}", gson.toJson(user));
        if (user.getId() == 0 || user.getName() == null || user.getLocation() == null) {
            responseCode = 400;
            response = "Invalid user object";
        } else {
            userRepository.addUser(user);
            responseCode = 200;
            response = "User added successfully";
        }
        logger.debug("Sending POST response with code {} and body: {}", responseCode, response);
        exchange.sendResponseHeaders(responseCode, response.getBytes().length);
        OutputStream responseBody = exchange.getResponseBody();
        responseBody.write(response.getBytes());
        responseBody.close();
    }
}