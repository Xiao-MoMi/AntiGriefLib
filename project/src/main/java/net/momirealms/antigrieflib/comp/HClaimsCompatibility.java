package net.momirealms.antigrieflib.comp;

import com.hakan.claim.ClaimPlugin;
import com.hakan.claim.model.member.ClaimMemberPermission;
import com.hakan.claim.service.ClaimService;
import com.hakan.claim.shadow.com.hakan.spinjection.SpigotBootstrap;
import net.momirealms.antigrieflib.AbstractAntiGriefCompatibility;
import net.momirealms.antigrieflib.Flag;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class HClaimsCompatibility extends AbstractAntiGriefCompatibility {

    private ClaimService service;

    public HClaimsCompatibility(Plugin plugin) {
        super(plugin);
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void init() {
        service = SpigotBootstrap.of(ClaimPlugin.class)
                .getInjector().getInstance(ClaimService.class);
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
        return service.findByLocation(location)
                .map(claim -> claim.hasPermission(player, ClaimMemberPermission.BLOCK_PLACE))
                .orElse(true);
    }

    private boolean canBreak(Player player, Location location) {
        return service.findByLocation(location)
                .map(claim -> claim.hasPermission(player, ClaimMemberPermission.BLOCK_BREAK))
                .orElse(true);
    }

    private boolean canInteract(Player player, Location location) {
        return service.findByLocation(location)
                .map(claim -> claim.hasPermission(player, ClaimMemberPermission.INTERACT))
                .orElse(true);
    }

    private boolean canInteractEntity(Player player, Entity entity) {
        return service.findByLocation(entity.getLocation())
                .map(claim -> claim.hasPermission(player, ClaimMemberPermission.INTERACT))
                .orElse(true);
    }

    private boolean canDamageEntity(Player player, Entity entity) {
        return service.findByLocation(entity.getLocation())
                .map(claim -> claim.hasPermission(player, ClaimMemberPermission.DAMAGE_ANIMALS))
                .orElse(true);
    }

    private boolean canOpenContainer(Player player, Location location) {
        return service.findByLocation(location)
                .map(claim -> claim.hasPermission(player, ClaimMemberPermission.INTERACT))
                .orElse(true);
    }

    private boolean canOpenDoor(Player player, Location location) {
        return service.findByLocation(location)
                .map(claim -> claim.hasPermission(player, ClaimMemberPermission.INTERACT))
                .orElse(true);
    }

    private boolean canUseButton(Player player, Location location) {
        return service.findByLocation(location)
                .map(claim -> claim.hasPermission(player, ClaimMemberPermission.INTERACT))
                .orElse(true);
    }

    private boolean canUsePressurePlate(Player player, Location location) {
        return service.findByLocation(location)
                .map(claim -> claim.hasPermission(player, ClaimMemberPermission.INTERACT))
                .orElse(true);
    }
}
