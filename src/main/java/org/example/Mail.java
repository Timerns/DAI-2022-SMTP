package org.example;

import java.util.LinkedList;

public class Mail {
    private final String sender;
    private final LinkedList<String> recipients;
    private final Message message;

    public Mail(String sender, LinkedList<String> recipients, Message message) {
        this.sender = sender;
        this.recipients = recipients;
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public LinkedList<String> getRecipients() {
        return recipients;
    }

    public Message getMessage() {
        return message;
    }
}
