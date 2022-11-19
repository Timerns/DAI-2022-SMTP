package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.Properties;

public class SmtpConfig {
    private final static String RESSOURCES_PATH = "resources/";

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
            throw new RuntimeException("Erreur lors de la récupération des emails !");
        }

        return emails;
    }

    public LinkedList<Message> getMessages() {
        LinkedList<Message> messages = new LinkedList<>();

        try (FileReader fr = new FileReader(RESSOURCES_PATH + messagesFile, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(fr)) {

            boolean isSubjectLine = true;
            String subject = "No Subject";
            StringBuilder text = new StringBuilder();
            String str;
            while ((str = reader.readLine()) != null) {
                if (isSubjectLine) {
                    isSubjectLine = false;
                    subject = str;
                } else if (str.equals("///")) {
                    isSubjectLine = true;
                    messages.add(new Message(subject, text.toString()));
                    text.setLength(0);
                } else {
                    text.append(str).append("\r\n");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la récupération des messages !");
        }

        return messages;
    }

    private Properties getProps() {
        Properties props = new Properties();

        try (FileReader reader = new FileReader(RESSOURCES_PATH + configFile, StandardCharsets.UTF_8)) {
            props.load(reader);
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la récupération des configs !");
        }

        return props;
    }

    public String getServerAddress() {
        return getProps().getProperty("smtpServerAddress", "localhost");
    }

    public int getServerPort() {
        try {
            return Integer.parseInt(getProps().getProperty("smtpServerPort", "25"));
        } catch (Exception e) {
            throw new RuntimeException("Le numéro de port n'est pas un nombre !");
        }
    }

    public int getNumberOfGroups() {
        try {
            return Integer.parseInt(getProps().getProperty("numberGroups", "1"));
        } catch (Exception e) {
            throw new RuntimeException("Le nombre de groupes n'est pas un nombre !");
        }
    }
}
