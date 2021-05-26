package me.Blume.InvisibilityCloak.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Blume.InvisibilityCloak.Main;
import me.Blume.InvisibilityCloak.Cloak.inviscloak;
import net.md_5.bungee.api.ChatColor;

public class removeCloak implements CommandExecutor{
	@SuppressWarnings("unused")
	private Main plugin;
	public removeCloak(Main plugin) {
		this.plugin=plugin;
	}
	inviscloak invcloak = new inviscloak();
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(args.length==0) {
				if(plugin.getcloakplayer().contains(player.getUniqueId())) {
					if(label.equals("removecloak")) {
						player.sendMessage(ChatColor.AQUA+"Cloak removed from player");
						invcloak.removeCloak(player);
						plugin.removeplayer(player.getUniqueId());
					}
				}
			}
		}
		return false;
	}

}
