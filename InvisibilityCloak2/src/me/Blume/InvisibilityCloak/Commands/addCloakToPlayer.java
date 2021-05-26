package me.Blume.InvisibilityCloak.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Blume.InvisibilityCloak.Main;
import me.Blume.InvisibilityCloak.Cloak.inviscloak;
import net.md_5.bungee.api.ChatColor;

public class addCloakToPlayer implements CommandExecutor{
	@SuppressWarnings("unused")
	private Main plugin;
	public addCloakToPlayer(Main plugin) {
		this.plugin=plugin;
	}
	inviscloak invcloak = new inviscloak();
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(args.length==0) {
				if(label.equals("addcloak")) {
					if(!(plugin.getcloakplayer().contains(player.getUniqueId()))) {
						plugin.addplayer(player.getUniqueId());
						player.sendMessage(ChatColor.AQUA+"Cloak added to player");
						player.getInventory().addItem(invcloak.getCloak());
					}
				}
			}
		}

		return false;
	}

}
