package xyz.acrylicstyle.mapper;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.acrylicstyle.mapper.commands.*;
import xyz.acrylicstyle.mapper.utils.Utils;
import xyz.acrylicstyle.tomeito_api.utils.Log;

public class Mapper extends JavaPlugin implements Listener {
    private static Mapper instance;
    private boolean disableEvents = false;

    @Override
    public void onLoad() { instance = this; }

    @Override
    public void onEnable() {
        Bukkit.getPluginCommand("fly").setExecutor(new Fly());
        Bukkit.getPluginCommand("flyspeed").setExecutor(new FlySpeed());
        Bukkit.getPluginCommand("mapperhelp").setExecutor(new MapperHelp());
        Bukkit.getPluginCommand("export").setExecutor(new Export());
        Bukkit.getPluginCommand("respawn").setExecutor(new Respawn());
        disableEvents = this.getConfig().getBoolean("disableEvents", false);
        Bukkit.getPluginManager().registerEvents(this, this);
        for (Player player : Bukkit.getOnlinePlayers()) doJob(player);
        Log.info("Enabled Mapper");
    }

    public static Mapper getInstance() {
        return instance;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) { doJob(e.getPlayer()); }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) { doJob(e.getPlayer()); }

    @EventHandler
    public void onPlayerChangedWorld(PlayerChangedWorldEvent e) {
        new BukkitRunnable() {
            @Override
            public void run() {
                doJob(e.getPlayer());
            }
        }.runTaskLater(this, 5);
    }

    private void doJob(Player player) {
        if (disableEvents) return;
        if (player.isOp()) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    player.sendMessage(ChatColor.YELLOW + "/mapperhelp" + ChatColor.AQUA + "でコマンド一覧を表示します。");
                    player.setGameMode(GameMode.CREATIVE);
                    player.setAllowFlight(true);
                    player.setFlying(true);
                    setItemIfNotExists(player.getInventory(), 7, Utils.getItemStack(Material.WOOD_AXE, 1, ChatColor.GREEN + "WorldEdit"));
                    setItemIfNotExists(player.getInventory(), 8, Utils.getItemStack(Material.COMPASS, 1, ChatColor.GREEN + "コンパス"));
                }
            }.runTaskLater(this, 5);
        }
    }

    private void setItemIfNotExists(Inventory inventory, int slot, ItemStack item) {
        if (inventory.getItem(slot) == null || inventory.getItem(slot).getType() == Material.AIR) inventory.setItem(slot, item);
    }
}
