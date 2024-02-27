package net.momirealms.antigrieflib.comp;

import net.momirealms.antigrieflib.AbstractComp;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Method;

public class CrashClaimComp extends AbstractComp {

    private Object permissionHelper;
    private Class<?> permissionRouteClass;
    private Method hasPermissionMethod;

    public CrashClaimComp(JavaPlugin plugin) {
        super(plugin, "CrashClaim");
    }

    @Override
    public void init() {
        try {
            Class<?> crashClaimClass = Class.forName("net.crashcraft.crashclaim.CrashClaim");
            Object crashClaimPlugin = crashClaimClass.getMethod("getPlugin").invoke(null);
            Object api = crashClaimPlugin.getClass().getMethod("getApi").invoke(crashClaimPlugin);
            permissionHelper = api.getClass().getMethod("getPermissionHelper").invoke(api);
            permissionRouteClass = Class.forName("net.crashcraft.crashclaim.permissions.PermissionRoute");
            hasPermissionMethod = permissionHelper.getClass().getMethod("hasPermission", java.util.UUID.class, Location.class, permissionRouteClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean canPlace(Player player, Location location) {
        return checkPermission(player, location, "BUILD");
    }

    @Override
    public boolean canBreak(Player player, Location location) {
        return checkPermission(player, location, "BUILD");
    }

    @Override
    public boolean canInteract(Player player, Location location) {
        return checkPermission(player, location, "INTERACTIONS");
    }

    private boolean checkPermission(Player player, Location location, String permissionRoute) {
        try {
            Object permissionRouteEnum = Enum.valueOf((Class<Enum>) permissionRouteClass, permissionRoute);
            return (boolean) hasPermissionMethod.invoke(permissionHelper, player.getUniqueId(), location, permissionRouteEnum);
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
            return false;
        }
    }
}
