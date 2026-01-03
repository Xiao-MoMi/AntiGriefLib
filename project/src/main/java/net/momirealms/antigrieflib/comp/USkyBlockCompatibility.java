package net.momirealms.antigrieflib.comp;

import net.momirealms.antigrieflib.AbstractAntiGriefCompatibility;
import net.momirealms.antigrieflib.Flag;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import us.talabrek.ultimateskyblock.api.uSkyBlockAPI;

import java.util.Optional;

public class USkyBlockCompatibility extends AbstractAntiGriefCompatibility {

    private uSkyBlockAPI api;

    public USkyBlockCompatibility(Plugin plugin) {
        super(plugin);
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void init() {
        api = (uSkyBlockAPI) Bukkit.getPluginManager().getPlugin("uSkyBlock");
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
        return Optional.ofNullable(api.getIslandInfo(location))
                .map(islandInfo -> islandInfo.getMembers().contains(player.getName()))
                .orElse(true);
    }
}
