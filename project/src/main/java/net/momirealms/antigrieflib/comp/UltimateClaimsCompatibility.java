package net.momirealms.antigrieflib.comp;

import com.craftaro.ultimateclaims.UltimateClaims;
import com.craftaro.ultimateclaims.member.ClaimPerm;
import net.momirealms.antigrieflib.AbstractAntiGriefCompatibility;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Optional;

public class UltimateClaimsCompatibility extends AbstractAntiGriefCompatibility {

    private UltimateClaims ultimateClaims;

    public UltimateClaimsCompatibility(Plugin plugin) {
        super(plugin);
    }

    @Override
    public void init() {
        this.ultimateClaims = (UltimateClaims) Bukkit.getPluginManager().getPlugin("UltimateClaims");
    }

    @Override
    public boolean canPlace(Player player, Location location) {
        return Optional.ofNullable(ultimateClaims.getClaimManager().getClaim(location.getChunk()))
                .map(claim -> claim.playerHasPerms(player, ClaimPerm.PLACE))
                .orElse(true);
    }

    @Override
    public boolean canBreak(Player player, Location location) {
        return Optional.ofNullable(ultimateClaims.getClaimManager().getClaim(location.getChunk()))
                .map(claim -> claim.playerHasPerms(player, ClaimPerm.BREAK))
                .orElse(true);
    }

    @Override
    public boolean canInteract(Player player, Location location) {
        return Optional.ofNullable(ultimateClaims.getClaimManager().getClaim(location.getChunk()))
                .map(claim -> claim.playerHasPerms(player, ClaimPerm.INTERACT))
                .orElse(true);
    }

    @Override
    public boolean canInteractEntity(Player player, Entity entity) {
        return Optional.ofNullable(ultimateClaims.getClaimManager().getClaim(entity.getLocation().getChunk()))
                .map(claim -> claim.playerHasPerms(player, ClaimPerm.INTERACT))
                .orElse(true);
    }

    @Override
    public boolean canDamage(Player player, Entity entity) {
        return Optional.ofNullable(ultimateClaims.getClaimManager().getClaim(entity.getLocation().getChunk()))
                .map(claim -> claim.playerHasPerms(player, ClaimPerm.MOB_KILLING))
                .orElse(true);
    }

    @Override
    public boolean canInteractContainer(Player player, Location location) {
        return Optional.ofNullable(ultimateClaims.getClaimManager().getClaim(location.getChunk()))
                .map(claim -> claim.playerHasPerms(player, ClaimPerm.INTERACT))
                .orElse(true);
    }
}
