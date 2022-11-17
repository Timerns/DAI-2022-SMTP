package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class SmtpConfig {
    private final static String RESSOURCES_PATH = "src/main/resources/";

    private final String configFile;
    private final String emailsFile;
    private final String messagesFile;

    public SmtpConfig(String configFile, String emailsFile, String messagesFile) {
        this.configFile = configFile;
        this.emailsFile = emailsFile;
        this.messagesFile = messagesFile;
    }

    public LinkedList<String> getEmails() {
        LinkedList<String> emails = new LinkedList<>();

        try (FileReader fr = new FileReader(RESSOURCES_PATH + emailsFile, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(fr)) {

            String str;
            while ((str = reader.readLine()) != null) {
                emails.add(str);
            }

        } catch (IOException e) {
            throw new RuntimeException("Error while getting the emails !");
        }

        return emails;
    }

    public LinkedList<String> getMessages() {
        LinkedList<String> messages = new LinkedList<>();

        try (FileReader fr = new FileReader(RESSOURCES_PATH + messagesFile, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(fr)) {

            StringBuilder message = new StringBuilder();
            String str;
            while ((str = reader.readLine()) != null) {
                if (str.equals("///")) {
                    messages.add(message.toString());
                    message.setLength(0);
                } else {
                    message.append(str).append("\r\n");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error while getting the messages !");
        }

        return messages;
    }

    private Properties getProps() {
        Properties props = new Properties();

        try (FileReader reader = new FileReader(RESSOURCES_PATH + configFile, StandardCharsets.UTF_8)) {
            props.load(reader);
        } catch (IOException e) {
            throw new RuntimeException("Error while getting the properties !");
        }

        return props;
    }

    public String getServerAddress() {
        return getProps().getProperty("smtpServerAddress", "localhost");
    }

    public int getServerPort() {
        return Integer.parseInt(getProps().getProperty("smtpServerPort", "25"));
    }

    public int getNumberOfGroups() {
        return Integer.parseInt(getProps().getProperty("numberGroups", "1"));
    }
}
