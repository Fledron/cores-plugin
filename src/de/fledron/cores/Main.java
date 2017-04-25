package de.fledron.cores;

import java.util.HashMap;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

@SuppressWarnings("deprecation")
public class Main extends JavaPlugin implements Listener {

	String PREFIX = "[Cores] ";
	HashMap<Player, PlayerGameInfo> pid = new HashMap<Player, PlayerGameInfo>();
	HashMap<Integer, Game> games = new HashMap<Integer, Game>();
	// HashMap<Location,Blocks> blocks = new HashMap<Location,Blocks>();
	LeaveEvent leave;
	int gameCount;
	Inventory invRed;
	Inventory invBlue;

	Scoreboard board;
	Team BLUE;
	Team RED;

	ItemStack leder1 = new ItemStack(Material.AIR);
	ItemStack leder2 = new ItemStack(Material.AIR);
	ItemStack leder3 = new ItemStack(Material.AIR);
	ItemStack sword = new ItemStack(Material.AIR);
	ItemStack axe = new ItemStack(Material.AIR);
	ItemStack pickaxe = new ItemStack(Material.AIR);
	ItemStack wood = new ItemStack(Material.AIR);
	ItemStack lederRed = new ItemStack(Material.AIR);
	ItemStack lederBlue = new ItemStack(Material.AIR);

	ConfigAccessor conf = new ConfigAccessor(this, "blockConfig.yml");

	public void onDisable() {
		for (World world : Bukkit.getWorlds()) {
			rollback(world.getName());
		}

	}

	// Unloading maps, to rollback maps. Will delete all player builds until
	// last server save
	public static void unloadMap(String mapname) {
		if (Bukkit.getServer().unloadWorld(Bukkit.getServer().getWorld(mapname), false)) {
			Bukkit.getLogger().log(Level.FINE, "UNLOADED " + mapname + " SUCCESSFULLY");
		} else {
			Bukkit.getLogger().log(Level.WARNING, "COULD NOT UNLOAD " + mapname);
		}
	}

	// Loading maps (MUST BE CALLED AFTER UNLOAD MAPS TO FINISH THE ROLLBACK
	// PROCESS)
	public static void loadMap(String mapname) {
		Bukkit.getServer().createWorld(new WorldCreator(mapname));
	}

	// Maprollback method, because were too lazy to type 2 lines
	public static void rollback(String mapname) {
		unloadMap(mapname);
		loadMap(mapname);
	}

	public void createGameWorlds() {
		if (Bukkit.getWorld("coresWorld") != null && Bukkit.getWorld("coresLobby") != null) {
			Bukkit.getLogger().log(Level.INFO, "Everything fine with the Cores Worlds.");
		} else {
			Bukkit.getServer().createWorld(new WorldCreator("coresWorld"));
			Bukkit.getServer().createWorld(new WorldCreator("coresLobby"));
			Bukkit.getLogger().log(Level.INFO, "Created Game Worlds 'coresWorld' and 'coresLobby'");
		}
	}

