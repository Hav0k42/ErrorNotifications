package org.baugindustries.errornotifications;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


public class ErrorListener extends Handler {
	Main plugin;
	
	List<String> recipients;
	String from;
	Properties properties;
	Session session;
	
	public ErrorListener(Main plugin) {
		this.plugin = plugin;

		File configFile = new File(plugin.getDataFolder() + File.separator + "config.yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
		recipients = config.getStringList("recipients");
		from = "spigoterrornotifications@gmail.com";

		properties = new Properties();
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.auth.mechanisms", "XOAUTH2");
		properties.put("mail.smtp.ssl.enable", "true");
		
		session = Session.getInstance(properties);
	}
	
	@Override
	public void publish(LogRecord record) {
		if (record.getLevel().equals(Level.SEVERE)) {
			File configFile = new File(plugin.getDataFolder() + File.separator + "config.yml");
			FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
			if (config.getBoolean("enabled")) {

				
				Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {

			          for (String recipient : recipients) {
					      try {
					          MimeMessage message = new MimeMessage(session);
					          message.setFrom(new InternetAddress(from));
						          message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
					          
					          message.setSubject("Error occured on server");
					          message.setText(record.getMessage());
						      
		
					          Transport.send(message, message.getAllRecipients(), from, getAccessToken());
					          
					          
					       } catch (MessagingException mex) {
					          mex.printStackTrace();
					       }
			          }
				});
			      
			      
			}
			
		}
		
	}

	private String getAccessToken() {//Copied and adapted from: https://kgiann78.github.io/java/gmail/2017/03/16/JavaMail-send-mail-at-Google-with-XOAUTH2/
		String TOKEN_URL = "https://www.googleapis.com/oauth2/v4/token";
		String oauthClientId = "---- REDACTED ----";
		String oauthSecret = "---- REDACTED ----";
		String refreshToken = "---- REDACTED ----";
		String accessToken = "---- REDACTED ----";
		long tokenExpires = 1458168133864L;

		if (System.currentTimeMillis() > tokenExpires) {
		    try {
		        String request = "client_id=" + URLEncoder.encode(oauthClientId, "UTF-8")
		                + "&client_secret=" + URLEncoder.encode(oauthSecret, "UTF-8")
		                + "&refresh_token=" + URLEncoder.encode(refreshToken, "UTF-8")
		                + "&grant_type=refresh_token";
		        HttpURLConnection conn = (HttpURLConnection) new URL(TOKEN_URL).openConnection();
		        conn.setDoOutput(true);
		        conn.setRequestMethod("POST");
		        PrintWriter out = new PrintWriter(conn.getOutputStream());
		        out.print(request); // note: println causes error
		        out.flush();
		        out.close();
		        conn.connect();
		        try {
		            HashMap<String, Object> result;
		            result = new ObjectMapper().readValue(conn.getInputStream(), new TypeReference<HashMap<String, Object>>() {
		            });
		            accessToken = (String) result.get("access_token");
		            tokenExpires = System.currentTimeMillis() + (((Number) result.get("expires_in")).intValue() * 1000);
		        } catch (IOException e) {
		            String line;
		            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		            while ((line = in.readLine()) != null) {
		                System.out.println(line);
		            }
		            System.out.flush();
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		}
		return accessToken;
	}
	
	@Override
	public void flush() {
		
	}

	@Override
	public void close() throws SecurityException {
		
	}

}
