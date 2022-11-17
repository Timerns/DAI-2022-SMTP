package org.example;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;

public class SmtpClient {

    Socket clientSocket = null;
    BufferedWriter out = null;
    BufferedReader in = null;

    public SmtpClient(String address, int port) throws Exception {
        initConnectionToServer(address, port);
    }

    private void initConnectionToServer(String address, int port) throws Exception {
        clientSocket = new Socket(address, port);
        System.out.println("*** Connected to server ***");
        out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(), StandardCharsets.ISO_8859_1));
        in  = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.ISO_8859_1));
        parseResponce();

    }

    public void sendMail(LinkedList<Mail> mails) throws IOException {
        out.write("EHLO test.com\r\n");
        out.flush();
        parseResponce();
        for (Mail mail: mails) {
            out.write("MAIL FROM:<" + mail.getSender() + ">\r\n");
            out.flush();
            parseResponce();
            for (int i = 0; i < mail.getRecipients().size(); i++) {
                out.write("RCPT To:<" + mail.getRecipients().get(i) + ">\r\n");
                System.out.println(mail.getRecipients().get(i));
                out.flush();
                parseResponce();
            }
            out.write("DATA\r\n");
            out.flush();
            parseResponce();
            //body and info of mail to send
            //out.write("Bcc: <" +  mail.to[0] + ">\r\n");
            //out.write("Subject: " + mail.subject + "\r\n");
            //out.write("\r\n");
            out.write(mail.getMessage());
            out.write("\r\n.\r\n");
            out.flush();
            parseResponce();
        }
        out.write("QUIT\r\n");
        out.flush();
        parseResponce();

    }

    private void parseResponce() throws IOException {
        String line;
        do {
            line = in.readLine();
            System.out.println(line);
        } while (line.charAt(4) == ' ');
    }
}
