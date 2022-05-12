package org.baugindustries.errornotifications;

import java.io.File;
import java.util.Properties;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ErrorListener extends Handler {
	Main plugin;
	
	String to;
	String from;
	String host;
	Properties properties;
	Session session;
	
	public ErrorListener(Main plugin) {
		this.plugin = plugin;

		File configFile = new File(plugin.getDataFolder() + File.separator + "config.yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
		to = config.getString("toEmail");
		from = config.getString("fromEmail");
		host = "localhost";

		properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		
		session = Session.getInstance(properties, new Authenticator() {
		    @Override
		    protected PasswordAuthentication getPasswordAuthentication() {
		        return new PasswordAuthentication(from, config.getString("emailPass"));
		    }
		});
	}
	
	@Override
	public void publish(LogRecord record) {
		if (record.getLevel().equals(Level.SEVERE)) {
			File configFile = new File(plugin.getDataFolder() + File.separator + "config.yml");
			FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
			if (config.getBoolean("enabled")) {

				
				Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
				
			      try {
			          MimeMessage message = new MimeMessage(session);
			          message.setFrom(new InternetAddress(from));
			          message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			          message.setSubject("Error occured on server");
			          message.setText(record.getMessage());
			          Transport.send(message);
			       } catch (MessagingException mex) {
			          mex.printStackTrace();
			       }
				});
			      
			      
			}
			
		}
		
	}

	@Override
	public void flush() {
		
	}

	@Override
	public void close() throws SecurityException {
		
	}

}
