package net.momirealms.antigrieflib.comp;

import me.ulrich.clans.Clans;
import me.ulrich.clans.interfaces.ClaimImplement;
import net.momirealms.antigrieflib.AbstractMemberAntiGriefCompatibility;
import net.momirealms.antigrieflib.Flag;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class UltimateClansCompatibility extends AbstractMemberAntiGriefCompatibility {

    private Clans clans;

    public UltimateClansCompatibility(Plugin plugin) {
        super(plugin);
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void init() {
        this.clans = (Clans) Bukkit.getPluginManager().getPlugin("UltimateClans");
    }

    @Override
    public <T> boolean test(Player player, @NotNull Flag<T> flag, T value) {
        if (value instanceof Location location) {
            return checkClaimedMember(player, location);
        } else if (value instanceof Entity entity) {
            return checkClaimedMember(player, entity);
        } else {
            return false;
        }
    }
    private boolean checkClaimedMember(Player player, Entity entity) {
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
