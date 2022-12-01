package smtp;

import mail.Mail;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Classe contenant des fonctions d'un client SMTP permet d'envoyer des mails
 *
 * @author Grégory Rey-Mermet
 * @author Tim Ernst
 * @author Eric Peronetti
 */
public class SmtpClient {
    /**
     * Retour ligne
     */
    private static final String CRLF = "\r\n";

    /**
     * Adresse du serveur
     */
    private final String address;

    /**
     * Port du serveur
     */
    private final int port;

    /**
     * Constructeur d'un SmtpClient
     * @param address   Adresse du serveur
     * @param port      Port du serveur
     */
    public SmtpClient(String address, int port) {
        this.address = address;
        this.port = port;
    }

    /**
     * Permet d'envoyer un mail
     * @param mail Le mail à envoyer
     * @throws IOException Exception pour l'input, l'output et le client
     */
    public void sendMail(Mail mail) throws IOException {
        Socket clientSocket = new Socket(address, port);
        System.out.println("*** Connected to server ***");
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(), StandardCharsets.UTF_8));
        BufferedReader in  = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8));
        parseResponse(in, 220);

        //Extended hello
        out.write("EHLO " + address + CRLF);
        out.flush();
        parseResponse(in);

        //Sender de l'email
        out.write("MAIL FROM:<" + mail.getSender() + ">" + CRLF);
        out.flush();
        parseResponse(in);

        //Recipients de l'email
        for (int i = 0; i < mail.getRecipients().size(); i++) {
            out.write("RCPT TO:<" + mail.getRecipients().get(i) + ">" + CRLF);
            out.flush();
            parseResponse(in);
        }

        //Partie donnée à afficher
        out.write("DATA" + CRLF);
        out.flush();
        parseResponse(in, 354);

        //Encodage de la partie donnée de l'email
        out.write("Content-Type: text/plain; charset=utf-8" + CRLF);

        //Sender de l'email
        out.write("From: " + mail.getSender() + CRLF);

        //Recipients de l'email
        out.write("To: ");
        for (int i = 0; i < mail.getRecipients().size(); i++) {
            out.write((i == 0 ? "" : ", ") + mail.getRecipients().get(i));
        }
        out.write(CRLF);
        out.flush();

        out.write("Subject: =?utf-8?B?" + Base64.getEncoder().encodeToString(mail.getMessage().getSubject().getBytes()) + "?=" + CRLF);
        out.flush();

        out.write(CRLF);
        out.write(mail.getMessage().getText());
        out.write(CRLF + "." + CRLF);
        out.flush();
        parseResponse(in);

        //Fin du message
        out.write("QUIT" + CRLF);
        out.flush();
        parseResponse(in, 221);

        //Nettoyage
        in.close();
        out.close();
        clientSocket.close();
    }


    /**
     * Lis la réponse du serveur et regarde que le code reçu est bien 250
     * @param in Réponse du serveur
     * @throws IOException
     */
    private void parseResponse(BufferedReader in) throws IOException {
        parseResponse(in, 250);
    }

    /**
     * Lis la réponse du serveur et regarde que le code reçu est bien le code
     * attendu
     * @param in Réponse du serveur
     * @param codeExpected Code attendu
     * @throws IOException Si une I/O erreur est levée
     * @throws RuntimeException Si le codeExpected n'est pas le code reçu
     * @throws RuntimeException Si le code reçu n'a pas le bon format
     * @throws NumberFormatException S'il y'a un problème lors de la conversion en int du code
     */
    private void parseResponse(BufferedReader in, int codeExpected) throws IOException, NumberFormatException {
        String line;
        while (true) {
            line = in.readLine();
            System.out.println(line);
            if (line.charAt(3) == ' ' || line.charAt(3) == '-') {
                String code = line.split(String.valueOf(line.charAt(3)))[0];
                if (Integer.parseInt(code) == codeExpected) {
                    if (line.charAt(3) == '-') {
                        continue;
                    }
                    break;
                }

                throw new RuntimeException("Le code reçu n'était pas égal à celui attendu !");
            } else {
                throw new RuntimeException("Le code reçu n'a pas le bon format !");
            }
        }
    }
}