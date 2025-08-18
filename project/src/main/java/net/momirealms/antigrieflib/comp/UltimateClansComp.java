package net.momirealms.antigrieflib.comp;

import me.ulrich.clans.Clans;
import me.ulrich.clans.interfaces.ClaimImplement;
import net.momirealms.antigrieflib.AbstractComp;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;

public class UltimateClansComp extends AbstractComp {

    private Clans clans;

    public UltimateClansComp(JavaPlugin plugin) {
        super(plugin, "UltimateClans");
    }

    @Override
    public void init() {
        this.clans = (Clans) Bukkit.getPluginManager().getPlugin("UltimateClans");
    }

    @Override
    public boolean canPlace(Player player, Location location) {
        return checkClaimedMember(player, location);
    }

    @Override
    public boolean canBreak(Player player, Location location) {
        return checkClaimedMember(player, location);
    }

    @Override
    public boolean canInteract(Player player, Location location) {
        return checkClaimedMember(player, location);
    }

    @Override
    public boolean canInteractEntity(Player player, Entity entity) {
        return checkClaimedMember(player, entity.getLocation());
    }

    @Override
    public boolean canDamage(Player player, Entity entity) {
        return checkClaimedMember(player, entity.getLocation());
    }

    private boolean checkClaimedMember(Player player, Location location) {
        for (Map.Entry<String, ClaimImplement> entry : this.clans.getClaimAPI().findClaimedLocationImplement(location)) {
            ClaimImplement implement = entry.getValue();
            if (implement.canDestroyClaimLocation(player, location)) {
                continue;
            }
            if (!implement.isOwnerClaimLocation(player, location) && !implement.isMemberClaimLocation(player, location)) {
                return false;
            }
        }
        return true;
    }
}
