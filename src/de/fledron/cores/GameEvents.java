package de.fledron.cores;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class GameEvents implements Listener{
	
	Main plugin;
	
	public GameEvents(Main plugin){
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		this.plugin = plugin;
	}
	
	@EventHandler
	public void breakBlock(BlockBreakEvent event){
		Location loc = event.getBlock().getLocation();
		int id = plugin.getID(event.getPlayer());
		double x = loc.getX();
		double y = loc.getY();
		double z = loc.getZ();
		double core1x = plugin.getConfig().getInt("cores.arena." + String.valueOf(id) + ".core1.x");
		double core1y = plugin.getConfig().getInt("cores.arena." + String.valueOf(id) + ".core1.y");
		double core1z = plugin.getConfig().getInt("cores.arena." + String.valueOf(id) + ".core1.z");
		double core2x = plugin.getConfig().getInt("cores.arena." + String.valueOf(id) + ".core2.x");
		double core2y = plugin.getConfig().getInt("cores.arena." + String.valueOf(id) + ".core2.y");
		double core2z = plugin.getConfig().getInt("cores.arena." + String.valueOf(id) + ".core2.z");
		double core11x = plugin.getConfig().getInt("cores.arena." + String.valueOf(id) + ".core11.x");
		double core11y = plugin.getConfig().getInt("cores.arena." + String.valueOf(id) + ".core11.y");
		double core11z = plugin.getConfig().getInt("cores.arena." + String.valueOf(id) + ".core11.z");
		double core22x = plugin.getConfig().getInt("cores.arena." + String.valueOf(id) + ".core22.x");
		double core22y = plugin.getConfig().getInt("cores.arena." + String.valueOf(id) + ".core22.y");
		double core22z = plugin.getConfig().getInt("cores.arena." + String.valueOf(id) + ".core22.z");
		if (x == core1x && y == core1y && z == core1z){
			plugin.msgToMates("Der linke blaue Core wurde zerstört!", "Red", id);
			plugin.msgToMates("Der linke blaue Core wurde zerstört!", "Blue", id);
			event.getBlock().setType(Material.AIR);
		}
		if (x == core2x && y == core2y && z == core2z){
			plugin.msgToMates("Der linke rote Core wurde zerstört!", "Red", id);
			plugin.msgToMates("Der linke rote Core wurde zerstört!", "Blue", id);
			event.getBlock().setType(Material.AIR);
		}
		if (x == core11x && y == core11y && z == core11z){
			plugin.msgToMates("Der rechte blaue Core wurde zerstört!", "Red", id);
			plugin.msgToMates("Der rechte blaue Core wurde zerstört!", "Blue", id);
			event.getBlock().setType(Material.AIR);
		}
		if (x == core22x && y == core22y && z == core22z){
			plugin.msgToMates("Der rechte rote Core wurde zerstört!", "Red", id);
			plugin.msgToMates("Der rechte rote Core wurde zerstört!", "Blue", id);
			event.getBlock().setType(Material.AIR);
		}
		Location BlueLeft = new Location(event.getBlock().getWorld(),core1x,core1y,core1z);
		Location BlueRight = new Location(event.getBlock().getWorld(),core11x,core11y,core11z);
		Location RedLeft = new Location(event.getBlock().getWorld(),core2x,core2y,core2z);
		Location RedRight = new Location(event.getBlock().getWorld(),core22x,core22y,core22z);
		if (!BlueLeft.getBlock().getType().equals(Material.BEACON) && !BlueRight.getBlock().getType().equals(Material.BEACON)){
			plugin.msgToMates("Beide blauen Cores wurden zerstört! Rot gewinnt!", "Red", id);
			plugin.msgToMates("Beide blauen Cores wurden zerstört! Rot gewinnt!", "Blue", id);
			if (plugin.games.containsKey(id)){
				plugin.games.get(id).stop();
				RedLeft.getBlock().setType(Material.BEACON);
				RedRight.getBlock().setType(Material.BEACON);
				BlueLeft.getBlock().setType(Material.BEACON);
				BlueRight.getBlock().setType(Material.BEACON);
			}
		}
		if (!RedLeft.getBlock().getType().equals(Material.BEACON) && !RedRight.getBlock().getType().equals(Material.BEACON)){
			plugin.msgToMates("Beide roten Cores wurden zerstört! Blau gewinnt!", "Red", id);
			plugin.msgToMates("Beide roten Cores wurden zerstört! Blau gewinnt!", "Blue", id);
			
			if (plugin.games.containsKey(id)){
				plugin.games.get(id).stop();
				RedLeft.getBlock().setType(Material.BEACON);
				RedRight.getBlock().setType(Material.BEACON);
				BlueLeft.getBlock().setType(Material.BEACON);
				BlueRight.getBlock().setType(Material.BEACON);
			}
			
		}
	}
	
	@EventHandler
	public void move(PlayerMoveEvent event){
		Player p = event.getPlayer();
		int id = plugin.getID(p);
		Location loc = event.getPlayer().getLocation();
		double x = loc.getX();
		double y = loc.getY();
		double z = loc.getZ();
		double core1x = plugin.getConfig().getInt("cores.arena." + String.valueOf(id) + ".core1.x");
		double core1y = plugin.getConfig().getInt("cores.arena." + String.valueOf(id) + ".core1.y");
		double core1z = plugin.getConfig().getInt("cores.arena." + String.valueOf(id) + ".core1.z");
		double core2x = plugin.getConfig().getInt("cores.arena." + String.valueOf(id) + ".core2.x");
		double core2y = plugin.getConfig().getInt("cores.arena." + String.valueOf(id) + ".core2.y");
		double core2z = plugin.getConfig().getInt("cores.arena." + String.valueOf(id) + ".core2.z");
		double core11x = plugin.getConfig().getInt("cores.arena." + String.valueOf(id) + ".core11.x");
		double core11y = plugin.getConfig().getInt("cores.arena." + String.valueOf(id) + ".core11.y");
		double core11z = plugin.getConfig().getInt("cores.arena." + String.valueOf(id) + ".core11.z");
		double core22x = plugin.getConfig().getInt("cores.arena." + String.valueOf(id) + ".core22.x");
		double core22y = plugin.getConfig().getInt("cores.arena." + String.valueOf(id) + ".core22.y");
		double core22z = plugin.getConfig().getInt("cores.arena." + String.valueOf(id) + ".core22.z");
		if (plugin.getTeam(p).equalsIgnoreCase("BLUE")){
			if (plugin.pointDistance(x, y, z, core2x, core2y, core2z) <= 5){
				plugin.msgToMates("Ein Gegner ist in der Nähe deines linken Cores!!!", "RED", id);
			}
			if (plugin.pointDistance(x, y, z, core22x, core22y, core22z) <= 5){
				plugin.msgToMates("Ein Gegner ist in der Nähe deines rechten Cores!!!", "RED", id);
			}
		}else{
			if (plugin.pointDistance(x, y, z, core1x, core1y, core1z) <= 5){
				plugin.msgToMates("Ein Gegner ist in der Nähe deines linken Cores!!!", "BLUE", id);
			}
			if (plugin.pointDistance(x, y, z, core11x, core11y, core11z) <= 5){
				plugin.msgToMates("Ein Gegner ist in der Nähe deines rechten Cores!!!", "BLUE", id);
			}
		}
	}
	
	@EventHandler
	public void death(PlayerDeathEvent event){
		event.setDeathMessage("");
		event.setKeepInventory(true);
		event.setKeepLevel(false);
		event.setNewExp(0);
		event.setDroppedExp(25);
		Player p = (Player)event.getEntity();
		p.setExp(0);
		p.setLevel(0);
		if (!plugin.pid.containsKey(p)){
			return;
		}
		
		if (event.getEntity().getKiller() != null){
			plugin.msgToMates(p.getName() + " wurde von " + 
				event.getEntity().getKiller().getDisplayName() + 
				" getötet.", "BLUE", plugin.getID(p));
			plugin.msgToMates(p.getName() + " wurde von " + 
					event.getEntity().getKiller().getDisplayName() + 
					" getötet.", "RED", plugin.getID(p));
		}else{
			plugin.msgToMates(p.getName() + " ist gestorben.", "BLUE", plugin.getID(p));
			plugin.msgToMates(p.getName() + " ist gestorben.", "RED", plugin.getID(p));
		}

	}
	
	@EventHandler
	public void respawn(PlayerRespawnEvent event){
		if (!plugin.pid.containsKey(event.getPlayer())){
			
			Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
				@Override
				public void run() {
					plugin.tpToLobby(event.getPlayer());
				}
			}, 1);
			return;
		}
		
		Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
			@Override
			public void run() {
				plugin.tpToSpawn(event.getPlayer());
				plugin.equipPlayer(event.getPlayer());
			}
		}, 1);
	}

}
