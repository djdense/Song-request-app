

import jakarta.mail.*;
import jakarta.mail.search.FlagTerm;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class EmailReader {

    public static void main(String[] args) {
        final String host = "imap.gmail.com";
        final String username = "contact@absynthdj.com";
        final String password = "bwfv wbzc ummk vqwe";
        final String folderName = "INBOX";
        final String subjectFilter = "Song Request";

        try {
            Properties properties = new Properties();
            properties.put("mail.store.protocol", "imap");
            properties.put("mail.imap.host", host);
            properties.put("mail.imap.port", "993");
            properties.put("mail.imap.ssl.enable", "true");

            Session session = Session.getDefaultInstance(properties);
            Store store = session.getStore("imap");
            store.connect(host, username, password);

            System.out.println("Connected to email successfully ✔️");

            Folder inbox = store.getFolder(folderName);
            inbox.open(Folder.READ_ONLY);

            Message[] messages = inbox.search(
                    new FlagTerm(new Flags(Flags.Flag.SEEN), false)
            );

            System.out.println("Checking unread emails...\n");

            for (Message msg : messages) {
                if (msg.getSubject() != null &&
                        msg.getSubject().contains(subjectFilter)) {

                    System.out.println("🎵 SONG REQUEST FOUND!");
                    System.out.println("From: " + msg.getFrom()[0]);
                    System.out.println("Subject: " + msg.getSubject());
                    System.out.println("Message:");

                    Object content = msg.getContent();
                    if (content instanceof String text) {
                        System.out.println(text);
                    } else {
                        System.out.println("[Non-text content]");
                    }

                    System.out.println("-----------------------------------------------------\n");
                }
            }

            inbox.close(false);
            store.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
