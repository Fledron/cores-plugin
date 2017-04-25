package de.fledron.cores;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CreateCoresArena implements CommandExecutor{

	Main plugin;

	public CreateCoresArena(Main plugin) {
		this.plugin = plugin;
	}
	
	public void create(int x1, int y1, int z1, int x2, int y2, int z2, 
			int maxP, int minP, int id){
		String d = String.valueOf(id);
		plugin.getConfig().set("cores.status", "ganz gut");
		plugin.getConfig().set("cores.arena." + d + ".x1", x1);
		plugin.getConfig().set("cores.arena." + d + ".y1", y1);
		plugin.getConfig().set("cores.arena." + d + ".z1", z1);
		plugin.getConfig().set("cores.arena." + d + ".x2", x2);
		plugin.getConfig().set("cores.arena." + d + ".y2", y2);
		plugin.getConfig().set("cores.arena." + d + ".z2", z2);
		plugin.getConfig().set("cores.arena." + d + ".maxp", maxP);
		plugin.getConfig().set("cores.arena." + d + ".minp", minP);
		plugin.saveConfig();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 9){
			if (sender.hasPermission("cores.create")){
				int x1 = Integer.valueOf(args[0]);
				int y1 = Integer.valueOf(args[1]);
				int z1 = Integer.valueOf(args[2]);
				int x2 = Integer.valueOf(args[3]);
				int y2 = Integer.valueOf(args[4]);
				int z2 = Integer.valueOf(args[5]);
				int maxP = Integer.valueOf(args[6]);
				int minP = Integer.valueOf(args[7]);
				int id = Integer.valueOf(args[8]);
				if (id == 0){
					sender.sendMessage("Du darfst nicht null benutzen.");
					return true;
				}
				create(x1,y1,z1,x2,y2,z2,maxP,minP,id);
				sender.sendMessage("Die Arena Nummer " + String.valueOf(id) + " wurde erfolgreich erstellt.");
			}else{
				sender.sendMessage("Du hast keine Berechtigung.");
			}
			return true;
			}else{
				return false;
			}
	}
	
}