	public void onEnable() {

		board = Bukkit.getScoreboardManager().getMainScoreboard();
		createGameWorlds();
		for (World world : Bukkit.getWorlds()) {
			world.setAutoSave(false);
		}
		this.getServer().getPluginManager().registerEvents(this, this);
		new LeaveEvent(this);
		new GameEvents(this);
		this.getConfig();
		conf.getConfig();
		CreateCoresArena exec = new CreateCoresArena(this);
		ForceJoin exec2 = new ForceJoin(this);
		Join exec3 = new Join(this);
		Leave exec4 = new Leave(this);
		SetSpawn exec5 = new SetSpawn(this);
		SetCore exec6 = new SetCore(this);
		Cores exec7 = new Cores(this);
		SetArena exec8 = new SetArena(this);
		getCommand("create").setExecutor(exec);
		getCommand("forcejoin").setExecutor(exec2);
		getCommand("join").setExecutor(exec3);
		getCommand("leave").setExecutor(exec4);
		getCommand("setspawn").setExecutor(exec5);
		getCommand("setcore").setExecutor(exec6);
		getCommand("cores").setExecutor(exec7);
		getCommand("setarena").setExecutor(exec8);

		int d = 1;
		conf.getConfig().options().header("Do not change this. This is for saving RAM.");
		this.getConfig().options().header("Cores Config");
		this.getConfig().set("cores.status", "very well");
		this.saveConfig();
		conf.saveConfig();
		while (this.getConfig().isSet("cores.arena." + String.valueOf(d))) {
			int minP = this.getConfig().getInt("cores.arena." + String.valueOf(d) + ".minp");
			int maxP = this.getConfig().getInt("cores.arena." + String.valueOf(d) + ".maxp");
			Game game = new Game(false, minP, maxP, this);
			games.put(d, game);
			d += 1;
		}
		gameCount = d - 1;

		invRed = Bukkit.createInventory(null, 36);
		invBlue = Bukkit.createInventory(null, 36);

		leder1.setType(Material.LEATHER_BOOTS);
		leder2.setType(Material.LEATHER_HELMET);
		leder3.setType(Material.LEATHER_LEGGINGS);
		sword.setType(Material.STONE_SWORD);
		axe.setType(Material.STONE_AXE);
		pickaxe.setType(Material.STONE_PICKAXE);
		wood.setType(Material.LOG);
		wood.setAmount(64);

		lederRed.setType(Material.LEATHER_CHESTPLATE);
		LeatherArmorMeta lam = (LeatherArmorMeta) lederRed.getItemMeta();
		lam.setColor(Color.fromRGB(255, 0, 0));
		lederRed.setItemMeta(lam);
		lederBlue.setType(Material.LEATHER_CHESTPLATE);
		LeatherArmorMeta lam2 = (LeatherArmorMeta) lederBlue.getItemMeta();
		lam2.setColor(Color.fromRGB(0, 0, 255));
		lederBlue.setItemMeta(lam2);

		invRed.addItem(leder1, leder2, leder3, sword, axe, pickaxe, wood, lederRed);
		invBlue.addItem(leder1, leder2, leder3, sword, axe, pickaxe, wood, lederBlue);

		regTeams();

	}

	public void regTeams() {
		for (Team t : board.getTeams()) {
			t.unregister();
		}
		BLUE = board.registerNewTeam("Blue");
		BLUE.setPrefix(ChatColor.BLUE + "[BLUE]" + ChatColor.RESET);
		BLUE.setCanSeeFriendlyInvisibles(true);
		BLUE.setAllowFriendlyFire(false);
		BLUE.setNameTagVisibility(NameTagVisibility.ALWAYS);
		BLUE.setDisplayName(ChatColor.BLUE + "[BLUE]" + ChatColor.RESET);

		RED = board.registerNewTeam("Red");
		RED.setPrefix(ChatColor.RED + "[RED]" + ChatColor.RESET);
		RED.setCanSeeFriendlyInvisibles(true);
		RED.setAllowFriendlyFire(false);
		RED.setNameTagVisibility(NameTagVisibility.ALWAYS);
		RED.setDisplayName(ChatColor.RED + "[RED]" + ChatColor.RESET);
	}

	@EventHandler
	public void join(PlayerJoinEvent event) {
		event.setJoinMessage("");
		msgToMates(event.getPlayer().getDisplayName() + " betritt das Spiel.", "", 0);
		event.getPlayer().setGameMode(GameMode.ADVENTURE);
	}

	@EventHandler
	public void chat(PlayerChatEvent event) {
		Player p = event.getPlayer();
		String msg = event.getMessage();
		int id = getID(p);
		String team = getTeam(p);
		if (team.equalsIgnoreCase("BLUE")) {
			msg = ChatColor.BLUE + msg;
		}
		if (team.equalsIgnoreCase("RED")) {
			msg = ChatColor.RED + msg;
		}
		msg = "»" + p.getName() + "« " + msg;
		msg = msg + ChatColor.RESET;
		event.setCancelled(true);
		msgToMates(msg, team, id);
	}

	public int getID(Player p) {
		if (pid.containsKey(p)) {
			PlayerGameInfo info = pid.get(p);
			return info.gameID;
		} else {
			return 0;
		}
	}

