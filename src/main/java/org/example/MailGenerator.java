package org.example;

import java.util.LinkedList;
import java.util.Random;

public class MailGenerator {
    private final LinkedList<String> emails;
    private final LinkedList<String> messages;
    private final int nbGroup;

    private final Random rd;

    public MailGenerator(LinkedList<String> emails, LinkedList<String> messages, int nbGroup) {
        this.emails = emails;
        this.messages = messages;
        this.nbGroup = nbGroup;
        rd = new Random();
    }

    public LinkedList<Mail> generateMails() {
        LinkedList<Mail> mails = new LinkedList<>();
        LinkedList<LinkedList<String>> groups = getRandomGroups();
        LinkedList<String> groupsMessages = getRandomMessages();

        for (int i = 0; i < nbGroup; i++) {
            int idxSender = rd.nextInt(groups.get(i).size());
            String sender = groups.get(i).get(idxSender);
            groups.get(i).remove(idxSender);

            mails.add(new Mail(sender, groups.get(i), groupsMessages.get(i)));
        }

        return mails;
    }

    private LinkedList<LinkedList<String>> getRandomGroups() {
        LinkedList<LinkedList<String>> groups = new LinkedList<>();
        // Exception ????????????????????????????? pour empecher d'avoir des groupes de moins de 2 personnes
        int nbEmailGroup = Math.max(2, emails.size() / nbGroup);

        for (int i = 0; i < nbGroup || emails.size() == 0; i++) {
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

    private LinkedList<String> getRandomMessages() {
        LinkedList<String> groupsMessages = new LinkedList<>();
        // Vérifier si assez de message pour le nombre de groupe ????????????????????
        for (int i = 0; i < nbGroup; i++) {
            int idxMessages = rd.nextInt(messages.size());
            groupsMessages.add(messages.get(idxMessages));
            messages.remove(idxMessages);
        }

        return groupsMessages;
    }
}
