package org.baugindustries.errornotifications;

import java.io.File;
import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;


public class ToggleEnable implements CommandExecutor {
	
	private Main plugin;
	
	public ToggleEnable(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("toggle").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players may execute this command.");
			return true;
		}
		
		Player player = (Player)sender;
		
		if (player.hasPermission("minecraft.command.op")) {//admin only
			File configFile = new File(plugin.getDataFolder() + File.separator + "config.yml");
			 
			FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
			
			config.set("enabled", !config.getBoolean("enabled"));
			if (config.getBoolean("enabled")) {
				player.sendMessage(ChatColor.YELLOW + "ErrorNotifcations is now" + ChatColor.GREEN + " enabled.");
			} else {
				player.sendMessage(ChatColor.YELLOW + "ErrorNotifcations is now" + ChatColor.RED + " disabled.");
			}
			
			try {
				config.save(configFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return true;
		}
		return true;
	}
}
