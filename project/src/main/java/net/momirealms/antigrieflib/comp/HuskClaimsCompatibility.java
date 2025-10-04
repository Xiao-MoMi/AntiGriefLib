package net.momirealms.antigrieflib.comp;

import net.momirealms.antigrieflib.AbstractAntiGriefCompatibility;
import net.william278.huskclaims.api.BukkitHuskClaimsAPI;
import net.william278.huskclaims.libraries.cloplib.operation.OperationType;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class HuskClaimsCompatibility extends AbstractAntiGriefCompatibility {

    private BukkitHuskClaimsAPI api;

    public HuskClaimsCompatibility(Plugin plugin) {
        super(plugin);
    }

    @Override
    public void init() {
        api = BukkitHuskClaimsAPI.getInstance();
    }

    @Override
    public boolean canPlace(Player player, Location location) {
        return api.isOperationAllowed(api.getOnlineUser(player), OperationType.BLOCK_PLACE, api.getPosition(location));
    }

    @Override
    public boolean canBreak(Player player, Location location) {
        return api.isOperationAllowed(api.getOnlineUser(player), OperationType.BLOCK_BREAK, api.getPosition(location));
    }

    @Override
    public boolean canInteract(Player player, Location location) {
        return api.isOperationAllowed(api.getOnlineUser(player), OperationType.BLOCK_INTERACT, api.getPosition(location));
    }

    @Override
    public boolean canInteractEntity(Player player, Entity entity) {
        return api.isOperationAllowed(api.getOnlineUser(player), OperationType.ENTITY_INTERACT, api.getPosition(entity.getLocation()));
    }

    @Override
    public boolean canDamage(Player player, Entity entity) {
        return api.isOperationAllowed(api.getOnlineUser(player), OperationType.PLAYER_DAMAGE_ENTITY, api.getPosition(entity.getLocation()));
    }
}
