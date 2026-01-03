package net.momirealms.antigrieflib.comp;

import net.momirealms.antigrieflib.AbstractMemberAntiGriefCompatibility;
import net.momirealms.antigrieflib.Flag;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
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
    public <T> boolean test(Player player, @NotNull Flag<T> flag, T value) {
        if (value instanceof Location location) {
            return kingdomsMemberCheck(player, location);
        } else if (value instanceof Entity entity) {
            return kingdomsMemberCheck(player, entity);
        } else {
            return false;
        }
    }

    private boolean kingdomsMemberCheck(Player player, Entity entity) {
        return kingdomsMemberCheck(player, entity.getLocation());
    }

    private boolean kingdomsMemberCheck(Player player, Location location) {
        Land land = Land.getLand(location);
        if (land == null || !land.isClaimed())
            return true;
        return Optional.ofNullable(land.getKingdom())
                .map(kingdom -> KingdomPlayer.getKingdomPlayer(player).getKingdom() == kingdom)
                .orElse(true);
    }
}