	public String getTeam(Player p) {
		if (pid.containsKey(p)) {
			PlayerGameInfo info = pid.get(p);
			return info.team;
		} else {
			return "";
		}
	}

	public void tpToSpawn(Player p) {
		String team = getTeam(p);
		int ID = getID(p);
		Location loc = p.getLocation();
		double x;
		double y;
		double z;
		if (team.equalsIgnoreCase("BLUE")) {
			x = this.getConfig().getInt("cores.arena." + String.valueOf(ID) + ".x1");
			y = this.getConfig().getInt("cores.arena." + String.valueOf(ID) + ".y1");
			z = this.getConfig().getInt("cores.arena." + String.valueOf(ID) + ".z1");
			loc.setX(x + 0.5);
			loc.setY(y);
			loc.setZ(z + 0.5);
		} else {
			x = this.getConfig().getInt("cores.arena." + String.valueOf(ID) + ".x2");
			y = this.getConfig().getInt("cores.arena." + String.valueOf(ID) + ".y2");
			z = this.getConfig().getInt("cores.arena." + String.valueOf(ID) + ".z2");
			loc.setX(x + 0.5);
			loc.setY(y);
			loc.setZ(z + 0.5);
		}
		p.teleport(loc);
	}

	public void tpToLobby(Player p) {

		clearFull(p);
		Location loc = p.getLocation();
		double x;
		double y;
		double z;
		x = this.getConfig().getInt("cores.lobby.x");
		y = this.getConfig().getInt("cores.lobby.y");
		z = this.getConfig().getInt("cores.lobby.z");
		loc.setX(x + 0.5);
		loc.setY(y);
		loc.setZ(z + 0.5);
		p.teleport(loc);
	}

	public void addPlayer(int id, String team, Player p) {
		pid.put(p, new PlayerGameInfo(team, id));
		if (team.equalsIgnoreCase("Blue")) {
			if (BLUE == null || RED == null) {
				regTeams();
			}
			BLUE.addPlayer(Bukkit.getOfflinePlayer(p.getName()));
		} else if (team.equalsIgnoreCase("Red")) {
			RED.addPlayer(Bukkit.getOfflinePlayer(p.getName()));
		}
	}

	public void removePlayer(Player p) {
		pid.remove(p);
	}

	public void clearFull(Player player) {
		player.getInventory().setHelmet(null);
		player.getInventory().setChestplate(null);
		player.getInventory().setLeggings(null);
		player.getInventory().setBoots(null);
		player.getInventory().clear();
	}

	public void equipPlayer(Player p) {
		clearFull(p);
		p.setGameMode(GameMode.SURVIVAL);
		if (getTeam(p).equalsIgnoreCase("Red")) {
			p.getInventory().addItem(sword, axe, pickaxe, wood, leder1, leder2, leder3, lederRed);
		} else {
			p.getInventory().addItem(sword, axe, pickaxe, wood, leder1, leder2, leder3, lederBlue);
		}

	}

	public void start(int id) {
		if (!games.containsKey(id)) {
			return;
		}
		Game game = games.get(id);
		int minP = game.minP;
		int nowP = 0;
		for (PlayerGameInfo info : pid.values()) {
			if (info.gameID == id) {
				nowP += 1;
			}
		}

		if (nowP == minP) {
			// start
			Bukkit.getScheduler().runTaskLater(this, new Runnable() {
				@Override
				public void run() {
					game.setRunning();
					for (Player p : pid.keySet()) {
						if (pid.get(p).gameID == id) {
							p.sendMessage("Die Runde beginnt jetzt!");
							tpToSpawn(p);
							equipPlayer(p);
						}
					}
				}
			}, 20 * 60);
			Bukkit.getScheduler().runTaskLater(this, new Runnable() {
				@Override
				public void run() {
					for (Player p : pid.keySet()) {
						if (pid.get(p).gameID == id) {
							p.sendMessage("Die Runde beginnt in 10 Sekunden!");
						}
					}
				}
			}, 20 * 50);
			Bukkit.getScheduler().runTaskLater(this, new Runnable() {
				@Override
				public void run() {
					for (Player p : pid.keySet()) {
						if (pid.get(p).gameID == id) {
							p.sendMessage("Die Runde beginnt in 30 Sekunden!");
						}
					}
				}
			}, 20 * 30);
			Bukkit.getScheduler().runTaskLater(this, new Runnable() {
				@Override
				public void run() {
					game.setStarting();
					for (Player p : pid.keySet()) {
						if (pid.get(p).gameID == id) {
							p.sendMessage("Die Runde beginnt in 60 Sekunden!");
						}
					}
				}
			}, 20 * 0);

		}
	}

