package de.sinixspielt.ultrahomes.manager;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.sinixspielt.ultrahomes.manager.other.PlayerData;

/*
Class created on 03.03.2019 by SinixSpielt
 * */

public class PlayerDataManager {
	
	private Map<UUID, PlayerData> playerDatas;

	public PlayerDataManager() {
		this.playerDatas = new HashMap<UUID, PlayerData>();
		loadPlayerdataForOnlines();
	}

	private void loadPlayerdataForOnlines() {
		for(Player all : Bukkit.getOnlinePlayers()){
			addPlayerToCache(all.getUniqueId());
		}
	}

	public void addPlayerToCache(UUID uuid) {
		if (this.playerDatas.containsKey(uuid)) {
			return;
		}
		PlayerData data = new PlayerData(uuid);
		this.playerDatas.put(uuid, data);
	}

	public void removePlayerFromCache(UUID uuid) {
		if (!this.playerDatas.containsKey(uuid)) {
			return;
		}
		this.playerDatas.remove(uuid);
	}

	public PlayerData getPlayerData(UUID uuid) {
		if (!this.playerDatas.containsKey(uuid)) {
			addPlayerToCache(uuid);
		}
		return (PlayerData) this.playerDatas.get(uuid);
	}
}
