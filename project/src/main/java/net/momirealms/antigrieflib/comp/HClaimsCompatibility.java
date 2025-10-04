package net.momirealms.antigrieflib.comp;

import com.hakan.claim.ClaimPlugin;
import com.hakan.claim.model.member.ClaimMemberPermission;
import com.hakan.claim.service.ClaimService;
import com.hakan.claim.shadow.com.hakan.spinjection.SpigotBootstrap;
import net.momirealms.antigrieflib.AbstractAntiGriefCompatibility;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class HClaimsCompatibility extends AbstractAntiGriefCompatibility {

    private ClaimService service;

    public HClaimsCompatibility(Plugin plugin) {
        super(plugin);
    }

    @Override
    public void init() {
        service = SpigotBootstrap.of(ClaimPlugin.class)
                .getInjector().getInstance(ClaimService.class);
    }

    @Override
    public boolean canPlace(Player player, Location location) {
        return service.findByLocation(location)
                .map(claim -> claim.hasPermission(player, ClaimMemberPermission.BLOCK_PLACE))
                .orElse(true);
    }

    @Override
    public boolean canBreak(Player player, Location location) {
        return service.findByLocation(location)
                .map(claim -> claim.hasPermission(player, ClaimMemberPermission.BLOCK_BREAK))
                .orElse(true);
    }

    @Override
    public boolean canInteract(Player player, Location location) {
        return service.findByLocation(location)
                .map(claim -> claim.hasPermission(player, ClaimMemberPermission.INTERACT))
                .orElse(true);
    }

    @Override
    public boolean canInteractEntity(Player player, Entity entity) {
        return service.findByLocation(entity.getLocation())
                .map(claim -> claim.hasPermission(player, ClaimMemberPermission.INTERACT))
                .orElse(true);
    }

    @Override
    public boolean canDamage(Player player, Entity entity) {
        return service.findByLocation(entity.getLocation())
                .map(claim -> claim.hasPermission(player, ClaimMemberPermission.DAMAGE_ANIMALS))
                .orElse(true);
    }
}
