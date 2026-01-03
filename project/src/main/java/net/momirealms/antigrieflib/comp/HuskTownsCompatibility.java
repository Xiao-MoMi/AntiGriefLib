package net.momirealms.antigrieflib.comp;

import net.momirealms.antigrieflib.AbstractAntiGriefCompatibility;
import net.momirealms.antigrieflib.Flag;
import net.william278.husktowns.api.BukkitHuskTownsAPI;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class HuskTownsCompatibility extends AbstractAntiGriefCompatibility {

    private BukkitHuskTownsAPI api;

    public HuskTownsCompatibility(Plugin plugin) {
        super(plugin);
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void init() {
        api = BukkitHuskTownsAPI.getInstance();
        registerFlagTester(Flag.PLACE, this::isTownMember);
        registerFlagTester(Flag.BREAK, this::isTownMember);
        registerFlagTester(Flag.INTERACT, this::isTownMember);
        registerFlagTester(Flag.INTERACT_ENTITY, this::isTownMember);
        registerFlagTester(Flag.DAMAGE_ENTITY, this::isTownMember);
        registerFlagTester(Flag.OPEN_CONTAINER, this::isTownMember);
        registerFlagTester(Flag.OPEN_DOOR, this::isTownMember);
        registerFlagTester(Flag.USE_BUTTON, this::isTownMember);
        registerFlagTester(Flag.USE_PRESSURE_PLATE, this::isTownMember);
    }

    private boolean isTownMember(Player player, Entity entity) {
        return isTownMember(player, entity.getLocation());
    }

    private boolean isTownMember(Player player, Location location) {
        return api.getClaimAt(api.getPosition(location))
                .map(townClaim -> townClaim.town().getMembers().containsKey(player.getUniqueId()))
                .orElse(true);
    }
}
