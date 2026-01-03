package net.momirealms.antigrieflib.comp;

import net.momirealms.antigrieflib.AbstractAntiGriefCompatibility;
import net.momirealms.antigrieflib.Flag;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.kingdoms.constants.land.Land;
import org.kingdoms.constants.player.KingdomPlayer;

import java.util.Optional;

public class KingdomsCompatibility extends AbstractAntiGriefCompatibility {

    public KingdomsCompatibility(Plugin plugin) {
        super(plugin);
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void init() {
        registerFlagTester(Flag.PLACE, this::kingdomsMemberCheck);
        registerFlagTester(Flag.BREAK, this::kingdomsMemberCheck);
        registerFlagTester(Flag.INTERACT, this::kingdomsMemberCheck);
        registerFlagTester(Flag.INTERACT_ENTITY, this::kingdomsMemberCheck);
        registerFlagTester(Flag.DAMAGE_ENTITY, this::kingdomsMemberCheck);
        registerFlagTester(Flag.OPEN_CONTAINER, this::kingdomsMemberCheck);
        registerFlagTester(Flag.OPEN_DOOR, this::kingdomsMemberCheck);
        registerFlagTester(Flag.USE_BUTTON, this::kingdomsMemberCheck);
        registerFlagTester(Flag.USE_PRESSURE_PLATE, this::kingdomsMemberCheck);
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
