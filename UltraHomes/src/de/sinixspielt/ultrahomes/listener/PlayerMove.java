package de.sinixspielt.ultrahomes.listener;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import de.sinixspielt.teleportapi.TeleportAPI;
import de.sinixspielt.ultrahomes.UltraHomes;

public class PlayerMove implements Listener{
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		if(!(UltraHomes.getFileManager().getConfigFile().getConfig().getBoolean("CONIG.ULTRAHOMES.CANCELONMOVE") == true)) {
			return;
		}
		Location from = e.getFrom();
		Location to = e.getTo();
		if(TeleportAPI.getTeleportManager().getTeleportDelays().containsKey(e.getPlayer())) {
			if(hasLocationChanged(from, to)) {
				TeleportAPI.getTeleportManager().getTeleportDelays().remove(e.getPlayer());
			}	
		}
	}
	
	private boolean hasLocationChanged(Location from, Location to) {
		if ((from.getBlockX() != to.getBlockX()) || (from.getBlockZ() != to.getBlockZ())) {
			return true;
		}
		return false;
	}
}