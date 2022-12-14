import mail.Mail;
import mail.MailGenerator;
import smtp.SmtpClient;
import smtp.SmtpConfig;

/**
 * Classe qui effectue automatiquement des emails de pranks sur une liste de personne
 *
 * @author Grégory Rey-Mermet
 * @author Tim Ernst
 * @author Eric Peronetti
 */
public class Main {
    public static void main(String[] args) {
        SmtpConfig config = new SmtpConfig("config.properties", "emails.utf8", "messages.utf8");

        try {
            SmtpClient client = new SmtpClient(config.getServerAddress(), config.getServerPort());
            MailGenerator mg = new MailGenerator(config.getEmails(), config.getMessages(), config.getNumberOfGroups());

            for (Mail mail : mg.generateMails()) {
                client.sendMail(mail);
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}