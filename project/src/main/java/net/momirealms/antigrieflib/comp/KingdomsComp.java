package net.momirealms.antigrieflib.comp;

import net.momirealms.antigrieflib.AbstractComp;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.kingdoms.constants.group.Kingdom;
import org.kingdoms.constants.land.Land;
import org.kingdoms.constants.player.KingdomPlayer;

public class KingdomsComp extends AbstractComp {

    public KingdomsComp(JavaPlugin plugin) {
        super(plugin, "Kingdoms");
    }

    @Override
    public void init() {
    }

    @Override
    public boolean canPlace(Player player, Location location) {
        return kingdomsMemberCheck(player, location);
    }

    @Override
    public boolean canBreak(Player player, Location location) {
        return kingdomsMemberCheck(player, location);
    }

    @Override
    public boolean canInteract(Player player, Location location) {
        return kingdomsMemberCheck(player, location);
    }

    private boolean kingdomsMemberCheck(Player player, Location location) {
        Land land = Land.getLand(location);
        if (land == null || !land.isClaimed())
            return true;
        KingdomPlayer kp = KingdomPlayer.getKingdomPlayer(player);
        Kingdom currentKingdom = land.getKingdom();
        return kp.getKingdom() == currentKingdom;
    }
}
