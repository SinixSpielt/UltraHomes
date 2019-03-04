package de.sinixspielt.ultrahomes.gui;

import java.util.ArrayList;
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
import de.sinixspielt.ultrahomes.utils.ItemSkulls;

public class HomesInventory {
	
	public static String WOORD_ARROW_LEFT_URL = "http://textures.minecraft.net/texture/737648ae7a564a5287792b05fac79c6b6bd47f616a559ce8b543e6947235bce";
	public static String WOOD_ARROW_RIGHT_URL = "http://textures.minecraft.net/texture/1a4f68c8fb279e50ab786f9fa54c88ca4ecfe1eb5fd5f0c38c54c9b1c7203d7a";
	public static String WOORD_X_URL = "http://textures.minecraft.net/texture/5a6787ba32564e7c2f3a0ce64498ecbb23b89845e5a66b5cec7736f729ed37";
	public static String WOOD_HEART_URL = "http://textures.minecraft.net/texture/336febeca7c488a6671dc071655dde2a1b65c3ccb20b6e8eaf9bfb08e64b80";
	public static ItemStack placeglasses = new ItemCreator().material(Material.STAINED_GLASS_PANE).displayName(" ").data((short) 7).build();
	public static ItemStack deletes = new ItemCreator().material(Material.STAINED_CLAY).displayName("§aJa, wirklich löschen!").data((short) 5).build();
	public static ItemStack nodelete = new ItemCreator().material(Material.STAINED_CLAY).displayName("§4Nein, nicht löschen!").data((short) 14).build();
	
	public static void openInventory(Player p) {
		Inventory inv = Bukkit.createInventory(null, 6*9, UltraHomes.getFileManager().getConfigFile().getConfig().getString("CONIG.ULTRAHOMES.GUI.TITLE").replace("&", "§"));
		
		for(int i = 0; i < 9; i++) {
			inv.setItem(i, placeglasses);
		}
		PlayerData data = UltraHomes.getPlayerdataManager().getPlayerData(p.getUniqueId());
		Map<String, Location> homes = data.getHomes();
		List<String> homeList = new ArrayList<String>();
		homeList.addAll(homes.keySet());
		if(homeList.size() == 0) {
			inv.setItem(22, new ItemCreator().material(Material.BARRIER).displayName(UltraHomes.getFileManager().getMessagesFile().getMessage("MESSAGES.ULTRAHOMES.NOHOMES")).build());
		}else {
			ArrayList<String> lore = new ArrayList<String>();
			for(String lore2 : UltraHomes.getFileManager().getConfigFile().getConfig().getStringList("CONIG.ULTRAHOMES.GUI.HOME.LORE")) {
				lore2 = lore2.replace("&", "§");
				lore.add(lore2);
			}
			
			for(String homes2 : homes.keySet()) {
				inv.addItem(new ItemCreator().displayName(homes2).lore(lore).material(Material.BED).build());
			}
		}
		
		for(int i = 4*9; i < 5*9; i++) {
			inv.setItem(i, placeglasses);
		}
		inv.setItem(45, ItemSkulls.getSkull1(WOORD_X_URL, 1, "§cKeine Seite Verfügbar"));
		inv.setItem(49, new ItemCreator().material(Material.PAPER).displayName("§7Seite §7-§e1§7-").build());
		inv.setItem(53, ItemSkulls.getSkull1(WOORD_X_URL, 1, "§cKeine Seite Verfügbar"));
		p.openInventory(inv);
	}
	
	public static void openDelete(Player p,String home) {
		Inventory delete = Bukkit.createInventory(null, 1*9, "§8" + home);
		for(int i = 0; i < delete.getSize(); i++) {
			delete.setItem(i, placeglasses);
		}
		
		delete.setItem(2, deletes);
		delete.setItem(6, nodelete);
		
		p.openInventory(delete);
	}
}