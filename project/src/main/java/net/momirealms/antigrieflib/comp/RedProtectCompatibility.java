package net.momirealms.antigrieflib.comp;

import br.net.fabiozumbi12.RedProtect.Bukkit.API.RedProtectAPI;
import br.net.fabiozumbi12.RedProtect.Bukkit.RedProtect;
import net.momirealms.antigrieflib.AbstractAntiGriefCompatibility;
import net.momirealms.antigrieflib.Flag;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Optional;

public class RedProtectCompatibility extends AbstractAntiGriefCompatibility {

    private RedProtectAPI api;

    public RedProtectCompatibility(Plugin plugin) {
        super(plugin);
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void init() {
        api = RedProtect.get().getAPI();
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
        return Optional.ofNullable(api.getRegion(location))
                .map(region -> region.canBuild(player))
                .orElse(true);
    }

    private boolean canBreak(Player player, Location location) {
        return Optional.ofNullable(api.getRegion(location))
                .map(region -> region.canBuild(player))
                .orElse(true);
    }

    private boolean canInteract(Player player, Location location) {
        return Optional.ofNullable(api.getRegion(location))
                .map(region -> region.canBuild(player))
                .orElse(true);
    }

    private boolean canInteractEntity(Player player, Entity entity) {
        return Optional.ofNullable(api.getRegion(entity.getLocation()))
                .map(region -> region.canBuild(player))
                .orElse(true);
    }

    private boolean canDamageEntity(Player player, Entity entity) {
        return Optional.ofNullable(api.getRegion(entity.getLocation()))
                .map(region -> {
                    if (entity instanceof Player another) {
                        return region.canPVP(player, another);
                    } else {
                        return region.canBuild(player);
                    }
                })
                .orElse(true);
    }

    private boolean canOpenContainer(Player player, Location location) {
        return Optional.ofNullable(api.getRegion(location))
                .map(region -> region.canChest(player))
                .orElse(true);
    }

    private boolean canOpenDoor(Player player, Location location) {
        return Optional.ofNullable(api.getRegion(location))
                .map(region -> region.canDoor(player))
                .orElse(true);
    }

    private boolean canUseButton(Player player, Location location) {
        return Optional.ofNullable(api.getRegion(location))
                .map(region -> region.canButton(player))
                .orElse(true);
    }

    private boolean canUsePressurePlate(Player player, Location location) {
        return Optional.ofNullable(api.getRegion(location))
                .map(region -> region.canPressPlate(player))
                .orElse(true);
    }
}
