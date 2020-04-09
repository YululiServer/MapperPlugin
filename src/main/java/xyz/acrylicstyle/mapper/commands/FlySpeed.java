package xyz.acrylicstyle.mapper.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.acrylicstyle.tomeito_core.utils.TypeUtil;

public class FlySpeed implements CommandExecutor {
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
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "/flyspeed <-10~10>");
            return true;
        }
        Player player = (Player) sender;
        player.setAllowFlight(true);
        float number;
        if (!TypeUtil.isFloat(args[0])) {
            sender.sendMessage(ChatColor.RED + "/flyspeed <-10~10>");
            return true;
        }
        number = Float.parseFloat(args[0])/10;
        if (number < -1 || number > 1) {
            sender.sendMessage(ChatColor.RED + "/flyspeed <-10~10>");
            return true;
        }
        player.setFlySpeed(number);
        return true;
    }
}
