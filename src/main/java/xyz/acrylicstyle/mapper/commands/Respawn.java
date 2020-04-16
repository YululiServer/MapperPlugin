package xyz.acrylicstyle.mapper.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.acrylicstyle.tomeito_api.command.OpCommandExecutor;

public class Respawn extends OpCommandExecutor {
    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "/respawn <Player>");
            return;
        }
        Player player = Bukkit.getPlayerExact(args[0]);
        if (player == null) {
            sender.sendMessage(ChatColor.RED + "Player " + args[0] + " could not be found");
            return;
        }
        player.spigot().respawn();
        sender.sendMessage(ChatColor.GREEN + "Respawned the player. (If the player was dead)");
    }
}
