package net.momirealms.antigrieflib.comp;

import me.angeschossen.lands.api.LandsIntegration;
import me.angeschossen.lands.api.flags.type.Flags;
import net.momirealms.antigrieflib.AbstractComp;
import org.bukkit.Location;
import org.bukkit.entity.Enemy;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Optional;

public class LandsComp extends AbstractComp {

    private LandsIntegration api;

    public LandsComp(JavaPlugin plugin) {
        super(plugin, "Lands");
    }

    @Override
    public void init() {
        this.api = LandsIntegration.of(getPlugin());
    }

    @Override
    public boolean canPlace(Player player, Location location) {
        return Optional.ofNullable(api.getWorld(location.getWorld()))
                .map(world ->
                        world.hasRoleFlag(
                            player.getUniqueId(),
                            location,
                            Flags.BLOCK_PLACE
                        )
                ).orElse(true);
    }

    @Override
    public boolean canBreak(Player player, Location location) {
        return Optional.ofNullable(api.getWorld(location.getWorld()))
                .map(world ->
                        world.hasRoleFlag(
                                player.getUniqueId(),
                                location,
                                Flags.BLOCK_BREAK
                        )
                ).orElse(true);
    }

    @Override
    public boolean canInteract(Player player, Location location) {
        return Optional.ofNullable(api.getWorld(location.getWorld()))
                .map(world ->
                        world.hasRoleFlag(
                                player.getUniqueId(),
                                location,
                                Flags.INTERACT_GENERAL
                        )
                ).orElse(true);
    }

    @Override
    public boolean canInteractEntity(Player player, Entity entity) {
        return Optional.ofNullable(api.getWorld(entity.getWorld()))
                .map(world ->
                        world.hasRoleFlag(
                                player.getUniqueId(),
                                entity.getLocation(),
                                Flags.INTERACT_GENERAL
                        )
                ).orElse(true);
    }

    @Override
    public boolean canDamage(Player player, Entity entity) {
        return Optional.ofNullable(api.getWorld(entity.getWorld()))
                .map(world ->
                        world.hasRoleFlag(
                                player.getUniqueId(),
                                entity.getLocation(),
                                entity instanceof Enemy ? Flags.ATTACK_MONSTER : (entity instanceof Player) ? Flags.ATTACK_PLAYER : Flags.ATTACK_ANIMAL
                        )
                ).orElse(true);
    }
}
