package de.fledron.cores;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Game {

	boolean running;
	int minP;
	int maxP;
	Main plugin;

	public Game(boolean running, int minP, int maxP, Main plugin) {
		this.plugin = plugin;
		this.running = running;
		this.minP = minP;
		this.maxP = maxP;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning() {
		running = true;
	}

	public void setStarting() {
		/*
		 * int ID = 0; for (int id : plugin.games.keySet()) { if
		 * (plugin.games.get(id).equals(this)) { ID = id; } } World world =
		 * null; for (Player p : plugin.pid.keySet()) { world = p.getWorld(); }
		 * final int finalID = ID; final World finalWorld = world;
		 * Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
		 * 
		 * @Override public void run() { plugin.copyMap(finalID, finalWorld); }
		 * }, 20 * 0);
		 */
	}

	public int getMinP() {
		return minP;
	}

	public int getMaxP() {
		return maxP;
	}

	public void stop() {
		running = false;
		int ID = 0;
		for (int id : plugin.games.keySet()) {
			if (plugin.games.get(id).equals(this)) {
				ID = id;
			}
		}
		/*
		 * World world = null; for (Player p : plugin.pid.keySet()) { world =
		 * p.getWorld(); }
		 */
		final int finalID = ID;
		// final World finalWorld = world;
		Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
			@Override
			public void run() {
				/*
				 * Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
				 * 
				 * @Override public void run() { //plugin.pasteMap(finalID,
				 * finalWorld); } }, 20 * 0);
				 */

				for (Player p : plugin.pid.keySet()) {
					if (plugin.pid.get(p).gameID == finalID) {
						plugin.pid.remove(p);
						plugin.tpToLobby(p);
						plugin.getServer().shutdown();
					}
				}
			}
		}, 20 * 5);

	}

}
