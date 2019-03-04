package de.sinixspielt.ultrahomes.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import de.sinixspielt.ultrahomes.UltraHomes;
import de.sinixspielt.ultrahomes.manager.other.PlayerData;
import de.sinixspielt.ultrahomes.utils.ItemCreator;
import de.sinixspielt.ultrahomes.utils.ItemCreator2;
import de.sinixspielt.ultrahomes.utils.ItemSkulls;

public class HomesInventory {
	
	public static String WOORD_ARROW_LEFT_URL = "http://textures.minecraft.net/texture/737648ae7a564a5287792b05fac79c6b6bd47f616a559ce8b543e6947235bce";
	public static String WOOD_ARROW_RIGHT_URL = "http://textures.minecraft.net/texture/1a4f68c8fb279e50ab786f9fa54c88ca4ecfe1eb5fd5f0c38c54c9b1c7203d7a";
	public static String WOORD_X_URL = "http://textures.minecraft.net/texture/5a6787ba32564e7c2f3a0ce64498ecbb23b89845e5a66b5cec7736f729ed37";
	public static String WOOD_HEART_URL = "http://textures.minecraft.net/texture/336febeca7c488a6671dc071655dde2a1b65c3ccb20b6e8eaf9bfb08e64b80";
	public static ItemStack placeglasses = new ItemCreator().material(Material.STAINED_GLASS_PANE).displayName(" ").data((short) 7).build();
	
	public static HashMap<Player, Integer> pages = new HashMap<>();
	
	public static void openDelete(Player p,String home) {
		Inventory delete = Bukkit.createInventory(null, 1*9, getMessage("GUI.ULTRAHOMES.DELETE.TITLE").replace("%HOME%", home));
		for(int i = 0; i < delete.getSize(); i++) {
			delete.setItem(i, placeglasses);
		}
		
		delete.setItem(2, new ItemCreator2().data((short) getInt("GUI.ULTRAHOMES.DELETE.YES.subID")).material(getInt("GUI.ULTRAHOMES.DELETE.YES.ID")).displayName(getMessage("GUI.ULTRAHOMES.DELETE.YES.TITLE")).build());
		delete.setItem(6, new ItemCreator2().data((short) getInt("GUI.ULTRAHOMES.DELETE.NO.subID")).material(getInt("GUI.ULTRAHOMES.DELETE.NO.ID")).displayName(getMessage("GUI.ULTRAHOMES.DELETE.NO.TITLE")).build());
		
		
		p.openInventory(delete);
	}
	
	public static void openInventory(Player p, int page) {
		Inventory homes = Bukkit.createInventory(null, 6*9, UltraHomes.getFileManager().getGuiFile().getConfig().getString("GUI.ULTRAHOMES.TITLE").replace("&", "§"));
		for(int i = 4*9; i < 5*9; i++) {
			homes.setItem(i, placeglasses);
		}
		for(int i = 0; i < 9; i++) {
			homes.setItem(i, placeglasses);
		}
		
		ArrayList<String> lore = new ArrayList<String>();
		for(String lore2 : UltraHomes.getFileManager().getGuiFile().getConfig().getStringList("GUI.ULTRAHOMES.HOME.LORE")) {
			lore2 = lore2.replace("&", "§");
			lore.add(lore2);
		}
		PlayerData data = UltraHomes.getPlayerdataManager().getPlayerData(p.getUniqueId());
		Map<String, Location> homes2 = data.getHomes();
		List<String> homeList = new ArrayList<String>();
		homeList.addAll(homes2.keySet());
		
		int pages = page;
		int skip = pages * 27;
		int left = 27;
		
		for(String home3 : homeList) {
			if(skip-- > 0) continue;
			if(left-- > 0) {
				homes.addItem(new ItemCreator2().displayName(home3).lore(lore).material(getInt("GUI.ULTRAHOMES.HOME.ITEM.ID")).data((short) getInt("GUI.ULTRAHOMES.HOME.ITEM.subID")).build());
			}
		}
		homes.setItem(45, ItemSkulls.getSkull1(WOORD_ARROW_LEFT_URL, 1, getMessage("GUI.ULTRAHOMES.PAGE.LAST")));
		homes.setItem(53, ItemSkulls.getSkull1(WOOD_ARROW_RIGHT_URL, 1, getMessage("GUI.ULTRAHOMES.PAGE.NEXT")));	
		homes.setItem(49, new ItemCreator2().material(getInt("GUI.ULTRAHOMES.PAGE.ITEM.ID")).data((short) getInt("GUI.ULTRAHOMES.PAGE.ITEM.subID")).displayName(getMessage("GUI.ULTRAHOMES.PAGE.ITEM.NAME").replace("%PAGE%", "" + (page+1))).amount(page+1).build());	
		p.openInventory(homes);
	}
	
	public static int getInt(String path) {
		int i = UltraHomes.getFileManager().getGuiFile().getConfig().getInt(path);
		return i;
	}
	
	public static String getMessage(String path) {
		String message = UltraHomes.getFileManager().getGuiFile().getConfig().getString(path);
		message = message.replace("&", "§");
		return message;
	}
}