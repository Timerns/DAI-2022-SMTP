package org.example;

public class Message {
    private final String subject;
    private final String text;

    public Message(String subject, String text) {
        this.subject = subject;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public String getSubject() {
        return subject;
    }
}
