package org.example;

import java.util.List;
import java.util.Objects;

public class MailGenerator {
    List<String> emails;
    List<String> messages;

    public MailGenerator(List<String> emails, List<String> messages) {
        this.emails = emails;
        this.messages = messages;
    }

    public List<Objects> generateMail() {
        return null;
    }

    private List<List<String>> getRandomGroup(int nbGroup) {
        return null;
    }

    private String getRandomMessage() {
        return "";
    }
}
