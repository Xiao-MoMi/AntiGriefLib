package net.momirealms.antigrieflib.comp;

import net.momirealms.antigrieflib.AbstractMemberAntiGriefCompatibility;
import net.momirealms.antigrieflib.Flag;
import net.william278.husktowns.api.BukkitHuskTownsAPI;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class HuskTownsCompatibility extends AbstractMemberAntiGriefCompatibility {

    private BukkitHuskTownsAPI api;

    public HuskTownsCompatibility(Plugin plugin) {
        super(plugin);
    }

    @Override
    public void init() {
        api = BukkitHuskTownsAPI.getInstance();
    }

    @Override
    public <T> boolean test(Player player, @NotNull Flag<T> flag, T value) {
        if (value instanceof Location location) {
            return isTownMember(player, location);
        } else if (value instanceof Entity entity) {
            return isTownMember(player, entity);
        } else {
            return false;
        }
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
