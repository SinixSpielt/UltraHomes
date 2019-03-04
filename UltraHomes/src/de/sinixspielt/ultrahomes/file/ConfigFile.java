package de.sinixspielt.ultrahomes.file;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

/*
Class created on 03.03.2019 by SinixSpielt
 * */

public class ConfigFile extends FileBase {
	
	public ConfigFile() {
		super("", "config");
		writeDefaults();
	}

	private void writeDefaults() {
		FileConfiguration cfg = getConfig();
		cfg.addDefault("CONIG.ULTRAHOMES.PREFIX", "&8[&cUltraHomes&8]");
		cfg.addDefault("CONIG.ULTRAHOMES.CANCELONMOVE", true);
		cfg.addDefault("CONIG.ULTRAHOMES.NOTELEPORTDELAY", "ultrahomes.permissions.nodelay");
		cfg.addDefault("CONIG.ULTRAHOMES.1.PERMISSIONS", "ultrahomes.permissions.homes.1");
		cfg.addDefault("CONIG.ULTRAHOMES.1.HOMES", 1);
		cfg.addDefault("CONIG.ULTRAHOMES.2.PERMISSIONS", "ultrahomes.permissions.homes.3");
		cfg.addDefault("CONIG.ULTRAHOMES.2.HOMES", 3);
		cfg.addDefault("CONIG.ULTRAHOMES.3.PERMISSIONS", "ultrahomes.permissions.homes.5");
		cfg.addDefault("CONIG.ULTRAHOMES.3.HOMES", 5);
		cfg.addDefault("CONIG.ULTRAHOMES.4.PERMISSIONS", "ultrahomes.permissions.homes.7");
		cfg.addDefault("CONIG.ULTRAHOMES.4.HOMES", 7);
		cfg.addDefault("CONIG.ULTRAHOMES.5.PERMISSIONS", "ultrahomes.permissions.homes.10");
		cfg.addDefault("CONIG.ULTRAHOMES.5.HOMES", 10);
		cfg.addDefault("CONIG.ULTRAHOMES.6.PERMISSIONS", "ultrahomes.permissions.homes.20");
		cfg.addDefault("CONIG.ULTRAHOMES.6.HOMES", 20);
		cfg.addDefault("CONIG.ULTRAHOMES.UNLIMETED.PERMISSIONS", "ultrahomes.permissions.homes.unlimeted");
		cfg.addDefault("CONIG.ULTRAHOMES.GUI.USEGUI", true);
		cfg.addDefault("CONIG.ULTRAHOMES.GUI.TITLE", "&4Deine Homes!");
		List<String> banscreen = new ArrayList<String>();
		banscreen.add("&7Linksklick &8| &aTeleport");
		banscreen.add("&7Rechtsklick &8| &cLöschen");
		cfg.addDefault("CONIG.ULTRAHOMES.GUI.HOME.LORE", banscreen);
		cfg.addDefault("CONIG.ULTRAHOMES.GUI.PAGE.NEXT", "&7Nächste Seite");
		cfg.addDefault("CONIG.ULTRAHOMES.GUI.PAGE.LAST", "&7Vorherige Seite");
		cfg.addDefault("CONIG.ULTRAHOMES.GUI.PAGE.NOAVAIBLE", "&cKeine Seite Verfügbar");
		cfg.options().copyDefaults(true);
		saveConfig();
	}
}