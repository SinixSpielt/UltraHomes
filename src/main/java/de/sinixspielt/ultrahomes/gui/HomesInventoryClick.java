package de.sinixspielt.ultrahomes.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import de.sinixspielt.teleportapi.TeleportAPI;
import de.sinixspielt.teleportapi.task.TPDelay;
import de.sinixspielt.ultrahomes.UltraHomes;
import de.sinixspielt.ultrahomes.manager.other.PlayerData;

public class HomesInventoryClick implements Listener{
	
	@EventHandler
	public void onCklick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		Inventory inv = e.getInventory();
		ItemStack item = e.getCurrentItem();
		if (e.getHotbarButton() != -1) {
			item = e.getView().getBottomInventory().getItem(e.getHotbarButton());
			if (item == null) {
				item = e.getCurrentItem();
			}
		}
		if ((item == null) || (item.getType() == Material.AIR)) {
			return;
		}
		if(inv.getTitle().equals(UltraHomes.getFileManager().getGuiFile().getConfig().getString("GUI.ULTRAHOMES.TITLE").replace("&", "�"))) {
			e.setCancelled(true);
			if(e.getCurrentItem().getType() == Material.BARRIER) {
				return;
			}
			if(e.getRawSlot() == 45) {
				if(HomesInventory.pages.get(p) == 0) {
					p.sendMessage(getMessage("GUI.ULTRAHOMES.PAGE.NOAVAIBLE"));
				}else {
					int lastpage = HomesInventory.pages.get(p)+-1;
					HomesInventory.pages.put(p, lastpage);
					HomesInventory.openInventory(p, lastpage);
				}
			}
			if(e.getRawSlot() == 53) {
				PlayerData data = UltraHomes.getPlayerdataManager().getPlayerData(p.getUniqueId());
				Map<String, Location> homes2 = data.getHomes();
				List<String> homeList = new ArrayList<String>();
				homeList.addAll(homes2.keySet());
				if(homes2.size() > (HomesInventory.pages.get(p)+1)*27) {
					int nextpage = HomesInventory.pages.get(p)+1;
					HomesInventory.pages.put(p, nextpage);
					HomesInventory.openInventory(p, nextpage);
				}else {
					p.sendMessage(getMessage("GUI.ULTRAHOMES.PAGE.NOAVAIBLE"));
				}
			}
			if(e.getRawSlot() > 8 && e.getRawSlot() < 36) {
				if(e.isLeftClick()) {
					String homeName = e.getCurrentItem().getItemMeta().getDisplayName();
					p.closeInventory();
					p.sendMessage(UltraHomes.getFileManager().getMessagesFile().getMessage("MESSAGES.ULTRAHOMES.TELEPORT.TELEPORTNOW"));
					PlayerData data = UltraHomes.getPlayerdataManager().getPlayerData(p.getUniqueId());
					final Location home = data.getHomeExpanded(homeName);
					if (p.hasPermission(UltraHomes.getFileManager().getConfigFile().getConfig().getString("CONIG.ULTRAHOMES.NOTELEPORTDELAY"))) {
						p.teleport(home);
						p.sendMessage(UltraHomes.getFileManager().getMessagesFile().getMessage("MESSAGES.ULTRAHOMES.TELEPORT.TELEPORTFINISH").replace("%HOME%",homeName));
					}else {
						TPDelay delay = new TPDelay(p, 0, 5, 12) {
							public void onTick() {
								if(!TeleportAPI.getTeleportManager().getTeleportDelays().containsKey(p)) {
									cancel();
									getPlayer().sendMessage(UltraHomes.getFileManager().getMessagesFile().getMessage("MESSAGES.ULTRAHOMES.TELEPORT.TELEPORTCANCEL"));
									return;
								}
								if ((home == null) || (UltraHomes.getPlayerdataManager().getPlayerData(getPlayer().getUniqueId()).getHomeExpanded(homeName) == null)) {
									cancel();
									getPlayer().sendMessage(UltraHomes.getFileManager().getMessagesFile().getMessage("MESSAGES.ULTRAHOMES.TELEPORT.TELEPORTCANCEL"));
									return;
								}
							}

							public void onEnd() {
								getPlayer().teleport(home);
								getPlayer().sendMessage(UltraHomes.getFileManager().getMessagesFile().getMessage("MESSAGES.ULTRAHOMES.TELEPORT.TELEPORTFINISH").replace("%HOME%", homeName));
							}
						};
						TeleportAPI.getTeleportManager().getTeleportDelays().put(p, delay);	
					}
				}
				if(e.isRightClick()) {
					HomesInventory.openDelete(p, e.getCurrentItem().getItemMeta().getDisplayName());
				}
			}
		}
		if(inv.getTitle().startsWith("§8")) {
			PlayerData data = UltraHomes.getPlayerdataManager().getPlayerData(p.getUniqueId());
			Map<String, Location> homes = data.getHomes();
			List<String> homeList = new ArrayList<String>();
			homeList.addAll(homes.keySet());
			for(String name : homeList) {
				if(inv.getTitle().contains(name)) {
					e.setCancelled(true);
					if(e.getRawSlot() == 2) {
						data.deleteHome(name);
						p.sendMessage(UltraHomes.getFileManager().getMessagesFile().getMessage("MESSAGES.ULTRAHOMES.DELETEHOME").replace("%HOME%", name));
						p.closeInventory();
						return;
					}
					if(e.getRawSlot() == 6) {
						p.closeInventory();
						return;
					}
				}
			}
		}
	}
	
	public static String getMessage(String path) {
		String message = UltraHomes.getFileManager().getGuiFile().getConfig().getString(path);
		message = message.replace("&", "§");
		return message;
	}
}