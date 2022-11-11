package org.example;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

public class SmtpClient {

    Socket clientSocket = null;
    BufferedWriter out = null;
    BufferedReader in = null;

    public SmtpClient(String address, int port){
        initConnectionToServer(address, port);
    }

    public void send(Mail mail) {
        try {
            sendMail(mail);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void initConnectionToServer(String address, int port) {
        try {
            clientSocket = new Socket(address, port);
            System.out.println("*** Connected to server ***");
            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(), StandardCharsets.ISO_8859_1));
            in  = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.ISO_8859_1));
            parseResponce();
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
    }

    private void sendMail(Mail mail) throws IOException {
        out.write("EHLO test.com\r\n");
        out.flush();
        parseResponce();
        out.write("MAIL FROM:<"+ mail.from +">\r\n");
        out.flush();
        parseResponce();
        out.write("RCPT To:<"+ mail.to[0] +">\r\n");
        out.flush();
        parseResponce();
        out.write("DATA\r\n");
        out.flush();
        parseResponce();
        //body and info of mail to send
        out.write("Subject: " + mail.subject + "\r\n");
        out.write("\r\n");
        out.write("je m'appelle tim.\r\n Je suis une autre ligne \r\n mes salutation tim ernst");
        out.write("\r\n.\r\n");
        out.flush();
        parseResponce();
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
