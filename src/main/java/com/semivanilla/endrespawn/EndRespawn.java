package com.semivanilla.endrespawn;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class EndRespawn extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("endrespawn").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        World endWorld = getServer().getWorlds().stream().filter(w -> w.getEnvironment() == World.Environment.THE_END).findFirst().orElse(null);
        World overworld = getServer().getWorlds().stream().filter(w -> w.getEnvironment() == World.Environment.NORMAL).findFirst().orElse(null);
        if (endWorld == null){
            sender.sendMessage("The end world does not exist!");
            return true;
        }
        for (Player player : endWorld.getPlayers()) {
            Location spawn = player.getBedSpawnLocation();
            if (spawn == null) {
                sender.sendMessage("Player " + player.getName() + " has no bed!");
                player.teleport(overworld.getSpawnLocation());
                continue;
            }
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"forcertp " + player.getName() + " -c end-respawn");
        }
        return true;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
