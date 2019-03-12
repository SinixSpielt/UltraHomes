package de.sinixspielt.ultrahomes.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.sinixspielt.teleportapi.TeleportAPI;
import de.sinixspielt.teleportapi.task.TPDelay;
import de.sinixspielt.ultrahomes.UltraHomes;
import de.sinixspielt.ultrahomes.gui.HomesInventory;
import de.sinixspielt.ultrahomes.manager.other.PlayerData;

/*
Class created on 03.03.2019 by SinixSpielt
 * */

public class CommandHome implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, final String[] args) {
		if (!(sender instanceof Player)) {
			return true;
		}
		Player p = (Player) sender;
		if (label.equalsIgnoreCase("home")) {
			if (args.length != 1) {
				p.sendMessage(UltraHomes.getFileManager().getMessagesFile().getMessage("MESSAGES.ULTRAHOMES.COMMAND.HOME.USE"));
				return true;
			}
			PlayerData data = UltraHomes.getPlayerdataManager().getPlayerData(p.getUniqueId());
			final Location home = data.getHomeExpanded(args[0]);
			if (home == null) {
				p.sendMessage(UltraHomes.getFileManager().getMessagesFile().getMessage("MESSAGES.ULTRAHOMES.NOHOME").replace("%HOME%", args[0]));
				return true;
			}
			if (TeleportAPI.getTeleportManager().getTeleportDelays().containsKey(p)) {
				p.sendMessage(UltraHomes.getFileManager().getMessagesFile().getMessage("MESSAGES.ULTRAHOMES.TELEPORT.ISTELEPORTING"));
				return true;
			}
			if (p.hasPermission(UltraHomes.getFileManager().getConfigFile().getConfig().getString("CONIG.ULTRAHOMES.NOTELEPORTDELAY"))) {
				p.teleport(home);
				p.sendMessage(UltraHomes.getFileManager().getMessagesFile().getMessage("MESSAGES.ULTRAHOMES.TELEPORT.TELEPORTFINISH").replace("%HOME%", args[0]));
			} else {
				p.sendMessage(UltraHomes.getFileManager().getMessagesFile().getMessage("MESSAGES.ULTRAHOMES.TELEPORT.TELEPORTNOW"));

				TPDelay delay = new TPDelay(p, 0, 5, 12) {
					public void onTick() {
						if(!TeleportAPI.getTeleportManager().getTeleportDelays().containsKey(p)) {
							cancel();
							getPlayer().sendMessage(UltraHomes.getFileManager().getMessagesFile().getMessage("MESSAGES.ULTRAHOMES.TELEPORT.TELEPORTCANCEL"));
							return;
						}
						if ((home == null) || (UltraHomes.getPlayerdataManager().getPlayerData(getPlayer().getUniqueId()).getHomeExpanded(args[0]) == null)) {
							cancel();
							getPlayer().sendMessage(UltraHomes.getFileManager().getMessagesFile().getMessage("MESSAGES.ULTRAHOMES.TELEPORT.TELEPORTCANCEL"));
							return;
						}
					}

					public void onEnd() {
						getPlayer().teleport(home);
						getPlayer().sendMessage(UltraHomes.getFileManager().getMessagesFile().getMessage("MESSAGES.ULTRAHOMES.TELEPORT.TELEPORTFINISH").replace("%HOME%", args[0]));
					}
				};
				TeleportAPI.getTeleportManager().getTeleportDelays().put(p, delay);
			}
		}
		if (label.equalsIgnoreCase("homes")) {
			if(!(UltraHomes.getFileManager().getGuiFile().getConfig().getBoolean("GUI.ULTRAHOMES.USEGUI") == true)) {
				PlayerData data = UltraHomes.getPlayerdataManager().getPlayerData(p.getUniqueId());
				Map<String, Location> homes = data.getHomes();
				if (homes.isEmpty()) {
					p.sendMessage(UltraHomes.getFileManager().getMessagesFile().getMessage("MESSAGES.ULTRAHOMES.NOHOMES"));
					return true;
				}
				List<String> homeList = new ArrayList<String>();
				homeList.addAll(homes.keySet());
				StringBuilder builder = new StringBuilder();
				for(int i = 0; i < homeList.size(); i++){
					String home = (String) homeList.get(i);
					builder.append(UltraHomes.getFileManager().getMessagesFile().getMessage("MESSAGES.ULTRAHOMES.COMMAND.HOMES.COLOR")).append(home).append(UltraHomes.getFileManager().getMessagesFile().getMessage("MESSAGES.ULTRAHOMES.COMMAND.HOMES.COLOR2"));
				}
				
				String output = builder.substring(0, builder.length() - 2);
				p.sendMessage(UltraHomes.getFileManager().getMessagesFile().getMessage("MESSAGES.ULTRAHOMES.COMMAND.HOMES.MESSAGE").replace("%HOMES%", output));	
			}
			HomesInventory.openInventory(p, 0);
			HomesInventory.pages.put(p, 0);
		}
		if (label.equalsIgnoreCase("sethome")) {
			if (args.length != 1) {
				p.sendMessage(UltraHomes.getFileManager().getMessagesFile().getMessage("MESSAGES.ULTRAHOMES.COMMAND.SETHOME.USE"));
				return true;
			}
			PlayerData data = UltraHomes.getPlayerdataManager().getPlayerData(p.getUniqueId());
			Location home = data.getHomeExpanded(args[0]);
			if (home != null) {
				p.sendMessage(UltraHomes.getFileManager().getMessagesFile().getMessage("MESSAGES.ULTRAHOMES.AVAIBLEHOME").replace("%HOME%", args[0]));
				return true;
			}
			if ((data.getHomeLimit() != -1) && (data.getHomes().size() >= data.getHomeLimit())) {
				p.sendMessage(UltraHomes.getFileManager().getMessagesFile().getMessage("MESSAGES.ULTRAHOMES.MAXHOME"));
				return true;
			}
			data.createHome(args[0], p.getLocation());
			p.sendMessage(UltraHomes.getFileManager().getMessagesFile().getMessage("MESSAGES.ULTRAHOMES.CREATEHOME").replace("%HOME%", args[0]));
		}
		if (label.equalsIgnoreCase("delhome")) {
			if (args.length != 1) {
				p.sendMessage(UltraHomes.getFileManager().getMessagesFile().getMessage("MESSAGES.ULTRAHOMES.COMMAND.DELETEHOME.USE"));
				return true;
			}
			PlayerData data = UltraHomes.getPlayerdataManager().getPlayerData(p.getUniqueId());
			Location home = data.getHomeExpanded(args[0]);
			if (home == null) {
				p.sendMessage(UltraHomes.getFileManager().getMessagesFile().getMessage("MESSAGES.ULTRAHOMES.NOHOME").replace("%HOME%", args[0]));
				return true;
			}
			data.deleteHome(args[0]);
			p.sendMessage(UltraHomes.getFileManager().getMessagesFile().getMessage("MESSAGES.ULTRAHOMES.DELETEHOME").replace("%HOME%", args[0]));
		}
		return true;
	}
}