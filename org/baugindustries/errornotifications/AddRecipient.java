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


public class AddRecipient implements CommandExecutor {
	
	private Main plugin;
	
	public AddRecipient(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("addrecipient").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players may execute this command.");
			return true;
		}
		
		Player player = (Player)sender;
		
		if (args.length == 0) {
			player.sendMessage(ChatColor.RED + "Incorrect Usage: Enter destination email address to add.");
			return true;
		}
		
		
		if (player.hasPermission("minecraft.command.op")) {//admin only
			File configFile = new File(plugin.getDataFolder() + File.separator + "config.yml");
			 
			FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
			
			List<String> recipients = config.getStringList("recipients");
			recipients.add(args[0]);
			
			player.sendMessage(ChatColor.GREEN + "Added recipient email: " + args[0]);
			
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
