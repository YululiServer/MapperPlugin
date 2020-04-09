package xyz.acrylicstyle.mapper.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Fly implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command cannot be invoked from console.");
            return true;
        }
        if (!sender.isOp()) {
            sender.sendMessage(ChatColor.RED + "You don't have permission to do that.");
            return true;
        }
        Player player = (Player) sender;
        if (player.getAllowFlight()) {
            player.setFlying(false);
            player.setAllowFlight(false);
            sender.sendMessage(ChatColor.YELLOW + "浮遊を" + ChatColor.RED + "オフ" + ChatColor.YELLOW + "にしました。");
        } else {
            player.setAllowFlight(true);
            player.setFlying(true);
            sender.sendMessage(ChatColor.YELLOW + "浮遊を" + ChatColor.GREEN + "オン" + ChatColor.YELLOW + "にしました。");
        }
        return true;
    }
}
