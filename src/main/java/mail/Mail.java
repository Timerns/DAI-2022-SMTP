package mail;

import java.util.LinkedList;

/**
 * Classe pour gérer un mail
 *
 * @author Grégory Rey-Mermet
 * @author Tim Ernst
 * @author Eric Peronetti
 */
public class Mail {
    /**
     * Le "sender"
     */
    private final String sender;

    /**
     * La liste des "recipients"
     */
    private final LinkedList<String> recipients;

    /**
     * Le message à envoyer
     */
    private final Message message;

    /**
     * Constructeur d'un Mail
     * @param sender        Le "sender"
     * @param recipients    La liste des "recipients"
     * @param message       Le message à envoyer
     */
    public Mail(String sender, LinkedList<String> recipients, Message message) {
        this.sender = sender;
        this.recipients = recipients;
        this.message = message;
    }

    /**
     * Permet de récupérer le "sender
     * @return
     */
    public String getSender() {
        return sender;
    }

    /**
     * Permet de récupérer la liste des "recipients"
     * @return
     */
    public LinkedList<String> getRecipients() {
        return recipients;
    }

    /**
     * Permet de récupérer le message
     * @return
     */
    public Message getMessage() {
        return message;
    }
}
