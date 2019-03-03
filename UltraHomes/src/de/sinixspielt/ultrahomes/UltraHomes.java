package de.sinixspielt.ultrahomes;

import org.bukkit.plugin.java.JavaPlugin;

import de.sinixspielt.ultrahomes.commands.CommandHome;
import de.sinixspielt.ultrahomes.file.FileManager;
import de.sinixspielt.ultrahomes.manager.PlayerDataManager;

/*
Class created on 03.03.2019 by SinixSpielt
 * */

public class UltraHomes extends JavaPlugin {
	
	public static UltraHomes instance;
	public static FileManager fileManager;
	public static PlayerDataManager playerdataManager;
	
	@Override
	public void onEnable() {
		instance = this;
		fileManager = new FileManager();
		playerdataManager = new PlayerDataManager();
		getCommand("home").setExecutor(new CommandHome());
	}
	
	@Override
	public void onDisable() {
		instance = null;
	}
	
	public static UltraHomes getInstance() {
		return instance;
	}
	
	public static FileManager getFileManager() {
		return fileManager;
	}
	
	public static PlayerDataManager getPlayerdataManager() {
		return playerdataManager;
	}
}