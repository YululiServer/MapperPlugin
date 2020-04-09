package xyz.acrylicstyle.mapper.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MapperHelp implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage(ChatColor.RED + "You don't have permission to do that.");
            return true;
        }
        sender.sendMessage(constructHelp("/flyspeed <-10~10>", "浮遊速度を設定します。0未満に設定すると反対方向に移動します。"));
        sender.sendMessage(constructHelp("/fly", "浮遊状態を変更します。"));
        sender.sendMessage(constructHelp("/export <ワールド>", "ワールドを外部にエクスポートします。"));
        sender.sendMessage(constructHelp("/respawn <プレイヤー>", "プレイヤーを強制的にリスポーンさせます。"));
        return true;
    }

    public String constructHelp(String cmd, String description) {
        return ChatColor.GRAY + " ・ " + ChatColor.AQUA + cmd + ChatColor.GRAY + " - " + ChatColor.YELLOW + description;
    }
}