	public double pointDistance(double x1, double y1, double z1, double x2, double y2, double z2) {
		double One = x1 + y1 + z1;
		double Two = x2 + y2 + z2;
		double diff = One - Two;
		if (diff < 0) {
			diff = diff * (-1);
		}
		return diff;

	}

	public void log(String message) {
		this.getLogger().info(PREFIX + message);
	}

	public void msgToAll(String message) {
		for (Player p : this.getServer().getOnlinePlayers()) {
			p.sendMessage(message);
		}
	}

	public void msgToMates(String msg, String team, int id) {
		for (Player player : this.getServer().getOnlinePlayers()) {
			if (team.equalsIgnoreCase(getTeam(player)) && id == getID(player)) {
				player.sendMessage(msg);
			}
		}
	}

	public void copyMap(int id, World world) {
		return;
		/*
		 * String d = String.valueOf(id); int x1 =
		 * this.getConfig().getInt("cores.arena." + d + ".border.x1"); int y1 =
		 * this.getConfig().getInt("cores.arena." + d + ".border.y1"); int z1 =
		 * this.getConfig().getInt("cores.arena." + d + ".border.z1"); int x2 =
		 * this.getConfig().getInt("cores.arena." + d + ".border.x2"); int y2 =
		 * this.getConfig().getInt("cores.arena." + d + ".border.y2"); int z2 =
		 * this.getConfig().getInt("cores.arena." + d + ".border.z2");
		 * SchematicAPI sch = new SchematicAPI(this); Location loc = new
		 * Location(world, 0, 0, 0); Location loc2 = new Location(world, 0, 0,
		 * 0); loc.setX((double) x1); loc.setY((double) y1); loc.setZ((double)
		 * z1); loc2.setX((double) x2); loc2.setY((double) y2);
		 * loc2.setZ((double) z2); Block block = loc.getBlock(); Block block2 =
		 * loc2.getBlock(); int[][][] areaType = sch.getStructureType(block,
		 * block2); byte[][][] areaData = sch.getStructureData(block, block2);
		 * sch.saveType(d, areaType); sch.saveData(d, areaData);
		 */
	}

	public void pasteMap(int id, World world) {
		return;
		/*
		 * String d = String.valueOf(id); int x1 =
		 * this.getConfig().getInt("cores.arena." + d + ".border.x1"); int y1 =
		 * this.getConfig().getInt("cores.arena." + d + ".border.y1"); int z1 =
		 * this.getConfig().getInt("cores.arena." + d + ".border.z1"); int x2 =
		 * this.getConfig().getInt("cores.arena." + d + ".border.x2"); int y2 =
		 * this.getConfig().getInt("cores.arena." + d + ".border.y2"); int z2 =
		 * this.getConfig().getInt("cores.arena." + d + ".border.z2");
		 * SchematicAPI sch = new SchematicAPI(this); Location loc = new
		 * Location(world, 0, 0, 0); Location loc2 = new Location(world, 0, 0,
		 * 0); loc.setX((double) x1); loc.setY((double) y1); loc.setZ((double)
		 * z1); loc2.setX((double) x2); loc2.setY((double) y2);
		 * loc2.setZ((double) z2); Block block = loc.getBlock(); // Block block2
		 * = loc2.getBlock(); int[][][] areaType = sch.loadType(d); byte[][][]
		 * areaData = sch.loadData(d); sch.paste(areaType, block.getLocation());
		 * sch.pasteData(areaData, block.getLocation());
		 */
	}
}
