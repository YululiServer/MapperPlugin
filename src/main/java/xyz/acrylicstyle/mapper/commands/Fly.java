package xyz.acrylicstyle.mapper.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import xyz.acrylicstyle.tomeito_api.command.PlayerOpCommandExecutor;

public class Fly extends PlayerOpCommandExecutor {
    @Override
    public void onCommand(Player player, String[] args) {
        if (player.getAllowFlight()) {
            player.setFlying(false);
            player.setAllowFlight(false);
            player.sendMessage(ChatColor.YELLOW + "浮遊を" + ChatColor.RED + "オフ" + ChatColor.YELLOW + "にしました。");
        } else {
            player.setAllowFlight(true);
            player.setFlying(true);
            player.sendMessage(ChatColor.YELLOW + "浮遊を" + ChatColor.GREEN + "オン" + ChatColor.YELLOW + "にしました。");
        }
    }
}
