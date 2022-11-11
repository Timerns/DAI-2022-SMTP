package org.example;

import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        SmtpConfig config = new SmtpConfig("config.properties","emails.utf8", "messages.utf8");
        SmtpClient client = new SmtpClient(config.getServerAddress(), config.getServerPort());
        MailGenerator mg = new MailGenerator(config.getEmails(), config.getMessages(), config.getNumberOfGroups());

//      Mail mail = new Mail( new String[]{"eric@heig-vd.ch", "greg@heig-vd.ch"}, "tim@heig.vd.ch", "Test", "HACKER");
        client.send(mg.generateMails());//?????????????????????? Check si nécessaire d'avoir send et sendMail
    }
}