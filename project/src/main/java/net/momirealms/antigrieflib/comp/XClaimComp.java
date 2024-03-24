package net.momirealms.antigrieflib.comp;

import codes.wasabi.xclaim.api.Claim;
import codes.wasabi.xclaim.api.enums.EntityGroup;
import codes.wasabi.xclaim.api.enums.Permission;
import net.momirealms.antigrieflib.AbstractComp;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.Optional;

public class XClaimComp extends AbstractComp {


    public XClaimComp(JavaPlugin plugin) {
        super(plugin, "XClaim");
    }

    @Override
    public void init() {
    }

    @Override
    public boolean canPlace(Player player, Location location) {
        return Optional.ofNullable(Claim.getByChunk(location.getChunk()))
                .map(claim -> claim.getUserPermission(player, Permission.BUILD))
                .orElse(true);
    }

    @Override
    public boolean canBreak(Player player, Location location) {
        return Optional.ofNullable(Claim.getByChunk(location.getChunk()))
                .map(claim -> claim.getUserPermission(player, Permission.BREAK))
                .orElse(true);
    }

    @Override
    public boolean canInteract(Player player, Location location) {
        return Optional.ofNullable(Claim.getByChunk(location.getChunk()))
                .map(claim -> claim.getUserPermission(player, Permission.INTERACT))
                .orElse(true);
    }

    @Override
    public boolean canInteractEntity(Player player, Entity entity) {
        return Optional.ofNullable(Claim.getByChunk(entity.getLocation().getChunk()))
                .map(claim -> claim.getUserPermission(player, Permission.INTERACT))
                .orElse(true);
    }

    @Override
    public boolean canDamage(Player player, Entity entity) {
        final Optional<EntityGroup> og = Arrays.stream(EntityGroup.values()).filter(g -> g.contains(entity)).findFirst();
        if (og.isEmpty() && (!(entity instanceof Player))) return true;
        final EntityGroup group = og.get();
        final Permission per = switch (group.name().toUpperCase()) {
            case "FRIENDLY" -> Permission.ENTITY_DAMAGE_FRIENDLY;
            case "HOSTILE" -> Permission.ENTITY_DAMAGE_HOSTILE;
            case "VEHICLE" -> Permission.ENTITY_DAMAGE_VEHICLE;
            case "NOT_ALIVE" -> Permission.ENTITY_DAMAGE_NL;
            case "MISC" -> Permission.ENTITY_DAMAGE_MISC;
            default -> null;
        };
        // FIXME: I'm failed to find any permission of PVP
        if (per == null) {
            return player.getWorld().getPVP();
        } else {
            return Optional.ofNullable(Claim.getByChunk(entity.getChunk()))
                    .map(claim -> claim.getUserPermission(player, per))
                    .orElse(true);
        }
    }
}
