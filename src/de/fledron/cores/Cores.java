package de.fledron.cores;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Cores implements CommandExecutor{

	public Main plugin;

	public Cores(Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
			
		if (sender instanceof Player){
			Player p = (Player)sender;
			int id = plugin.getID(p);
			String team = plugin.getTeam(p);
			if (id != 0){
			sender.sendMessage("Du bist in Runde " + String.valueOf(id) + ", und im Team " + team);
			}
		}
			
			int running = 0;
			for (Game info : plugin.games.values()){
				if (info.running == true){
					running += 1;
				}
			}
			sender.sendMessage("Momentan gibt es " + plugin.gameCount + " Arenen. In " + 
					String.valueOf(running) + " Arenen wird gerade gespielt.");
			
			return true;
	}
	
}
