package net.momirealms.antigrieflib.comp;

import com.griefdefender.api.GriefDefender;
import com.griefdefender.api.User;
import com.griefdefender.api.claim.TrustTypes;
import net.momirealms.antigrieflib.AbstractComp;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class GriefDefenderComp extends AbstractComp {

    public GriefDefenderComp(JavaPlugin plugin) {
        super(plugin, "GriefDefender");
    }

    @Override
    public void init() {
    }

    @Override
    public boolean canPlace(Player player, Location location) {
        User user = GriefDefender.getCore().getUser(player.getUniqueId());
        if (user == null) return false;
        return user.canPlace(player.getInventory().getItemInMainHand(), location);
    }

    @Override
    public boolean canBreak(Player player, Location location) {
        User user = GriefDefender.getCore().getUser(player.getUniqueId());
        if (user == null) return false;
        return user.canBreak(location);
    }

    @Override
    public boolean canInteract(Player player, Location location) {
        User user = GriefDefender.getCore().getUser(player.getUniqueId());
        if (user == null) return false;
        return user.canUseBlock(location, TrustTypes.CONTAINER, false, false);
    }
}
