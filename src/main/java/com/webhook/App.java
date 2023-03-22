package com.webhook;

import com.webhook.repository.UserRepository;
import com.webhook.server.WebhookListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * The main entry point for the Webhook application.
 */
public class App {

    private static final Logger logger = LogManager.getLogger(App.class);

    /**
     * Starts the Webhook application.
     *
     * @param args The command line arguments.
     * @throws IOException If there is an error starting the HTTP server.
     */
    public static void main(String[] args) throws IOException {
        logger.info("Starting application");
        UserRepository userRepository = new UserRepository();
        WebhookListener webhookListener = new WebhookListener(userRepository);
        webhookListener.start();
        logger.info("Application started");
    }
}