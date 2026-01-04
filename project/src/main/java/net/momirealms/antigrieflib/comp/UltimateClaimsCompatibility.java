package net.momirealms.antigrieflib.comp;

import com.craftaro.ultimateclaims.UltimateClaims;
import com.craftaro.ultimateclaims.member.ClaimPerm;
import net.momirealms.antigrieflib.AbstractAntiGriefCompatibility;
import net.momirealms.antigrieflib.Flag;
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

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void init() {
        this.ultimateClaims = (UltimateClaims) Bukkit.getPluginManager().getPlugin("UltimateClaims");
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
        return Optional.ofNullable(ultimateClaims.getClaimManager().getClaim(location.getChunk()))
                .map(claim -> claim.playerHasPerms(player, ClaimPerm.PLACE))
                .orElse(true);
    }

    private boolean canBreak(Player player, Location location) {
        return Optional.ofNullable(ultimateClaims.getClaimManager().getClaim(location.getChunk()))
                .map(claim -> claim.playerHasPerms(player, ClaimPerm.BREAK))
                .orElse(true);
    }

    private boolean canInteract(Player player, Location location) {
        return Optional.ofNullable(ultimateClaims.getClaimManager().getClaim(location.getChunk()))
                .map(claim -> claim.playerHasPerms(player, ClaimPerm.INTERACT))
                .orElse(true);
    }

    private boolean canInteractEntity(Player player, Entity entity) {
        return Optional.ofNullable(ultimateClaims.getClaimManager().getClaim(entity.getLocation().getChunk()))
                .map(claim -> claim.playerHasPerms(player, ClaimPerm.INTERACT))
                .orElse(true);
    }

    private boolean canDamageEntity(Player player, Entity entity) {
        return Optional.ofNullable(ultimateClaims.getClaimManager().getClaim(entity.getLocation().getChunk()))
                .map(claim -> claim.playerHasPerms(player, ClaimPerm.MOB_KILLING))
                .orElse(true);
    }

    private boolean canOpenContainer(Player player, Location location) {
        return Optional.ofNullable(ultimateClaims.getClaimManager().getClaim(location.getChunk()))
                .map(claim -> claim.playerHasPerms(player, ClaimPerm.INTERACT))
                .orElse(true);
    }

    private boolean canOpenDoor(Player player, Location location) {
        return Optional.ofNullable(ultimateClaims.getClaimManager().getClaim(location.getChunk()))
                .map(claim -> claim.playerHasPerms(player, ClaimPerm.DOORS))
                .orElse(true);
    }

    private boolean canUseButton(Player player, Location location) {
        return Optional.ofNullable(ultimateClaims.getClaimManager().getClaim(location.getChunk()))
                .map(claim -> claim.playerHasPerms(player, ClaimPerm.INTERACT))
                .orElse(true);
    }

    private boolean canUsePressurePlate(Player player, Location location) {
        return Optional.ofNullable(ultimateClaims.getClaimManager().getClaim(location.getChunk()))
                .map(claim -> claim.playerHasPerms(player, ClaimPerm.INTERACT))
                .orElse(true);
    }
}
