package net.momirealms.antigrieflib.comp;

import com.griefdefender.api.GriefDefender;
import com.griefdefender.api.User;
import com.griefdefender.api.claim.TrustTypes;
import net.momirealms.antigrieflib.AbstractAntiGriefCompatibility;
import net.momirealms.antigrieflib.Flag;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Optional;

public class GriefDefenderCompatibility extends AbstractAntiGriefCompatibility {

    public GriefDefenderCompatibility(Plugin plugin) {
        super(plugin);
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void init() {
        registerFlagTester(Flag.PLACE, this::canPlace);
        registerFlagTester(Flag.BREAK, this::canBreak);
        registerFlagTester(Flag.INTERACT, this::canInteract);
        registerFlagTester(Flag.INTERACT_ENTITY, this::canInteractEntity);
        registerFlagTester(Flag.DAMAGE_ENTITY, this::canDamageEntity);
        registerFlagTester(Flag.OPEN_CONTAINER, this::canOpenContainer);
        registerFlagTester(Flag.OPEN_DOOR, this::canOpenDoor);
        registerFlagTester(Flag.USE_BUTTON, this::canUseButton);
        registerFlagTester(Flag.USE_PRESSURE_PLATE, this::canUsePressurePlate);
    }

    private boolean canPlace(Player player, Location location) {
        return Optional.ofNullable(GriefDefender.getCore().getUser(player.getUniqueId()))
                .map(User::getPlayerData)
                .map(data -> data.canPlace(player.getInventory().getItemInMainHand(), location))
                .orElse(false);
    }

    private boolean canBreak(Player player, Location location) {
        return Optional.ofNullable(GriefDefender.getCore().getUser(player.getUniqueId()))
                .map(User::getPlayerData)
                .map(data -> data.canBreak(location))
                .orElse(false);
    }

    private boolean canInteract(Player player, Location location) {
        return Optional.ofNullable(GriefDefender.getCore().getUser(player.getUniqueId()))
                .map(User::getPlayerData)
                .map(data -> data.canUseBlock(location, TrustTypes.CONTAINER, false, false))
                .orElse(false);
    }

    private boolean canInteractEntity(Player player, Entity entity) {
        return Optional.ofNullable(GriefDefender.getCore().getUser(player.getUniqueId()))
                .map(User::getPlayerData)
                .map(data -> data.canInteractWithEntity(player.getInventory(), entity, TrustTypes.CONTAINER))
                .orElse(false);
    }

    private boolean canDamageEntity(Player player, Entity entity) {
        return Optional.ofNullable(GriefDefender.getCore().getUser(player.getUniqueId()))
                .map(User::getPlayerData)
                .map(data -> data.canHurtEntity(player.getInventory().getItemInMainHand(), entity))
                .orElse(false);
    }

    private boolean canOpenContainer(Player player, Location location) {
        return Optional.ofNullable(GriefDefender.getCore().getUser(player.getUniqueId()))
                .map(User::getPlayerData)
                .map(data -> data.canUseBlock(location, TrustTypes.CONTAINER, false, false))
                .orElse(false);
    }

    private boolean canOpenDoor(Player player, Location location) {
        return Optional.ofNullable(GriefDefender.getCore().getUser(player.getUniqueId()))
                .map(User::getPlayerData)
                .map(data -> data.canUseBlock(location, TrustTypes.BUILDER, false, false))
                .orElse(false);
    }

    private boolean canUseButton(Player player, Location location) {
        return Optional.ofNullable(GriefDefender.getCore().getUser(player.getUniqueId()))
                .map(User::getPlayerData)
                .map(data -> data.canUseBlock(location, TrustTypes.BUILDER, false, false))
                .orElse(false);
    }

    private boolean canUsePressurePlate(Player player, Location location) {
        return Optional.ofNullable(GriefDefender.getCore().getUser(player.getUniqueId()))
                .map(User::getPlayerData)
                .map(data -> data.canUseBlock(location, TrustTypes.BUILDER, false, false))
                .orElse(false);
    }
}
