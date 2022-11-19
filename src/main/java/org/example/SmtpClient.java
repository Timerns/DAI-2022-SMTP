package org.example;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class SmtpClient {
    private static final String RET = "\r\n";
    String address;
    int port;

    public SmtpClient(String address, int port) {
        this.address = address;
        this.port = port;
    }

    public void sendMail(Mail mail) throws IOException {
        Socket clientSocket = new Socket(address, port);
        System.out.println("*** Connected to server ***");
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(), StandardCharsets.UTF_8));
        BufferedReader in  = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8));
        parseResponse(in);

        out.write("EHLO " + address + RET);
        out.flush();
        parseResponse(in);

        out.write("MAIL FROM:<" + mail.getSender() + ">" + RET);
        out.flush();
        parseResponse(in);

        for (int i = 0; i < mail.getRecipients().size(); i++) {
            out.write("RCPT TO:<" + mail.getRecipients().get(i) + ">" + RET);
            out.flush();
            parseResponse(in);
        }

        out.write("DATA\r\n");
        out.flush();
        parseResponse(in);

        out.write("From: " + mail.getSender() + RET);

        out.write("To: ");
        for (int i = 0; i < mail.getRecipients().size(); i++) {
            out.write((i == 0 ? "" : ", ") + mail.getRecipients().get(i));
        }
        out.write(RET);
        out.flush();
        parseResponse(in);

        out.write("Subject: " + mail.getMessage().getSubject() + RET);
        out.flush();
        parseResponse(in);

        //body and info of mail to send
        //out.write("Bcc: <" +  mail.to[0] + ">\r\n");
        //out.write("Subject: " + mail.subject + "\r\n");
        //out.write("\r\n");
        out.write(RET);
        out.write(mail.getMessage().getText());
        out.write(RET + "." + RET);
        out.flush();
        parseResponse(in);

        out.write("QUIT" + RET);
        out.flush();
        parseResponse(in);

        in.close();
        out.close();
        clientSocket.close();
    }

    private void parseResponse(BufferedReader in) throws IOException {
        String line;
        do {
            line = in.readLine();
            System.out.println(line);
        } while (line.charAt(4) == '-');
    }
}
