package net.momirealms.antigrieflib.comp;

import com.massivecraft.factions.FactionsPlugin;
import com.massivecraft.factions.listeners.FactionsBlockListener;
import com.massivecraft.factions.listeners.FactionsEntityListener;
import com.massivecraft.factions.perms.PermissibleActions;
import net.momirealms.antigrieflib.AbstractAntiGriefCompatibility;
import net.momirealms.antigrieflib.Flag;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class FactionsUUIDCompatibility extends AbstractAntiGriefCompatibility {

    private FactionsPlugin plugin;

    public FactionsUUIDCompatibility(Plugin plugin) {
        super(plugin);
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void init() {
        plugin = FactionsPlugin.getInstance();
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
        if (!plugin.worldUtil().isEnabled(location.getWorld())) return true;
        return FactionsBlockListener.playerCanBuildDestroyBlock(player, location, PermissibleActions.BUILD, true);
    }

    private boolean canBreak(Player player, Location location) {
        if (!plugin.worldUtil().isEnabled(location.getWorld())) return true;
        return FactionsBlockListener.playerCanBuildDestroyBlock(player, location, PermissibleActions.DESTROY, true);
    }

    private boolean canInteract(Player player, Location location) {
        if (!plugin.worldUtil().isEnabled(location.getWorld())) return true;
        return FactionsBlockListener.playerCanBuildDestroyBlock(player, location, PermissibleActions.CONTAINER, true);
    }

    private boolean canInteractEntity(Player player, Entity entity) {
        if (!plugin.worldUtil().isEnabled(entity.getWorld())) return true;
        return FactionsBlockListener.playerCanBuildDestroyBlock(player, entity.getLocation(), PermissibleActions.CONTAINER, true);
    }

    private boolean canDamageEntity(Player player, Entity entity) {
        if (!plugin.worldUtil().isEnabled(entity.getWorld())) return true;
        return FactionsEntityListener.canDamage(player, entity, true);
    }

    private boolean canOpenContainer(Player player, Location location) {
        if (!plugin.worldUtil().isEnabled(location.getWorld())) return true;
        return FactionsBlockListener.playerCanBuildDestroyBlock(player, location, PermissibleActions.CONTAINER, true);
    }

    private boolean canOpenDoor(Player player, Location location) {
        if (!plugin.worldUtil().isEnabled(location.getWorld())) return true;
        return FactionsBlockListener.playerCanBuildDestroyBlock(player, location, PermissibleActions.DOOR, true);
    }

    private boolean canUseButton(Player player, Location location) {
        if (!plugin.worldUtil().isEnabled(location.getWorld())) return true;
        return FactionsBlockListener.playerCanBuildDestroyBlock(player, location, PermissibleActions.BUTTON, true);
    }

    private boolean canUsePressurePlate(Player player, Location location) {
        if (!plugin.worldUtil().isEnabled(location.getWorld())) return true;
        return FactionsBlockListener.playerCanBuildDestroyBlock(player, location, PermissibleActions.PLATE, true);
    }
}
