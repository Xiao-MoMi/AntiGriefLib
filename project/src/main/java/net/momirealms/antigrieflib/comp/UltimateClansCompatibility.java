package net.momirealms.antigrieflib.comp;

import me.ulrich.clans.Clans;
import me.ulrich.clans.interfaces.ClaimImplement;
import net.momirealms.antigrieflib.AbstractAntiGriefCompatibility;
import net.momirealms.antigrieflib.Flag;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Map;

public class UltimateClansCompatibility extends AbstractAntiGriefCompatibility {

    private Clans clans;

    public UltimateClansCompatibility(Plugin plugin) {
        super(plugin);
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void init() {
        this.clans = (Clans) Bukkit.getPluginManager().getPlugin("UltimateClans");
        registerFlagTester(Flag.PLACE, this::checkClaimedMember);
        registerFlagTester(Flag.BREAK, this::checkClaimedMember);
        registerFlagTester(Flag.INTERACT, this::checkClaimedMember);
        registerFlagTester(Flag.INTERACT_ENTITY, this::checkClaimedMember);
        registerFlagTester(Flag.DAMAGE_ENTITY, this::checkClaimedMember);
        registerFlagTester(Flag.OPEN_CONTAINER, this::checkClaimedMember);
        registerFlagTester(Flag.OPEN_DOOR, this::checkClaimedMember);
        registerFlagTester(Flag.USE_BUTTON, this::checkClaimedMember);
        registerFlagTester(Flag.USE_PRESSURE_PLATE, this::checkClaimedMember);
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
