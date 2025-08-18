package net.momirealms.antigrieflib.comp;

import com.craftaro.ultimateclaims.UltimateClaims;
import com.craftaro.ultimateclaims.member.ClaimPerm;
import net.momirealms.antigrieflib.AbstractComp;
import net.momirealms.antigrieflib.util.ReflectionUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Optional;

public class UltimateClaimsComp extends AbstractComp {

    private UltimateClaims ultimateClaims;

    public UltimateClaimsComp(JavaPlugin plugin) {
        super(plugin, "UltimateClaims");
    }

    @Override
    public boolean checkClazz() {
        return ReflectionUtils.classExists("com.craftaro.ultimateclaims.UltimateClaims");
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
}
