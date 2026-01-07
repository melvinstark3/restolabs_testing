package org.example.core;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import java.io.UnsupportedEncodingException;

public class emailReport {
    private static final String SENDER_EMAIL = "shivam@7cloudtech.com";
    private static final String SENDER_PASSWORD = "";
    private static final String RECIPIENT_LIST = "kartik@restolabs.com, shivam@restolabs.com";

    public static void send(String automationStatus, String logs, String attachmentPath) {
        String module = System.getProperty("module");
	    String senderName = "Automation Bot";
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
            message.setSubject("[" + automationStatus + "] Automation Report " + module + " Module");

            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText("Module: " + module + " Tests Completed.\n\n" +
                    "--------------------------------------\n" +
                    "LOGS:\n" + logs);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            if (attachmentPath != null) {
                messageBodyPart = new MimeBodyPart();
                DataSource source = new FileDataSource(attachmentPath);
                messageBodyPart.setDataHandler(new DataHandler(source));
                messageBodyPart.setFileName(new java.io.File(attachmentPath).getName());
                multipart.addBodyPart(messageBodyPart);
            }
            message.setContent(multipart);
            Transport.send(message);
        } catch (MessagingException | UnsupportedEncodingException e) {
            System.err.println(" Logs Email Failed: " + e.getMessage());
	    e.printStackTrace();
        }
    }
}
