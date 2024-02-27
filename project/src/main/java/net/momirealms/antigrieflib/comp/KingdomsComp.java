package net.momirealms.antigrieflib.comp;

import net.momirealms.antigrieflib.AbstractComp;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Method;

public class KingdomsComp extends AbstractComp {

    public KingdomsComp(JavaPlugin plugin) {
        super(plugin, "Kingdoms");
    }

    private Method getLandMethod;
    private Method getKingdomPlayerMethod;
    private Method getKingdomMethod;
    private Method getKingdomPlayerKingdomMethod;
    private Method isClaimedMethod;

    @Override
    public void init() {
        try {
            Class<?> landClass = Class.forName("org.kingdoms.constants.land.Land");
            getLandMethod = landClass.getMethod("getLand", Location.class);
            Class<?> kingdomPlayerClass = Class.forName("org.kingdoms.constants.player.KingdomPlayer");
            getKingdomPlayerMethod = kingdomPlayerClass.getMethod("getKingdomPlayer", OfflinePlayer.class);
            getKingdomMethod = landClass.getMethod("getKingdom");
            getKingdomPlayerKingdomMethod = kingdomPlayerClass.getMethod("getKingdom");
            isClaimedMethod = landClass.getMethod("isClaimed");
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
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
        try {
            Object land = getLandMethod.invoke(null, location);
            if (land == null) return true;
            boolean isClaimed = (boolean) isClaimedMethod.invoke(land);
            if (!isClaimed) return true;
            Object kingdomPlayer = getKingdomPlayerMethod.invoke(null, player);
            Object currentKingdom = getKingdomMethod.invoke(land);
            Object playerKingdom = getKingdomPlayerKingdomMethod.invoke(kingdomPlayer);
            return playerKingdom == currentKingdom;
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
            return false;
        }
    }
}
