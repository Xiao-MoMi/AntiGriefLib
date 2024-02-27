package net.momirealms.antigrieflib.comp;

import net.momirealms.antigrieflib.AbstractComp;
import net.momirealms.antigrieflib.CustomFlag;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Method;

public class ResidenceComp extends AbstractComp implements CustomFlag {

    private Object residenceManager;
    private Method getByLocMethod;
    private Method getPermissionsMethod;
    private Method playerHasMethod;
    Class<?> flagsClass;

    public ResidenceComp(JavaPlugin plugin) {
        super(plugin, "Residence");
    }

    @Override
    public void init() {
        try {
            Class<?> residenceClass = Class.forName("com.bekvon.bukkit.residence.Residence");
            Object residenceInstance = residenceClass.getMethod("getInstance").invoke(null);
            residenceManager = residenceClass.getMethod("getResidenceManager").invoke(residenceInstance);
            getByLocMethod = residenceManager.getClass().getMethod("getByLoc", Location.class);
            Class<?> claimedResidenceClass = Class.forName("com.bekvon.bukkit.residence.protection.ClaimedResidence");
            getPermissionsMethod = claimedResidenceClass.getMethod("getPermissions");
            flagsClass = Class.forName("com.bekvon.bukkit.residence.containers.Flags");
            Class<?> residencePermissionsClass = Class.forName("com.bekvon.bukkit.residence.protection.ResidencePermissions");
            playerHasMethod = residencePermissionsClass.getMethod("playerHas", Player.class, flagsClass, boolean.class);
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean canPlace(Player player, Location location) {
        return checkPermission(player, location, "place");
    }

    @Override
    public boolean canBreak(Player player, Location location) {
        return checkPermission(player, location, "destroy");
    }

    @Override
    public boolean canInteract(Player player, Location location) {
        return checkPermission(player, location, "container");
    }

    private boolean checkPermission(Player player, Location location, String flag) {
        try {
            Object claimedResidence = getByLocMethod.invoke(residenceManager, location);
            if (claimedResidence == null) {
                return true;
            }
            Object permissions = getPermissionsMethod.invoke(claimedResidence);
            Object flagEnum = Enum.valueOf((Class<Enum>) flagsClass, flag.toUpperCase());
            return (boolean) playerHasMethod.invoke(permissions, player, flagEnum, true);
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
            return false;
        }
    }
}
