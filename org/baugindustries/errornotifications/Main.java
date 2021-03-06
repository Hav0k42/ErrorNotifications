package org.baugindustries.errornotifications;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Handler;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	ErrorListener errors;
	
	public static void main(String[] args) {
	}
	
	@Override
	public void onEnable() {
		
		
		File defaultDir = new File(this.getDataFolder().getAbsolutePath());
		defaultDir.mkdir();
		
		File configFile = new File(this.getDataFolder() + File.separator + "config.yml");
		 
		 //Check to see if the file already exists. If not, create it.
		 if (!configFile.exists()) {
			 try {
				 configFile.createNewFile();
			 } catch (IOException e) {
				 e.printStackTrace();
			 }
		 }
		 
		 FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
		 if (!config.contains("enabled")) {
			 config.set("enabled", true);
		 }
		 
		 
		 
		 boolean enabled = true;
		 
		 if (!config.contains("recipients")) {
			 enabled = false;
			 config.set("recipients", new ArrayList<String>());
		 }
		 
		 if (config.getString("recipients").length() == 0) {
			 enabled = false;
		 }
		
		 if (enabled) {
			errors = new ErrorListener(this);
			getServer().getLogger().addHandler(errors);
		 } else {
			getServer().getLogger().severe("ErrorNotifications: config.yml is incorrectly configured. Add recipient email addresses using /addrecipient, and relaunch server to enable.");
		 }
		 
		try {
			config.save(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		new ToggleEnable(this);
		new AddRecipient(this);
		new RemoveRecipient(this);
		getCommand("removerecipient").setTabCompleter(new RemoveRecipientTabCompleter(this));
		 
	}
	
	@Override
	public void onDisable() {
		for (Handler h : getServer().getLogger().getHandlers()) {
			if (h instanceof ErrorListener) {
				getServer().getLogger().removeHandler(h);
			}
		}
	}
}
