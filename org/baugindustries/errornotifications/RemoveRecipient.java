package org.baugindustries.errornotifications;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;


public class RemoveRecipient implements CommandExecutor {
	
	private Main plugin;
	
	public RemoveRecipient(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("removerecipient").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players may execute this command.");
			return true;
		}
		
		Player player = (Player)sender;
		
		if (args.length == 0) {
			player.sendMessage(ChatColor.RED + "Incorrect Usage: Enter destination email address to remove.");
			return true;
		}
		
		
		if (player.hasPermission("minecraft.command.op")) {//admin only
			File configFile = new File(plugin.getDataFolder() + File.separator + "config.yml");
			 
			FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
			
			List<String> recipients = config.getStringList("recipients");
			if (recipients.contains(args[0])) {
				recipients.remove(args[0]);
				player.sendMessage(ChatColor.GOLD + "Removed recipient email: " + args[0]);
			} else {
				player.sendMessage(ChatColor.RED + "Incorrect Usage: Address not found in config.yml.");
			}
			
			config.set("recipients", recipients);
			
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
