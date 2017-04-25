package de.fledron.cores;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ForceJoin implements CommandExecutor{

	public Main plugin;

	public ForceJoin(Main plugin) {
		this.plugin = plugin;
	}
	
	public void join(Player p, String team, int id){
		plugin.addPlayer(id, team, p);
		plugin.start(id);
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 3){
			
			if (args[0].equals("@a")){
				for (Player player : plugin.getServer().getOnlinePlayers()) {
					if (sender.hasPermission("cores.join") && (args[1].equalsIgnoreCase("BLUE") || args[1].equalsIgnoreCase("RED"))){
						join(player, args[1], Integer.valueOf(args[2]));
						plugin.msgToMates(player.getDisplayName() + " ist Team " + args[1] + " gejoint.", args[1], Integer.valueOf(args[2]));
						}else{
							sender.sendMessage("Du hast keine Berechtigung.");
							return true;
						}
						
				}
				return true;
			}
			if (args[0].equals("@p")){
				args[0] = sender.getName();
			}
			Player player = plugin.getServer().getPlayer(args[0]);
			if (player == null){
				sender.sendMessage("Der Spieler existiert nicht");
				return true;
			}
			if (sender.hasPermission("cores.join") && (args[1].equalsIgnoreCase("BLUE") || args[1].equalsIgnoreCase("RED"))){
			join(player, args[1], Integer.valueOf(args[2]));
			plugin.msgToMates(player.getDisplayName() + " ist Team " + args[1] + " gejoint.", args[1], Integer.valueOf(args[2]));
			}else{
				sender.sendMessage("Du hast keine Berechtigung.");
			}
			return true;
			}else{
				return false;
			}
	}
	
}
