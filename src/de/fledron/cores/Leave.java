package de.fledron.cores;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Leave implements CommandExecutor{

	public Main plugin;

	public Leave(Main plugin) {
		this.plugin = plugin;
	}
	
	public void leave(Player p){
		plugin.removePlayer(p);
		return;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
			if (!(sender instanceof Player)){
				sender.sendMessage("Dieser Befehl ist nur f�r Spieler!");
				return true;
			}
			Player p = (Player)sender;
			int id = plugin.getID(p);
			String team = plugin.getTeam(p);
			plugin.msgToMates(p.getDisplayName() + " verl�sst die Runde.", team, id);
			leave(p);
			return true;
	}
	
}
