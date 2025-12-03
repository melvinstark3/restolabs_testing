package org.example;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.UnsupportedEncodingException;

public class emailReport {
    private static final String SENDER_EMAIL = "";
    private static final String SENDER_PASSWORD = "";
    private static final String RECIPIENT_LIST = "";

    public static void send(String logs) {
	String senderName = "Kartik's Automation Bot";
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SENDER_EMAIL, SENDER_PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SENDER_EMAIL, senderName));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(RECIPIENT_LIST));
            message.setSubject("Basic Cases Automation Report");
            message.setText("Basic Cases Tests Completed.\n\n" +
                    "--------------------------------------\n" +
                    "LOGS:\n" + logs);
            Transport.send(message);
        } catch (MessagingException | UnsupportedEncodingException e) {
            System.err.println(" Logs Email Failed: " + e.getMessage());
	    e.printStackTrace();
        }
    }
}
