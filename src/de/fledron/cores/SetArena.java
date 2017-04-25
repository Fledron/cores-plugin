package de.fledron.cores;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SetArena implements CommandExecutor{

	public Main plugin;

	public SetArena(Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
			
		if (args.length != 7){
			return false;
		}
			
		if (sender.hasPermission("cores.create")){
			int x1 = Integer.valueOf(args[0]);
			int y1 = Integer.valueOf(args[1]);
			int z1 = Integer.valueOf(args[2]);
			int x2 = Integer.valueOf(args[3]);
			int y2 = Integer.valueOf(args[4]);
			int z2 = Integer.valueOf(args[5]);
			int id = Integer.valueOf(args[6]);
			
			String d = String.valueOf(id);
			plugin.getConfig().set("cores.arena." + d + ".border.x1", x1);
			plugin.getConfig().set("cores.arena." + d + ".border.y1", y1);
			plugin.getConfig().set("cores.arena." + d + ".border.z1", z1);
			plugin.getConfig().set("cores.arena." + d + ".border.x2", x2);
			plugin.getConfig().set("cores.arena." + d + ".border.y2", y2);
			plugin.getConfig().set("cores.arena." + d + ".border.z2", z2);
			plugin.saveConfig();
			
			sender.sendMessage("Die Arena-Grenze wurde erfolgreich erstellt.");
		}else{
			sender.sendMessage("Du hast keine Berechtigung.");
		}
			
		return true;
	}
	
}
