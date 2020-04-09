package xyz.acrylicstyle.mapper.commands;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.acrylicstyle.mapper.Mapper;
import xyz.acrylicstyle.mapper.utils.Utils;
import xyz.acrylicstyle.tomeito_core.utils.Log;

import java.io.File;
import java.io.IOException;

public class Export implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage(ChatColor.RED + "You don't have permission to do that.");
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "You must specify world.");
            return true;
        }
        World world = Bukkit.getWorld(args[0]);
        if (world == null) {
            sender.sendMessage(ChatColor.RED + "World " + args[0] + " could not be found.");
            return true;
        }
        world.save();
        String dir = "./" + world.getName();
        String zip = "./exports/" + world.getName() + ".zip";
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    //noinspection ResultOfMethodCallIgnored
                    new File(zip).delete();
                    FileUtils.deleteDirectory(new File("./exports/" + world.getName()));
                    File to = new File("./exports/" + world.getName());
                    //noinspection ResultOfMethodCallIgnored
                    new File("./exports/").mkdir();
                    Log.debug("Copying files from: " + new File(dir).getAbsolutePath());
                    Log.debug("To: " + to.getAbsolutePath());
                    FileUtils.copyDirectory(new File(dir), to);
                    File session = new File("./exports/" + world.getName() + "/session.lock");
                    //noinspection ResultOfMethodCallIgnored
                    session.delete();
                    Utils.compressDirectory("./exports/" + world.getName(), zip);
                    String url = Utils.uploadFile(zip);
                    sender.sendMessage(ChatColor.GREEN + "Successfully exported world: " + url);
                } catch (IOException e) {
                    sender.sendMessage(ChatColor.RED + "An error occurred while exporting world");
                    e.printStackTrace();
                }
            }
        }.runTaskAsynchronously(Mapper.getInstance());
        return true;
    }
}
