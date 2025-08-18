package net.momirealms.antigrieflib.comp;

import net.crashcraft.crashclaim.CrashClaim;
import net.crashcraft.crashclaim.api.CrashClaimAPI;
import net.crashcraft.crashclaim.permissions.PermissionRoute;
import net.momirealms.antigrieflib.AbstractComp;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class CrashClaimComp extends AbstractComp {

    public CrashClaimComp(JavaPlugin plugin) {
        super(plugin, "CrashClaim");
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
}
