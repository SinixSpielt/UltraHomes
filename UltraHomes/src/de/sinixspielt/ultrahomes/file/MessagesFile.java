package de.sinixspielt.ultrahomes.file;

import org.bukkit.configuration.file.FileConfiguration;

import de.sinixspielt.ultrahomes.UltraHomes;

/*
Class created on 03.03.2019 by SinixSpielt
 * */

public class MessagesFile extends FileBase {

	public MessagesFile() {
		super("", "message");
		writeDefaults();
	}

	private void writeDefaults() {
		FileConfiguration cfg = getConfig();
		cfg.addDefault("MESSAGES.ULTRAHOMES.NOPERMISSION", "%PREFIX% &4Keine Berechtigung!");
		cfg.addDefault("MESSAGES.ULTRAHOMES.NOHOME", "%PREFIX% &cDu besitzt kein Home mit dem Namen &e%HOME%&c.");
		cfg.addDefault("MESSAGES.ULTRAHOMES.NOHOMES", "%PREFIX% &cDu besitzt noch keine Homes");
		cfg.addDefault("MESSAGES.ULTRAHOMES.MAXHOME", "%PREFIX% &7Du hast die Maximale Anzahl an Homes erreicht!");
		cfg.addDefault("MESSAGES.ULTRAHOMES.DELETEHOME", "%PREFIX% &7Home &e%HOME% &7wurde gelöscht.");
		cfg.addDefault("MESSAGES.ULTRAHOMES.CREATEHOME", "%PREFIX% &7Home &e%HOME% &7wurde erstellt.");
		cfg.addDefault("MESSAGES.ULTRAHOMES.AVAIBLEHOME", "%PREFIX% &cDu beseitzt bereits ein Home mit dem Namen &e%HOME%&c.");
		cfg.addDefault("MESSAGES.ULTRAHOMES.COMMAND.HOME.USE", "%PREFIX% &cVerwendung: &e/home <Name>");
		cfg.addDefault("MESSAGES.ULTRAHOMES.COMMAND.SETHOME.USE", "%PREFIX% &cVerwendung: &e/sethome <Name>");
		cfg.addDefault("MESSAGES.ULTRAHOMES.COMMAND.DELETEHOME.USE", "%PREFIX% &cVerwendung: &e/delhome <Name>");
		cfg.addDefault("MESSAGES.ULTRAHOMES.COMMAND.HOMES.MESSAGE1", "%PREFIX% &7Deine Homes&8: &7%HOMES%");
		cfg.addDefault("MESSAGES.ULTRAHOMES.COMMAND.HOMES.COLOR", "&b");
		cfg.addDefault("MESSAGES.ULTRAHOMES.COMMAND.HOMES.COLOR2", "&7, ");
		cfg.addDefault("MESSAGES.ULTRAHOMES.TELEPORT.TELEPORTCANCEL", "%PREFIX% &cTeleportvorgang wurde abgebrochen.");
		cfg.addDefault("MESSAGES.ULTRAHOMES.TELEPORT.ISTELEPORTING", "%PREFIX% &cEs läuft bereits ein Teleportvorgang.");
		cfg.addDefault("MESSAGES.ULTRAHOMES.TELEPORT.TELEPORTFINISH", "%PREFIX% &7Du wurdest zum Home &e%HOME% &7Teleportiert.");
		cfg.addDefault("MESSAGES.ULTRAHOMES.TELEPORT.TELEPORTNOW", "%PREFIX% &7Du wirst in &e3 &7Sekunden Teleportiert! &cBewege dich bitte nicht!");
		
		
		
		
		cfg.options().copyDefaults(true);
		saveConfig();
	}
	
	public String getMessage(String path) {
		String msg = getConfig().getString(path);
		msg = msg.replace("&", "§");
		if(msg.contains("%PREFIX%")) {
			msg = msg.replace("%PREFIX%", UltraHomes.getFileManager().getConfigFile().getConfig().getString("CONIG.ULTRAHOMES.PREFIX").replace("&", "§"));
		}
		return msg;
	}
}