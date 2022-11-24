/**
 * Fichier pour générer des mails de pranks
 *
 * @author Grégory Rey-Mermet
 * @author Tim Ernst
 * @author Eric Peronetti
 * Date    24.11.2022
 */
package org.example;

import java.util.LinkedList;
import java.util.Random;

public class MailGenerator {
    /**
     * Liste des emails des personnes à prank
     */
    private final LinkedList<String> emails;

    /**
     * Liste des messages à envoyer
     */
    private final LinkedList<Message> messages;

    /**
     * Nombre de groupe à prank
     */
    private final int nbGroup;

    /**
     * Permet de générer des nombres aléatoires
     */
    private final Random rd;

    /**
     * Constructeur d'un MailGenerator
     * @param emails    Liste des emails des personnes à prank
     * @param messages  Liste des messages à envoyer
     * @param nbGroup   Nombre de groupe à prank
     */
    public MailGenerator(LinkedList<String> emails, LinkedList<Message> messages, int nbGroup) {
        this.emails = emails;
        this.messages = messages;
        this.nbGroup = nbGroup;
        rd = new Random();
    }

    /**
     * Crée un mail complet avec un "sender", plusieurs "recipients" et le message à envoyer
     * @return La liste des différents mails à envoyer
     */
    public LinkedList<Mail> generateMails() {
        LinkedList<Mail> mails = new LinkedList<>();
        LinkedList<LinkedList<String>> groups = getRandomGroups();
        LinkedList<Message> groupsMessages = getRandomMessages();

        for (int i = 0; i < nbGroup; i++) {
            int idxSender = rd.nextInt(groups.get(i).size());
            String sender = groups.get(i).get(idxSender);
            groups.get(i).remove(idxSender);

            mails.add(new Mail(sender, groups.get(i), groupsMessages.get(i)));
        }

        return mails;
    }

    /**
     * Crée nbGroup groupe de façon complètement aléatoire, chaque personne est
     * assignée à un groupe
     * @return La liste comprenant la liste des emails de chaque groupe
     */
    private LinkedList<LinkedList<String>> getRandomGroups() {
        LinkedList<LinkedList<String>> groups = new LinkedList<>();
        if (nbGroup <= 0) {
            throw new RuntimeException("Le nombre de groupe doit être plus grand que 0 !");
        }
        int nbEmailGroup = emails.size() / nbGroup;
        if (nbEmailGroup < 3) {
            throw new RuntimeException("Le nombre de groupe est incorrect, la taille minimale d'un groupe est 3 !");
        }

        for (int i = 0; i < nbGroup; i++) {
            groups.add(new LinkedList<>());
            for (int j = 0; j < nbEmailGroup; j++) {
                int idxEmails = rd.nextInt(emails.size());
                groups.get(i).add(emails.get(idxEmails));
                emails.remove(idxEmails);
            }
        }
        //Si il y a un reste lors de la division assigne aléatoirement à un groupe existant
        for (String email : emails) {
            groups.get(rd.nextInt(groups.size())).add(email);
        }

        return groups;
    }

    /**
     * Récupère une liste de nbGroup messages de façon aléatoire afin de les assigner
     * aux groupes
     * @return La liste des messages
     */
    private LinkedList<Message> getRandomMessages() {
        LinkedList<Message> groupsMessages = new LinkedList<>();

        if (nbGroup > messages.size()) {
            throw new RuntimeException("Il n'y avait pas assez de messages pour tous les groupes !");
        }

        for (int i = 0; i < nbGroup; i++) {
            int idxMessages = rd.nextInt(messages.size());
            groupsMessages.add(messages.get(idxMessages));
            messages.remove(idxMessages);
        }

        return groupsMessages;
    }
}
