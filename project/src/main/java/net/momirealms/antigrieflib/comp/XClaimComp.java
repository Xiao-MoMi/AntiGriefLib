package net.momirealms.antigrieflib.comp;

import codes.wasabi.xclaim.api.Claim;
import codes.wasabi.xclaim.api.enums.Permission;
import net.momirealms.antigrieflib.AbstractComp;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

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
}
