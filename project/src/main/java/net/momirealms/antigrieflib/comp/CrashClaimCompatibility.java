package net.momirealms.antigrieflib.comp;

import net.crashcraft.crashclaim.CrashClaim;
import net.crashcraft.crashclaim.api.CrashClaimAPI;
import net.crashcraft.crashclaim.permissions.PermissionRoute;
import net.momirealms.antigrieflib.AbstractAntiGriefCompatibility;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class CrashClaimCompatibility extends AbstractAntiGriefCompatibility {

    public CrashClaimCompatibility(Plugin plugin) {
        super(plugin);
    }

    private CrashClaimAPI api;

    @Override
    public void init() {
        api = CrashClaim.getPlugin().getApi();
    }

    @Override
    public boolean canPlace(Player player, Location location) {
        return api.getPermissionHelper().hasPermission(player.getUniqueId(), location, PermissionRoute.BUILD);
    }

    @Override
    public boolean canBreak(Player player, Location location) {
        return api.getPermissionHelper().hasPermission(player.getUniqueId(), location, PermissionRoute.BUILD);
    }

    @Override
    public boolean canInteract(Player player, Location location) {
        return api.getPermissionHelper().hasPermission(player.getUniqueId(), location, PermissionRoute.INTERACTIONS);
    }

    @Override
    public boolean canInteractEntity(Player player, Entity entity) {
        return api.getPermissionHelper().hasPermission(player.getUniqueId(), entity.getLocation(), PermissionRoute.ENTITIES);
    }

    @Override
    public boolean canDamage(Player player, Entity entity) {
        return api.getPermissionHelper().hasPermission(player.getUniqueId(), entity.getLocation(), PermissionRoute.ENTITIES);
    }

    @Override
    public boolean canOpenContainer(Player player, Location location) {
        return api.getPermissionHelper().hasPermission(player.getUniqueId(), location, PermissionRoute.CONTAINERS);
    }
}
