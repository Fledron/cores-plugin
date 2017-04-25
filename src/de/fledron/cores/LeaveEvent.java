package de.fledron.cores;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class LeaveEvent implements Listener{
	
	Main plugin;
	
	public LeaveEvent(Main plugin){
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		this.plugin = plugin;
	}
	
	@EventHandler
	public void leave(PlayerQuitEvent event){
		Player p = event.getPlayer();
		event.setQuitMessage("");
		plugin.msgToMates(p.getName() + " verlässt das Spiel.", plugin.getTeam(p), plugin.getID(p));
		plugin.removePlayer(p);
	}

}
