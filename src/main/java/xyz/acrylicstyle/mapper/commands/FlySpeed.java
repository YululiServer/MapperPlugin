package xyz.acrylicstyle.mapper.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import xyz.acrylicstyle.tomeito_api.command.PlayerOpCommandExecutor;
import xyz.acrylicstyle.tomeito_api.utils.TypeUtil;

public class FlySpeed extends PlayerOpCommandExecutor {
    @Override
    public void onCommand(Player sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "/flyspeed <-10~10>");
            return;
        }
        sender.setAllowFlight(true);
        float number;
        if (!TypeUtil.isFloat(args[0])) {
            sender.sendMessage(ChatColor.RED + "/flyspeed <-10~10>");
            return;
        }
        number = Float.parseFloat(args[0])/10;
        if (number < -1 || number > 1) {
            sender.sendMessage(ChatColor.RED + "/flyspeed <-10~10>");
            return;
        }
        sender.setFlySpeed(number);
    }
}
