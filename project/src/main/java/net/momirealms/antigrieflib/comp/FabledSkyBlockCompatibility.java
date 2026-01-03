package net.momirealms.antigrieflib.comp;

import com.craftaro.skyblock.api.SkyBlockAPI;
import com.craftaro.skyblock.api.island.IslandRole;
import net.momirealms.antigrieflib.AbstractAntiGriefCompatibility;
import net.momirealms.antigrieflib.Flag;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Optional;

public class FabledSkyBlockCompatibility extends AbstractAntiGriefCompatibility {

    public FabledSkyBlockCompatibility(Plugin plugin) {
        super(plugin);
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void init() {
        registerFlagTester(Flag.PLACE, this::isIslandMember);
        registerFlagTester(Flag.BREAK, this::isIslandMember);
        registerFlagTester(Flag.INTERACT, this::isIslandMember);
        registerFlagTester(Flag.INTERACT_ENTITY, this::isIslandMember);
        registerFlagTester(Flag.DAMAGE_ENTITY, this::isIslandMember);
        registerFlagTester(Flag.OPEN_CONTAINER, this::isIslandMember);
        registerFlagTester(Flag.OPEN_DOOR, this::isIslandMember);
        registerFlagTester(Flag.USE_BUTTON, this::isIslandMember);
        registerFlagTester(Flag.USE_PRESSURE_PLATE, this::isIslandMember);
    }

    private boolean isIslandMember(Player player, Entity entity) {
        return isIslandMember(player, entity.getLocation());
    }

    private boolean isIslandMember(Player player, Location location) {
        return Optional.ofNullable(SkyBlockAPI.getIslandManager().getIslandAtLocation(location))
                .map(island -> {
                     if (island.isCoopPlayer(player))
                         return true;
                     return Optional.ofNullable(island.getRole(player))
                            .map(islandRole -> islandRole != IslandRole.VISITOR)
                            .orElse(false);
                     }
                )
                .orElse(true);
    }
}