package de.sinixspielt.ultrahomes.file;

/*
Class created on 03.03.2019 by SinixSpielt
 * */

public class FileManager {

	private ConfigFile configFile;
	private MessagesFile messagesFile;
	
	public FileManager() {
		this.configFile = new ConfigFile();
		this.messagesFile = new MessagesFile();
	}
	
	public MessagesFile getMessagesFile() {
		return messagesFile;
	}
	
	public ConfigFile getConfigFile() {
		return configFile;
	}
}