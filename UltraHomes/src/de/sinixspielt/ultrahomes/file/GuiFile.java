package de.sinixspielt.ultrahomes.file;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

public class GuiFile extends FileBase {
	
	public GuiFile() {
		super("", "gui");
		writeDefaults();
	}

	private void writeDefaults() {
		FileConfiguration cfg = getConfig();
		cfg.addDefault("GUI.ULTRAHOMES.USEGUI", true);
		cfg.addDefault("GUI.ULTRAHOMES.TITLE", "&cDeine Homes!");
		cfg.addDefault("GUI.ULTRAHOMES.HOME.NOHOME", "&cDu besitzt noch keine Homes!");
		cfg.addDefault("GUI.ULTRAHOMES.HOME.ITEM.ID", 355);
		cfg.addDefault("GUI.ULTRAHOMES.HOME.ITEM.subID", 0);
		cfg.addDefault("GUI.ULTRAHOMES.PAGE.NEXT", "&7Nächste Seite");
		cfg.addDefault("GUI.ULTRAHOMES.PAGE.LAST", "&7Vorherige Seite");
		cfg.addDefault("GUI.ULTRAHOMES.PAGE.ITEM.ID", 388);
		cfg.addDefault("GUI.ULTRAHOMES.PAGE.ITEM.subID", 0);
		cfg.addDefault("GUI.ULTRAHOMES.PAGE.ITEM.NAME", "&7Seite &e%PAGE%");
		cfg.addDefault("GUI.ULTRAHOMES.PAGE.NOAVAIBLE", "&cKeine Seite Verfügbar");
		List<String> list = new ArrayList<String>();
		list.add("&7Linksklick &8| &aTeleport");
		list.add("&7Rechtsklick &8| &cLöschen");
		cfg.addDefault("GUI.ULTRAHOMES.HOME.LORE", list);
		cfg.addDefault("GUI.ULTRAHOMES.DELETE.TITLE", "&8%HOME%");
		cfg.addDefault("GUI.ULTRAHOMES.DELETE.YES.TITLE", "&aJa, wirklich löschen!");
		cfg.addDefault("GUI.ULTRAHOMES.DELETE.YES.ID", 159);
		cfg.addDefault("GUI.ULTRAHOMES.DELETE.YES.subID", 5);
		cfg.addDefault("GUI.ULTRAHOMES.DELETE.NO.TITLE", "&cNein, nicht löschen!");
		cfg.addDefault("GUI.ULTRAHOMES.DELETE.NO.ID", 159);
		cfg.addDefault("GUI.ULTRAHOMES.DELETE.NO.subID", 14);
		
		cfg.options().copyDefaults(true);
		saveConfig();
	}
}