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

    public LinkedList<Mail> generateMail() {
        LinkedList<Mail> mails = new LinkedList<>();
        LinkedList<LinkedList<String>> groups = getRandomGroups();
        LinkedList<String> groupsMessages = getRandomMessages();

        for (int i = 0; i < nbGroup; i++) {
            int idxSender = rd.nextInt(emails.size());
            String sender = groups.get(i).get(idxSender);
            groups.get(i).remove(idxSender);

            mails.add(new Mail(/*sender, groups.get(i), groupsMessages.get(i)*/));
        }

        return mails;
    }

    private LinkedList<LinkedList<String>> getRandomGroups() {
        LinkedList<LinkedList<String>> groups = new LinkedList<>();
        for (int i = 0; i < nbGroup; i++) {
            groups.add(new LinkedList<>());
            int idxEmails = rd.nextInt(emails.size());
            groups.get(i).add(emails.get(idxEmails));
            emails.remove(idxEmails);
        }

        return groups;
    }

    private LinkedList<String> getRandomMessages() {
        LinkedList<String> groupsMessages = new LinkedList<>();
        for (int i = 0; i < nbGroup; i++) {
            int idxMessages = rd.nextInt(messages.size());
            messages.add(messages.get(idxMessages));
            emails.remove(idxMessages);
        }

        return groupsMessages;
    }
}
