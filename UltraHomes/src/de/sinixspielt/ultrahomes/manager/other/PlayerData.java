package de.sinixspielt.ultrahomes.manager.other;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import de.sinixspielt.ultrahomes.UltraHomes;
import de.sinixspielt.ultrahomes.file.FileBase;

/*
Class created on 03.03.2019 by SinixSpielt
 * */

public class PlayerData extends FileBase {
	
	private UUID uuid;
	private Map<String, Location> homes;
	private int homeLimit;
	
	public PlayerData(UUID uuid) {

		super(File.separator + "playerdata", uuid.toString());
		this.uuid = uuid;
		this.homes = new HashMap<String, Location>();
		loadHomeLimit();
		loadHomes();
	}
	
	private void loadHomeLimit() {
		Player target = Bukkit.getPlayer(uuid);
		if (target != null) {
			if (target.hasPermission(UltraHomes.getFileManager().getConfigFile().getConfig().getString("CONIG.ULTRAHOMES.UNLIMETED.PERMISSIONS"))) {
				this.homeLimit = -1;
			} else if (target.hasPermission(UltraHomes.getFileManager().getConfigFile().getConfig().getString("CONIG.ULTRAHOMES.1.PERMISSIONS"))) {
				this.homeLimit = UltraHomes.getFileManager().getConfigFile().getConfig().getInt("CONIG.ULTRAHOMES.1.HOMES");
			} else if (target.hasPermission(UltraHomes.getFileManager().getConfigFile().getConfig().getString("CONIG.ULTRAHOMES.2.PERMISSIONS"))) {
				this.homeLimit = UltraHomes.getFileManager().getConfigFile().getConfig().getInt("CONIG.ULTRAHOMES.2.HOMES");
			} else if (target.hasPermission(UltraHomes.getFileManager().getConfigFile().getConfig().getString("CONIG.ULTRAHOMES.3.PERMISSIONS"))) {
				this.homeLimit = UltraHomes.getFileManager().getConfigFile().getConfig().getInt("CONIG.ULTRAHOMES.3.HOMES");
			} else if (target.hasPermission(UltraHomes.getFileManager().getConfigFile().getConfig().getString("CONIG.ULTRAHOMES.4.PERMISSIONS"))) {
				this.homeLimit = UltraHomes.getFileManager().getConfigFile().getConfig().getInt("CONIG.ULTRAHOMES.4.HOMES");
			} else if (target.hasPermission(UltraHomes.getFileManager().getConfigFile().getConfig().getString("CONIG.ULTRAHOMES.5.PERMISSIONS"))) {
				this.homeLimit = UltraHomes.getFileManager().getConfigFile().getConfig().getInt("CONIG.ULTRAHOMES.5.HOMES");
			} else if (target.hasPermission(UltraHomes.getFileManager().getConfigFile().getConfig().getString("CONIG.ULTRAHOMES.6.PERMISSIONS"))) {
				this.homeLimit = UltraHomes.getFileManager().getConfigFile().getConfig().getInt("CONIG.ULTRAHOMES.6.HOMES");
			} else {
				this.homeLimit = 1;
			}
		}
	}

	private void loadHomes() {
		FileConfiguration cfg = getConfig();
		if (cfg.get("Homes") == null) {
			return;
		}
		for (String homeName : cfg.getConfigurationSection("Homes").getKeys(false)) {
			String[] splitted = cfg.getString("Homes." + homeName).split(";");
			World world = Bukkit.getWorld(splitted[0]);
			if (world != null) {
				Location loc = new Location(world, Double.parseDouble(splitted[1]), Double.parseDouble(splitted[2]),
						Double.parseDouble(splitted[3]), Float.parseFloat(splitted[4]), Float.parseFloat(splitted[5]));
				this.homes.put(homeName, loc);
			}
		}
	}
	
	public void createHome(String homeName, Location loc) {
		for (String homeN : this.homes.keySet()) {
			if (homeN.equalsIgnoreCase(homeName)) {
				return;
			}
		}
		this.homes.put(homeName, loc);
		FileConfiguration cfg = getConfig();
		String homeString = loc.getWorld().getName() + ";" + loc.getX() + ";" + loc.getY() + ";" + loc.getZ() + ";"
				+ loc.getYaw() + ";" + loc.getPitch();

		cfg.set("Homes." + homeName, homeString);
		saveConfig();
	}

	public void deleteHome(String homeName) {
		String foundName = "";
		for (String homeN : this.homes.keySet()) {
			if (homeN.equalsIgnoreCase(homeName)) {
				foundName = homeN;
				break;
			}
		}
		if (foundName.isEmpty()) {
			return;
		}
		this.homes.remove(foundName);

		FileConfiguration cfg = getConfig();
		cfg.set("Homes." + foundName, null);
		saveConfig();
	}

	public Location getHome(String homeName) {
		if (!this.homes.containsKey(homeName)) {
			return null;
		}
		return (Location) this.homes.get(homeName);
	}

	public Location getHomeExpanded(String homeName) {
		for (Map.Entry<String, Location> entryHomes : this.homes.entrySet()) {
			if (((String) entryHomes.getKey()).equalsIgnoreCase(homeName)) {
				return (Location) entryHomes.getValue();
			}
		}
		return null;
	}

	public Map<String, Location> getHomes() {
		return this.homes;
	}

	public int getHomeLimit() {
		return this.homeLimit;
	}

}
