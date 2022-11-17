package org.example;

import java.io.IOException;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        SmtpConfig config = new SmtpConfig("config.properties","emails.utf8", "messages.utf8");
        SmtpClient client = new SmtpClient(config.getServerAddress(), config.getServerPort());
        MailGenerator mg = new MailGenerator(config.getEmails(), config.getMessages(), config.getNumberOfGroups());
//        Content-Type: multipart/related; type="text/html";
//        Mail mail = new Mail( "tim@heig.vd.ch", new LinkedList<String>({"eric@heig-vd.ch", "greg@heig-vd.ch"}), "Test", "HACKER");

        try {
            client.sendMail(mg.generateMails());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}