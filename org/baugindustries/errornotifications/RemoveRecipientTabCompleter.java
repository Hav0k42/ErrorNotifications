package org.baugindustries.errornotifications;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class RemoveRecipientTabCompleter implements TabCompleter {

	Main plugin;
	
	public RemoveRecipientTabCompleter(Main plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
		if (args.length == 1) {
			File configFile = new File(plugin.getDataFolder() + File.separator + "config.yml");
			 
			FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
			
			List<String> recipients = config.getStringList("recipients");
			
			List<String> matches = new ArrayList<String>();
			
			for (String recipient : recipients) {
				if (recipient.toLowerCase().contains(args[0].toLowerCase())) {
					matches.add(recipient);
				}
			}
			
			return matches;
		}
		return null;
	}

}
