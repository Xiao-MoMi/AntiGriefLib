package net.momirealms.antigrieflib.comp;

import com.griefdefender.api.GriefDefender;
import com.griefdefender.api.User;
import com.griefdefender.api.claim.TrustTypes;
import net.momirealms.antigrieflib.AbstractAntiGriefCompatibility;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Optional;

public class GriefDefenderCompatibility extends AbstractAntiGriefCompatibility {

    public GriefDefenderCompatibility(Plugin plugin) {
        super(plugin);
    }

    @Override
    public void init() {
    }

    @Override
    public boolean canPlace(Player player, Location location) {
        return Optional.ofNullable(GriefDefender.getCore().getUser(player.getUniqueId()))
                .map(User::getPlayerData)
                .map(data -> data.canPlace(player.getInventory().getItemInMainHand(), location))
                .orElse(false);
    }

    @Override
    public boolean canBreak(Player player, Location location) {
        return Optional.ofNullable(GriefDefender.getCore().getUser(player.getUniqueId()))
                .map(User::getPlayerData)
                .map(data -> data.canBreak(location))
                .orElse(false);
    }

    @Override
    public boolean canInteract(Player player, Location location) {
        return Optional.ofNullable(GriefDefender.getCore().getUser(player.getUniqueId()))
                .map(User::getPlayerData)
                .map(data -> data.canUseBlock(location, TrustTypes.CONTAINER, false, false))
                .orElse(false);
    }

    @Override
    public boolean canInteractEntity(Player player, Entity entity) {
        return Optional.ofNullable(GriefDefender.getCore().getUser(player.getUniqueId()))
                .map(User::getPlayerData)
                .map(data -> data.canInteractWithEntity(player.getInventory(), entity, TrustTypes.CONTAINER))
                .orElse(false);
    }

    @Override
    public boolean canDamage(Player player, Entity entity) {
        return Optional.ofNullable(GriefDefender.getCore().getUser(player.getUniqueId()))
                .map(User::getPlayerData)
                .map(data -> data.canHurtEntity(player.getInventory().getItemInMainHand(), entity))
                .orElse(false);
    }

    @Override
    public boolean canInteractContainer(Player player, Location location) {
        return Optional.ofNullable(GriefDefender.getCore().getUser(player.getUniqueId()))
                .map(User::getPlayerData)
                .map(data -> data.canUseBlock(location, TrustTypes.CONTAINER, false, false))
                .orElse(false);
    }
}
