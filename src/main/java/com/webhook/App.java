package com.webhook;

import com.webhook.repository.UserRepository;
import com.webhook.server.WebhookListener;

import java.io.IOException;


public class App 
{
    public static void main(String[] args) throws IOException {
        UserRepository userRepository = new UserRepository();
        WebhookListener webhookListener = new WebhookListener(userRepository);
        webhookListener.start();
    }
}
