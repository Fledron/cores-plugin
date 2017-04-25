package de.fledron.cores;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SetCore implements CommandExecutor{

	public Main plugin;

	public SetCore(Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
			
		if (args.length != 13){
			return false;
		}
			
		if (sender.hasPermission("cores.create")){
			int x1 = Integer.valueOf(args[0]);
			int y1 = Integer.valueOf(args[1]);
			int z1 = Integer.valueOf(args[2]);
			int x2 = Integer.valueOf(args[3]);
			int y2 = Integer.valueOf(args[4]);
			int z2 = Integer.valueOf(args[5]);
			int x11 = Integer.valueOf(args[6]);
			int y11 = Integer.valueOf(args[7]);
			int z11 = Integer.valueOf(args[8]);
			int x22 = Integer.valueOf(args[9]);
			int y22 = Integer.valueOf(args[10]);
			int z22 = Integer.valueOf(args[11]);
			int id = Integer.valueOf(args[12]);
			
			String d = String.valueOf(id);
			plugin.getConfig().set("cores.arena." + d + ".core1.x", x1);
			plugin.getConfig().set("cores.arena." + d + ".core1.y", y1);
			plugin.getConfig().set("cores.arena." + d + ".core1.z", z1);
			plugin.getConfig().set("cores.arena." + d + ".core2.x", x2);
			plugin.getConfig().set("cores.arena." + d + ".core2.y", y2);
			plugin.getConfig().set("cores.arena." + d + ".core2.z", z2);
			plugin.getConfig().set("cores.arena." + d + ".core11.x", x11);
			plugin.getConfig().set("cores.arena." + d + ".core11.y", y11);
			plugin.getConfig().set("cores.arena." + d + ".core11.z", z11);
			plugin.getConfig().set("cores.arena." + d + ".core22.x", x22);
			plugin.getConfig().set("cores.arena." + d + ".core22.y", y22);
			plugin.getConfig().set("cores.arena." + d + ".core22.z", z22);
			plugin.saveConfig();
			
			sender.sendMessage("Die Cores wurden erfolgreich gesetzt.");
		}else{
			sender.sendMessage("Du hast keine Berechtigung.");
		}
			
		return true;
	}
	
}
