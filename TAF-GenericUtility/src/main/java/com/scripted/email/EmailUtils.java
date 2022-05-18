package com.scripted.email;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.FlagTerm;
import com.scripted.dataload.PropertyDriver;

import junit.framework.Assert;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class EmailUtils {

	public static void validateGmailInbox(String messge) throws IOException {
		String emailProp = "\\src\\main\\resources\\Email\\mail.properties";
		PropertyDriver emailProperty = new PropertyDriver();
		emailProperty.setPropFilePath(emailProp);

		String host = PropertyDriver.readProp("mail.imap.host");
		String port = PropertyDriver.readProp("mail.imap.port");
		String mailuser = PropertyDriver.readProp("mail.user");
		String mailpassword = PropertyDriver.readProp("mail.password");
		int intport = Integer.parseInt(port);
		FileReader reader = new FileReader(PropertyDriver.getFilePath());
		Properties prop = new Properties();
		prop.load(reader);
		Session mailSession = Session.getDefaultInstance(prop);
		try {
			Store store = mailSession.getStore("imaps");
			store.connect(host, intport, mailuser, mailpassword);
			Folder inbox = store.getFolder("INBOX");
			inbox.open(Folder.READ_WRITE);
			Flags seen = new Flags(Flags.Flag.SEEN);
			FlagTerm unseenFlagTerm = new FlagTerm(seen, false);
			Message messages[] = inbox.search(unseenFlagTerm);

			// Todo -- > Change the class to generic after POC
			if (messages.length == 0) {
				Assert.fail("There is no unread mails in the inbox");
				System.out.println("No messages found.");
			} else {
				for (int i = 0; i < messages.length; i++) {
					String Fromid = messages[i].getFrom()[0].toString();
					String subject = messages[i].getSubject();
					if (Fromid.equalsIgnoreCase("lk2712.svcs@gmail.com")
							&& subject.equalsIgnoreCase("-No-Reply- Shopping Details")) {
						String body = messages[i].getContent().toString();
						if (body.contains("Your Order has been placed successfully.")) {
							System.out.println("Your Order has been placed successfully.");

						} else {
							Assert.fail(messge + " doesnot contains in the mail");

						}

					}

				}
			}
			
			inbox.close(true);
			store.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}