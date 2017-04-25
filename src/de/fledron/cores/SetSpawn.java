package de.fledron.cores;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SetSpawn implements CommandExecutor{

	public Main plugin;

	public SetSpawn(Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
			
		if (args.length != 3){
			return false;
		}
			
		if (sender.hasPermission("cores.create")){
			int x = Integer.valueOf(args[0]);
			int y = Integer.valueOf(args[1]);
			int z = Integer.valueOf(args[2]);
			
			plugin.getConfig().set("cores.lobby.x", x);
			plugin.getConfig().set("cores.lobby.y", y);
			plugin.getConfig().set("cores.lobby.z", z);
			plugin.saveConfig();
			
			sender.sendMessage("Der Lobby-Spawn wurde erfolgreich erstellt.");
		}else{
			sender.sendMessage("Du hast keine Berechtigung.");
		}
			
		return true;
	}
	
}
