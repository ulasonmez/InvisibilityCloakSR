package me.Blume.InvisibilityCloak.Listeners;

import java.util.List;
import java.util.ListIterator;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;
import me.Blume.InvisibilityCloak.Main;
import me.Blume.InvisibilityCloak.Cloak.inviscloak;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
public class events implements Listener {
	private Main plugin;
	public events(Main plugin) {
		this.plugin=plugin;
	}
	int id1,id2,a,c,d;
	public static BukkitTask task,task2;
	inviscloak invcloak = new inviscloak();
	@EventHandler
	public void elytraDrops(PlayerDropItemEvent event) {
		if(plugin.getcloakplayer().contains(event.getPlayer().getUniqueId())) {
			if(event.getItemDrop().getItemStack().isSimilar(invcloak.getCloak())) {
				event.setCancelled(true);
				return;
			}
		}
	}
	@EventHandler
	public void clayDrops(PlayerDropItemEvent event) {
		if(plugin.getcloakplayer().contains(event.getPlayer().getUniqueId())) {
			if(event.getItemDrop().getItemStack().isSimilar(invcloak.getClay())) {
				event.setCancelled(true);
				return;
			}
		}
	}
	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		if(plugin.getcloakplayer().contains(event.getEntity().getUniqueId())) {
			List<ItemStack> drops = event.getDrops(); 
			ListIterator<ItemStack> litr = drops.listIterator();	  
			Player player= (Player) event.getEntity();
			while( litr.hasNext() )
			{
				ItemStack stack = litr.next();

				if(stack.isSimilar(invcloak.getCloak()) || stack.isSimilar(invcloak.getClay()) )
				{
					player.getInventory().remove(invcloak.getClay());
					player.getInventory().remove(invcloak.getCloak());
					litr.remove();
				}
			}
			Bukkit.getScheduler().cancelTask(id1);
			Bukkit.getScheduler().cancelTask(id2);
			task.cancel();
		}
	}
	@EventHandler
	public void addTime(EntityDeathEvent event) {
		Player killer = event.getEntity().getKiller();
		if(event.getEntity() instanceof Player && !(plugin.getcloakplayer().contains(event.getEntity().getUniqueId()))) {
			if(plugin.getcloakplayer().contains(killer.getUniqueId())) {
				
				Main.invtime+=10;
				killer.sendMessage("Now you can be invis for: "+ChatColor.AQUA+Main.invtime+" sec");
				
			}
		}
	}
	@EventHandler
	public void elytraRespawn(PlayerRespawnEvent event) {
		if(plugin.getcloakplayer().contains(event.getPlayer().getUniqueId())) {
			event.getPlayer().getInventory().addItem(invcloak.getCloak());
			return;
		}
		else return;
	}

	@EventHandler
	public void rightclickCloak(PlayerInteractEvent event) {
		ItemStack item = event.getItem();
		Action action = event.getAction();
		if(plugin.getcloakplayer().contains(event.getPlayer().getUniqueId())) {
			Player player = event.getPlayer();
			if (item != null && item.isSimilar(invcloak.getCloak())) {
				if(action==Action.RIGHT_CLICK_AIR || action==Action.RIGHT_CLICK_BLOCK) {
					if((player.getInventory().getChestplate())==null) {
						player.getInventory().setChestplate(null);
					}
					int slot1 = -1;

					for (int i = 0; i < player.getInventory().getSize(); i++) {
						if(player.getInventory().getItem(i)==null) continue;
						if (!(player.getInventory().getItem(i).isSimilar(invcloak.getCloak()))) continue;

						slot1 = i;
						break;
					}

					if (slot1 == -1) return;
					invcloak.removeCloak(player);
					player.getInventory().setItem(slot1, invcloak.getClay());
					player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY,Main.invtime*20,1));
					player.sendMessage(ChatColor.AQUA+"You turned invisible");
					id1=Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						@Override
						public void run() {
							player.sendMessage(ChatColor.AQUA+"You turned visible");
						}
					}, Main.invtime*20L);
					a=Main.invtime-1;
					task=Bukkit.getScheduler().runTaskTimer(plugin, new Runnable(){
						@Override
						public void run() {
							if(a<0 ) {
								task.cancel();
							}
							else {
							player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED+""+a+ChatColor.WHITE+" seconds left"));
							a--;}
						}}, 0L, 20L);
					id2=Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						@Override
						public void run() {
							int slot = -1;

							for (int i = 0; i < player.getInventory().getSize(); i++) {
								if(player.getInventory().getItem(i)==null) continue;
								if (!(player.getInventory().getItem(i).isSimilar(invcloak.getClay()))) continue;

								slot = i;
								break;
							}
							invcloak.removeClay(player);
							player.getInventory().setItem(slot, invcloak.getCloak());

						}
					},Main.invtime*20L);
				}
			}
		}

	}
	@EventHandler
	public void gliding(PlayerMoveEvent e) {
		Player player = e.getPlayer();
		if(plugin.getcloakplayer().contains(player.getUniqueId())) {
			if(player.isGliding()) {
				player.getInventory().setChestplate(null);
				player.getInventory().addItem(invcloak.getCloak());
			}

		}
	}


}

