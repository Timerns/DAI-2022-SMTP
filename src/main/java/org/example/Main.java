package org.example;

import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        SmtpClient client = new SmtpClient("localhost", 25);
        SmtpConfig config = new SmtpConfig("config.properties","emails.utf8", "messages.utf8");
        MailGenerator mg = new MailGenerator((LinkedList<String>) config.getEmails(), (LinkedList<String>) config.getMessages(), 1);
//        Mail mail = new Mail( new String[]{"eric@heig-vd.ch", "greg@heig-vd.ch"}, "tim@heig.vd.ch", "Test", "HACKER");
        client.send(mg.generateMail());
    }
}