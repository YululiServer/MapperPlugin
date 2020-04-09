package xyz.acrylicstyle.mapper.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Respawn implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage(ChatColor.RED + "You don't have permission to do that.");
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "/respawn <Player>");
            return true;
        }
        Player player = Bukkit.getPlayerExact(args[0]);
        if (player == null) {
            sender.sendMessage(ChatColor.RED + "Player " + args[0] + " could not be found");
            return true;
        }
        player.spigot().respawn();
        sender.sendMessage(ChatColor.GREEN + "Respawned the player. (If the player was dead)");
        return true;
    }
}
