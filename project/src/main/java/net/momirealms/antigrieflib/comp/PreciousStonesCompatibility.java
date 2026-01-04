package net.momirealms.antigrieflib.comp;

import net.momirealms.antigrieflib.AbstractAntiGriefCompatibility;
import net.momirealms.antigrieflib.Flag;
import net.sacredlabyrinth.Phaed.PreciousStones.api.Api;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class PreciousStonesCompatibility extends AbstractAntiGriefCompatibility {

    private Api api;

    public PreciousStonesCompatibility(Plugin plugin) {
        super(plugin);
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void init() {
        this.api = new Api();
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
        return api.canPlace(player, location);
    }

    private boolean canBreak(Player player, Location location) {
        return api.canBreak(player, location);
    }

    private boolean canInteract(Player player, Location location) {
        return api.canPlace(player, location);
    }

    private boolean canInteractEntity(Player player, Entity entity) {
        return api.canPlace(player, entity.getLocation());
    }

    private boolean canDamageEntity(Player player, Entity entity) {
        return api.canBreak(player, entity.getLocation());
    }

    private boolean canOpenContainer(Player player, Location location) {
        return api.canPlace(player, location);
    }

    private boolean canOpenDoor(Player player, Location location) {
        return api.canPlace(player, location);
    }

    private boolean canUseButton(Player player, Location location) {
        return api.canPlace(player, location);
    }

    private boolean canUsePressurePlate(Player player, Location location) {
        return api.canPlace(player, location);
    }
}
