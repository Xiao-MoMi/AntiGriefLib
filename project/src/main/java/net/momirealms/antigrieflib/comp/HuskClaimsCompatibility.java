package net.momirealms.antigrieflib.comp;

import net.momirealms.antigrieflib.AbstractAntiGriefCompatibility;
import net.momirealms.antigrieflib.Flag;
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

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void init() {
        api = BukkitHuskClaimsAPI.getInstance();
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
        return api.isOperationAllowed(api.getOnlineUser(player), OperationType.BLOCK_PLACE, api.getPosition(location));
    }

    private boolean canBreak(Player player, Location location) {
        return api.isOperationAllowed(api.getOnlineUser(player), OperationType.BLOCK_BREAK, api.getPosition(location));
    }

    private boolean canInteract(Player player, Location location) {
        return api.isOperationAllowed(api.getOnlineUser(player), OperationType.BLOCK_INTERACT, api.getPosition(location));
    }

    private boolean canInteractEntity(Player player, Entity entity) {
        return api.isOperationAllowed(api.getOnlineUser(player), OperationType.ENTITY_INTERACT, api.getPosition(entity.getLocation()));
    }

    private boolean canDamageEntity(Player player, Entity entity) {
        return api.isOperationAllowed(api.getOnlineUser(player), OperationType.PLAYER_DAMAGE_ENTITY, api.getPosition(entity.getLocation()));
    }

    private boolean canOpenContainer(Player player, Location location) {
        return api.isOperationAllowed(api.getOnlineUser(player), OperationType.CONTAINER_OPEN, api.getPosition(location));
    }

    private boolean canOpenDoor(Player player, Location location) {
        return api.isOperationAllowed(api.getOnlineUser(player), OperationType.BLOCK_INTERACT, api.getPosition(location));
    }

    private boolean canUseButton(Player player, Location location) {
        return api.isOperationAllowed(api.getOnlineUser(player), OperationType.BLOCK_INTERACT, api.getPosition(location));
    }

    private boolean canUsePressurePlate(Player player, Location location) {
        return api.isOperationAllowed(api.getOnlineUser(player), OperationType.BLOCK_INTERACT, api.getPosition(location));
    }
}
