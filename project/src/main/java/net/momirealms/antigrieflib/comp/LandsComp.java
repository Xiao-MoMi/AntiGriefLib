package net.momirealms.antigrieflib.comp;

import me.angeschossen.lands.api.LandsIntegration;
import me.angeschossen.lands.api.flags.enums.FlagTarget;
import me.angeschossen.lands.api.flags.enums.RoleFlagCategory;
import me.angeschossen.lands.api.flags.type.Flags;
import me.angeschossen.lands.api.flags.type.RoleFlag;
import net.momirealms.antigrieflib.AbstractComp;
import net.momirealms.antigrieflib.CustomFlag;
import net.momirealms.antigrieflib.Flag;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Optional;

public class LandsComp extends AbstractComp implements CustomFlag {

    private LandsIntegration api;
    private RoleFlag PLACE_FLAG;
    private RoleFlag BREAK_FLAG;
    private RoleFlag INTERACT_FLAG;

    public LandsComp(JavaPlugin plugin) {
        super(plugin, "Lands");
    }

    @Override
    public void init() {
        this.api = LandsIntegration.of(getPlugin());
    }

    @Override
    public boolean canPlace(Player player, Location location) {
        return Optional.ofNullable(api.getWorld(location.getWorld()))
                .map(world ->
                        world.hasRoleFlag(
                            player.getUniqueId(),
                            location,
                            PLACE_FLAG == null ? Flags.BLOCK_PLACE : PLACE_FLAG
                        )
                ).orElse(true);
    }

    @Override
    public boolean canBreak(Player player, Location location) {
        return Optional.ofNullable(api.getWorld(location.getWorld()))
                .map(world ->
                        world.hasRoleFlag(
                                player.getUniqueId(),
                                location,
                                BREAK_FLAG == null ? Flags.BLOCK_BREAK : BREAK_FLAG
                        )
                ).orElse(true);
    }

    @Override
    public boolean canInteract(Player player, Location location) {
        return Optional.ofNullable(api.getWorld(location.getWorld()))
                .map(world ->
                        world.hasRoleFlag(
                                player.getUniqueId(),
                                location,
                                INTERACT_FLAG == null ? Flags.INTERACT_GENERAL : INTERACT_FLAG
                        )
                ).orElse(true);
    }

    @Override
    public void registerOnEnable(Flag customPlaceFlag, Flag customBreakFlag, Flag customInteractFlag) {
        if (customPlaceFlag != null) {
            PLACE_FLAG = RoleFlag.of(api, FlagTarget.PLAYER, RoleFlagCategory.ACTION, customPlaceFlag.getName());
            PLACE_FLAG.setDefaultState(customPlaceFlag.getDefaultValue());
            if (customPlaceFlag.getDisplayItem() != null)
                PLACE_FLAG.setIcon(customPlaceFlag.getDisplayItem());
            if (customPlaceFlag.getDisplayName() != null)
                PLACE_FLAG.setDisplayName(customPlaceFlag.getDisplayName());
            if (customPlaceFlag.getDescription() != null)
                PLACE_FLAG.setDescription(customPlaceFlag.getDescription());
        }
        if (customBreakFlag != null) {
            BREAK_FLAG = RoleFlag.of(api, FlagTarget.PLAYER, RoleFlagCategory.ACTION, customBreakFlag.getName());
            BREAK_FLAG.setDefaultState(customBreakFlag.getDefaultValue());
            if (customBreakFlag.getDisplayItem() != null)
                BREAK_FLAG.setIcon(customBreakFlag.getDisplayItem());
            if (customBreakFlag.getDisplayName() != null)
                BREAK_FLAG.setDisplayName(customBreakFlag.getDisplayName());
            if (customBreakFlag.getDescription() != null)
                BREAK_FLAG.setDescription(customBreakFlag.getDescription());
        }
        if (customInteractFlag != null) {
            INTERACT_FLAG = RoleFlag.of(api, FlagTarget.PLAYER, RoleFlagCategory.ACTION, customInteractFlag.getName());
            INTERACT_FLAG.setDefaultState(customInteractFlag.getDefaultValue());
            if (customInteractFlag.getDisplayItem() != null)
                INTERACT_FLAG.setIcon(customInteractFlag.getDisplayItem());
            if (customInteractFlag.getDisplayName() != null)
                INTERACT_FLAG.setDisplayName(customInteractFlag.getDisplayName());
            if (customInteractFlag.getDescription() != null)
                INTERACT_FLAG.setDescription(customInteractFlag.getDescription());
        }
    }
}
