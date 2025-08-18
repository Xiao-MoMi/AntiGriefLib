package net.momirealms.antigrieflib.comp;

import com.griefdefender.api.GriefDefender;
import com.griefdefender.api.claim.TrustTypes;
import net.momirealms.antigrieflib.AbstractComp;
import net.momirealms.antigrieflib.util.ReflectionUtils;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Optional;

public class GriefDefenderComp extends AbstractComp {

    public GriefDefenderComp(JavaPlugin plugin) {
        super(plugin, "GriefDefender");
    }

    @Override
    public boolean checkClazz() {
        return ReflectionUtils.classExists("com.griefdefender.api.GriefDefender");
    }

    @Override
    public void init() {
    }

    @Override
    public boolean canPlace(Player player, Location location) {
        return Optional.ofNullable(GriefDefender.getCore().getUser(player.getUniqueId()))
                .map(user -> user.canPlace(player.getInventory().getItemInMainHand(), location))
                .orElse(false);
    }

    @Override
    public boolean canBreak(Player player, Location location) {
        return Optional.ofNullable(GriefDefender.getCore().getUser(player.getUniqueId()))
                .map(user -> user.canBreak(location))
                .orElse(false);
    }

    @Override
    public boolean canInteract(Player player, Location location) {
        return Optional.ofNullable(GriefDefender.getCore().getUser(player.getUniqueId()))
                .map(user -> user.canUseBlock(location, TrustTypes.CONTAINER, false, false))
                .orElse(false);
    }

    @Override
    public boolean canInteractEntity(Player player, Entity entity) {
        return Optional.ofNullable(GriefDefender.getCore().getUser(player.getUniqueId()))
                .map(user -> user.canInteractWithEntity(player.getInventory(), entity, TrustTypes.CONTAINER))
                .orElse(false);
    }

    @Override
    public boolean canDamage(Player player, Entity entity) {
        return Optional.ofNullable(GriefDefender.getCore().getUser(player.getUniqueId()))
                .map(user -> user.canHurtEntity(player.getInventory().getItemInMainHand(), entity))
                .orElse(false);
    }
}
