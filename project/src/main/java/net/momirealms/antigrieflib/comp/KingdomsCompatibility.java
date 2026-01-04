package net.momirealms.antigrieflib.comp;

import net.momirealms.antigrieflib.AbstractMemberAntiGriefCompatibility;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.kingdoms.constants.land.Land;
import org.kingdoms.constants.player.KingdomPlayer;

import java.util.Optional;

public class KingdomsCompatibility extends AbstractMemberAntiGriefCompatibility {

    public KingdomsCompatibility(Plugin plugin) {
        super(plugin);
    }

    @Override
    public void init() {
    }

    @Override
    public boolean isMemberAt(Player player, Location location) {
        Land land = Land.getLand(location);
        if (land == null || !land.isClaimed())
            return true;
        return Optional.ofNullable(land.getKingdom())
                .map(kingdom -> KingdomPlayer.getKingdomPlayer(player).getKingdom() == kingdom)
                .orElse(true);
    }
}
