package net.momirealms.antigrieflib.comp;

import net.momirealms.antigrieflib.AbstractMemberAntiGriefCompatibility;
import net.william278.husktowns.api.BukkitHuskTownsAPI;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

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
    public boolean isMemberAt(Player player, Location location) {
        return api.getClaimAt(api.getPosition(location))
                .map(townClaim -> townClaim.town().getMembers().containsKey(player.getUniqueId()))
                .orElse(true);
    }
}
