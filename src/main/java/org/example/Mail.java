package org.example;

public class Mail {
    public String[] to;
    public String from;
    public String subject;
    public String body;

    public Mail(String[] to, String from, String subject, String body){
        this.to = to;
        this.from = from;
        this.subject = subject;
        this.body = body;
    }
}
