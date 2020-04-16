package xyz.acrylicstyle.mapper.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import xyz.acrylicstyle.tomeito_api.command.OpCommandExecutor;

public class MapperHelp extends OpCommandExecutor {
    @Override
    public void onCommand(CommandSender sender, String[] args) {
        sender.sendMessage(constructHelp("/flyspeed <-10~10>", "浮遊速度を設定します。0未満に設定すると反対方向に移動します。"));
        sender.sendMessage(constructHelp("/fly", "浮遊状態を変更します。"));
        sender.sendMessage(constructHelp("/export <ワールド>", "ワールドを外部にエクスポートします。"));
        sender.sendMessage(constructHelp("/respawn <プレイヤー>", "プレイヤーを強制的にリスポーンさせます。"));
    }

    public String constructHelp(String cmd, String description) {
        return ChatColor.GRAY + " ・ " + ChatColor.AQUA + cmd + ChatColor.GRAY + " - " + ChatColor.YELLOW + description;
    }
}
