package net.momirealms.antigrieflib.comp;

import net.crashcraft.crashclaim.CrashClaim;
import net.crashcraft.crashclaim.api.CrashClaimAPI;
import net.crashcraft.crashclaim.permissions.PermissionRoute;
import net.momirealms.antigrieflib.AbstractAntiGriefCompatibility;
import net.momirealms.antigrieflib.Flag;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class CrashClaimCompatibility extends AbstractAntiGriefCompatibility {

    public CrashClaimCompatibility(Plugin plugin) {
        super(plugin);
    }

    private CrashClaimAPI api;

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void init() {
        api = CrashClaim.getPlugin().getApi();
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
        return api.getPermissionHelper().hasPermission(player.getUniqueId(), location, PermissionRoute.BUILD);
    }

    private boolean canBreak(Player player, Location location) {
        return api.getPermissionHelper().hasPermission(player.getUniqueId(), location, PermissionRoute.BUILD);
    }

    private boolean canInteract(Player player, Location location) {
        return api.getPermissionHelper().hasPermission(player.getUniqueId(), location, PermissionRoute.INTERACTIONS);
    }

    private boolean canInteractEntity(Player player, Entity entity) {
        return api.getPermissionHelper().hasPermission(player.getUniqueId(), entity.getLocation(), PermissionRoute.ENTITIES);
    }

    private boolean canDamageEntity(Player player, Entity entity) {
        return api.getPermissionHelper().hasPermission(player.getUniqueId(), entity.getLocation(), PermissionRoute.ENTITIES);
    }

    private boolean canOpenContainer(Player player, Location location) {
        return api.getPermissionHelper().hasPermission(player.getUniqueId(), location, PermissionRoute.CONTAINERS);
    }

    private boolean canOpenDoor(Player player, Location location) {
        return api.getPermissionHelper().hasPermission(player.getUniqueId(), location, PermissionRoute.INTERACTIONS);
    }

    private boolean canUseButton(Player player, Location location) {
        return api.getPermissionHelper().hasPermission(player.getUniqueId(), location, PermissionRoute.INTERACTIONS);
    }

    private boolean canUsePressurePlate(Player player, Location location) {
        return api.getPermissionHelper().hasPermission(player.getUniqueId(), location, PermissionRoute.INTERACTIONS);
    }
}
