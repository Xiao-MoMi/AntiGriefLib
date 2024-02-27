package net.momirealms.antigrieflib.comp;

import com.iridium.iridiumcore.Item;
import com.iridium.iridiumcore.dependencies.xseries.XMaterial;
import com.iridium.iridiumskyblock.api.IridiumSkyblockAPI;
import com.iridium.iridiumteams.Permission;
import com.iridium.iridiumteams.PermissionType;
import com.iridium.iridiumteams.Rank;
import net.momirealms.antigrieflib.AbstractComp;
import net.momirealms.antigrieflib.CustomFlag;
import net.momirealms.antigrieflib.Flag;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Optional;

public class IridiumSkyblockComp extends AbstractComp implements CustomFlag {

    private IridiumSkyblockAPI api;
    private Permission PLACE_PERM;
    private String PLACE_PERM_KEY;
    private Permission BREAK_PERM;
    private String BREAK_PERM_KEY;
    private Permission INTERACT_PERM;
    private String INTERACT_PERM_KEY;

    public IridiumSkyblockComp(JavaPlugin plugin) {
        super(plugin, "IridiumSkyblock");
    }

    @Override
    public void init() {
        api = IridiumSkyblockAPI.getInstance();
    }

    @Override
    public boolean canPlace(Player player, Location location) {
        return Optional.ofNullable(PLACE_PERM)
                .map(permission -> api.getIslandViaLocation(location)
                        .map(island -> api.getIslandPermission(
                                island,
                                api.getUser(player),
                                permission,
                                PLACE_PERM_KEY)
                        )
                        .orElse(true)
                ).orElse(api.getIslandViaLocation(location)
                        .map(island -> api.getIslandPermission(
                                island,
                                api.getUser(player),
                                PermissionType.BLOCK_PLACE)
                        )
                        .orElse(true)
                );
    }

    @Override
    public boolean canBreak(Player player, Location location) {
        return Optional.ofNullable(BREAK_PERM)
                .map(permission -> api.getIslandViaLocation(location)
                        .map(island -> api.getIslandPermission(
                                island,
                                api.getUser(player),
                                permission,
                                BREAK_PERM_KEY)
                        )
                        .orElse(true)
                ).orElse(api.getIslandViaLocation(location)
                        .map(island -> api.getIslandPermission(
                                island,
                                api.getUser(player),
                                PermissionType.BLOCK_BREAK)
                        )
                        .orElse(true)
                );
    }

    @Override
    public boolean canInteract(Player player, Location location) {
        return Optional.ofNullable(INTERACT_PERM)
                .map(permission -> api.getIslandViaLocation(location)
                        .map(island -> api.getIslandPermission(
                                island,
                                api.getUser(player),
                                permission,
                                INTERACT_PERM_KEY)
                        )
                        .orElse(true)
                ).orElse(api.getIslandViaLocation(location)
                        .map(island -> api.getIslandPermission(
                                island,
                                api.getUser(player),
                                PermissionType.OPEN_CONTAINERS)
                        )
                        .orElse(true)
                );
    }

    @Override
    @SuppressWarnings("DuplicatedCode")
    public void registerOnEnable(Flag customPlaceFlag, Flag customBreakFlag, Flag customInteractFlag) {
        if (       customPlaceFlag != null
                && customPlaceFlag.getDisplayName() != null
                && customPlaceFlag.getDescription() != null
                && customPlaceFlag.getDisplayItem() != null
        ) {
            PLACE_PERM = new Permission(
                    new Item(
                            XMaterial.matchXMaterial(customPlaceFlag.getDisplayItem().getType()),
                            1,
                            1,
                            customPlaceFlag.getDisplayName(),
                            customPlaceFlag.getDescription()
                    ), 1, Rank.OWNER.getId()
            );
            api.addPermission(PLACE_PERM, customPlaceFlag.getName());
            PLACE_PERM_KEY = customPlaceFlag.getName();
        }
        if (       customBreakFlag != null
                && customBreakFlag.getDisplayName() != null
                && customBreakFlag.getDescription() != null
                && customBreakFlag.getDisplayItem() != null
        ) {
            BREAK_PERM = new Permission(
                    new Item(
                            XMaterial.matchXMaterial(customBreakFlag.getDisplayItem().getType()),
                            1,
                            1,
                            customBreakFlag.getDisplayName(),
                            customBreakFlag.getDescription()
                    ), 1, Rank.OWNER.getId()
            );
            api.addPermission(BREAK_PERM, customBreakFlag.getName());
            BREAK_PERM_KEY = customBreakFlag.getName();
        }
        if (       customInteractFlag != null
                && customInteractFlag.getDisplayName() != null
                && customInteractFlag.getDescription() != null
                && customInteractFlag.getDisplayItem() != null
        ) {
            INTERACT_PERM = new Permission(
                    new Item(
                            XMaterial.matchXMaterial(customInteractFlag.getDisplayItem().getType()),
                            1,
                            1,
                            customInteractFlag.getDisplayName(),
                            customInteractFlag.getDescription()
                    ), 1, Rank.OWNER.getId()
            );
            api.addPermission(INTERACT_PERM, customInteractFlag.getName());
            INTERACT_PERM_KEY = customInteractFlag.getName();
        }
    }
}
