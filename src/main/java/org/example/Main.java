package org.example;

public class Main {
    public static void main(String[] args) {
        SmtpClient client = new SmtpClient("localhost", 25);
        Mail mail = new Mail( new String[]{"eric@heig-vd.ch", "greg@heig-vd.ch"}, "tim@heig.vd.ch", "Test", "HACKER");
        client.send(mail);
    }
}