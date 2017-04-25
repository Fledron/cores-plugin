package de.fledron.cores;

import java.util.Collection;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Join implements CommandExecutor{

	public Main plugin;

	public Join(Main plugin) {
		this.plugin = plugin;
	}
	
	public String join(Player p, int id){
		Collection<PlayerGameInfo> info = plugin.pid.values();
		int blue = 0;
		int red = 0;
		for (PlayerGameInfo infor : info){
			if (infor.team.equalsIgnoreCase("Blue")){
				blue += 1;
			}
			if (infor.team.equalsIgnoreCase("Red")){
				red += 1;
			}
		}
		
		for (Player pl : plugin.pid.keySet()){
			if (plugin.pid.containsKey(pl)){
				if (plugin.pid.get(pl).gameID != 0){
					p.sendMessage("Du bist bereits in einer Runde.");
					return "";
				}
			}
		}
		
		String team;
		if (blue > red){
			team = "Red";
		}else{
			team = "Blue";
		}
		if (plugin.games.containsKey(id)){
			
			Game f = plugin.games.get(id);
			if (f.running == true){
				int players = 0;
				for (PlayerGameInfo infoz : plugin.pid.values()){
					if (infoz.gameID == id){
						players += 1;
					}
				}
				if (players == 0){
					f.running = false;
				}else{
					p.sendMessage("Diese Runde läuft bereits.");
					return "";
				}
			}
			
			if (red+blue >= plugin.getConfig().getInt("cores.arena." + id + ".maxp")){
				p.sendMessage("Diese Runde ist leider schon voll.");
				return "";
			}
		}else{
			p.sendMessage("Diese Runde existiert nicht.");
			return "";
		}
		plugin.addPlayer(id, team, p);
		plugin.start(id);
		return team;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 1){
			
			if (!(sender instanceof Player)){
				sender.sendMessage("Dieser Befehl ist nur für Spieler!");
				return true;
			}
			Player p = (Player)sender;
			int id = Integer.valueOf(args[0]);
			String team = join(p, id);
			if (team != ""){
				plugin.msgToMates(p.getDisplayName() + " ist Team " + team + " gejoint.", team, id);
			}
			return true;
			}else{
				return false;
			}
	}
	
}
