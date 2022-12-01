package mail;

/**
 * Classe pour gérer un message
 *
 * @author Grégory Rey-Mermet
 * @author Tim Ernst
 * @author Eric Peronetti
 */
public class Message {
    /**
     * Sujet du message
     */
    private final String subject;

    /**
     * Contenu du message
     */
    private final String text;

    /**
     * Constructeur d'un Message
     * @param subject   Sujet du message
     * @param text      Contenu du message
     */
    public Message(String subject, String text) {
        this.subject = subject;
        this.text = text;
    }

    /**
     * Permet de récupérer le contenu du message
     * @return Le contenu du message
     */
    public String getText() {
        return text;
    }

    /**
     * Permet de récupérer le sujet du message
     * @return Le sujet du message
     */
    public String getSubject() {
        return subject;
    }
}
