package smtp;

import mail.Message;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.Properties;
import java.util.regex.Pattern;

/**
 * Classe pour récupérer les configurations d'un SMTP
 *
 * @author Grégory Rey-Mermet
 * @author Tim Ernst
 * @author Eric Peronetti
 */
public class SmtpConfig {
    /**
     * Emplacement des ressources
     */
    private final static String RESSOURCES_PATH = "resources/";

    /**
     * Pattern regex pour check le format de l'email
     */
    private final static Pattern PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]*@[A-Za-z0-9.-]*\\.[A-Za-z]*$");

    /**
     * Fichier contenant les configurations
     */
    private final String configFile;

    /**
     * Fichier contenant les emails
     */
    private final String emailsFile;

    /**
     * Fichier contenant les messages
     */
    private final String messagesFile;

    /**
     * Constructeur d'un SmtpConfig
     * @param configFile    Fichier contenant les configurations
     * @param emailsFile    Fichier contenant les emails
     * @param messagesFile  Fichier contenant les messages
     */
    public SmtpConfig(String configFile, String emailsFile, String messagesFile) {
        this.configFile = configFile;
        this.emailsFile = emailsFile;
        this.messagesFile = messagesFile;
    }

    /**
     * Lis les emails depuis le fichier de ressource
     * @return La liste de tous les emails
     */
    public LinkedList<String> getEmails() {
        LinkedList<String> emails = new LinkedList<>();

        try (FileReader fr = new FileReader(RESSOURCES_PATH + emailsFile, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(fr)) {

            String str;
            while ((str = reader.readLine()) != null) {
                if (!PATTERN.matcher(str).find()) {
                    throw new RuntimeException("Le format de l'email est incorrect : " + str);
                }
                emails.add(str);
            }

        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la récupération des emails !");
        }

        return emails;
    }

    /**
     * Lis les messages depuis le fichier de ressource
     * @return La liste de tous les messages
     */
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

    /**
     * Obtiens les propriétés depuis le fichier de configuration
     * @return Un objet contenant les propriétés
     */
    private Properties getProps() {
        Properties props = new Properties();

        try (FileReader reader = new FileReader(RESSOURCES_PATH + configFile, StandardCharsets.UTF_8)) {
            props.load(reader);
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la récupération des configs !");
        }

        return props;
    }

    /**
     * Obtenir l'adresse du serveur
     * @return Adresse du serveur
     */
    public String getServerAddress() {
        return getProps().getProperty("smtpServerAddress", "localhost");
    }

    /**
     * Obtenir le port du serveur
     * @return Port du serveur
     */
    public int getServerPort() {
        try {
            return Integer.parseInt(getProps().getProperty("smtpServerPort", "25"));
        } catch (Exception e) {
            throw new RuntimeException("Le numéro de port n'est pas un nombre !");
        }
    }

    /**
     * Obtenir le nombre de groupe
     * @return Nombre de groupe
     */
    public int getNumberOfGroups() {
        try {
            return Integer.parseInt(getProps().getProperty("numberGroups", "1"));
        } catch (Exception e) {
            throw new RuntimeException("Le nombre de groupes n'est pas un nombre !");
        }
    }
}
