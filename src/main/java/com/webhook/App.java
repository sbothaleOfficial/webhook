package com.webhook;

import com.webhook.repository.UserRepository;
import com.webhook.server.WebhookListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class App {

    private static final Logger logger = LogManager.getLogger(App.class);

    public static void main(String[] args) throws IOException {
        logger.info("Starting application");
        UserRepository userRepository = new UserRepository();
        WebhookListener webhookListener = new WebhookListener(userRepository);
        webhookListener.start();
        logger.info("Application started");
    }
}