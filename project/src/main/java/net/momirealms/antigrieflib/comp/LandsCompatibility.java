package net.momirealms.antigrieflib.comp;

import me.angeschossen.lands.api.LandsIntegration;
import me.angeschossen.lands.api.flags.type.Flags;
import net.momirealms.antigrieflib.AbstractAntiGriefCompatibility;
import net.momirealms.antigrieflib.Flag;
import org.bukkit.Location;
import org.bukkit.entity.Enemy;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Optional;

public class LandsCompatibility extends AbstractAntiGriefCompatibility {
    private JavaPlugin self;
    private LandsIntegration api;

    public LandsCompatibility(Plugin plugin, JavaPlugin self) {
        super(plugin);
        this.self = self;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void init() {
        this.api = LandsIntegration.of(this.self);
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
        return Optional.ofNullable(api.getWorld(location.getWorld()))
                .map(world ->
                        world.hasRoleFlag(
                            player.getUniqueId(),
                            location,
                            Flags.BLOCK_PLACE
                        )
                ).orElse(true);
    }

    private boolean canBreak(Player player, Location location) {
        return Optional.ofNullable(api.getWorld(location.getWorld()))
                .map(world ->
                        world.hasRoleFlag(
                                player.getUniqueId(),
                                location,
                                Flags.BLOCK_BREAK
                        )
                ).orElse(true);
    }

    private boolean canInteract(Player player, Location location) {
        return Optional.ofNullable(api.getWorld(location.getWorld()))
                .map(world ->
                        world.hasRoleFlag(
                                player.getUniqueId(),
                                location,
                                Flags.INTERACT_GENERAL
                        )
                ).orElse(true);
    }

    private boolean canInteractEntity(Player player, Entity entity) {
        return Optional.ofNullable(api.getWorld(entity.getWorld()))
                .map(world ->
                        world.hasRoleFlag(
                                player.getUniqueId(),
                                entity.getLocation(),
                                Flags.INTERACT_GENERAL
                        )
                ).orElse(true);
    }

    private boolean canDamageEntity(Player player, Entity entity) {
        return Optional.ofNullable(api.getWorld(entity.getWorld()))
                .map(world ->
                        world.hasRoleFlag(
                                player.getUniqueId(),
                                entity.getLocation(),
                                entity instanceof Enemy ? Flags.ATTACK_MONSTER : (entity instanceof Player) ? Flags.ATTACK_PLAYER : Flags.ATTACK_ANIMAL
                        )
                ).orElse(true);
    }

    private boolean canOpenContainer(Player player, Location location) {
        return Optional.ofNullable(api.getWorld(location.getWorld()))
                .map(world ->
                        world.hasRoleFlag(
                                player.getUniqueId(),
                                location,
                                Flags.INTERACT_CONTAINER
                        )
                ).orElse(true);
    }

    private boolean canOpenDoor(Player player, Location location) {
        return Optional.ofNullable(api.getWorld(location.getWorld()))
                .map(world ->
                        world.hasRoleFlag(
                                player.getUniqueId(),
                                location,
                                Flags.INTERACT_DOOR
                        )
                ).orElse(true);
    }

    private boolean canUseButton(Player player, Location location) {
        return Optional.ofNullable(api.getWorld(location.getWorld()))
                .map(world ->
                        world.hasRoleFlag(
                                player.getUniqueId(),
                                location,
                                Flags.INTERACT_GENERAL
                        )
                ).orElse(true);
    }

    private boolean canUsePressurePlate(Player player, Location location) {
        return Optional.ofNullable(api.getWorld(location.getWorld()))
                .map(world ->
                        world.hasRoleFlag(
                                player.getUniqueId(),
                                location,
                                Flags.INTERACT_GENERAL
                        )
                ).orElse(true);
    }
}
