package net.momirealms.antigrieflib.comp;

import biz.princeps.landlord.api.ILandLord;
import net.momirealms.antigrieflib.AbstractAntiGriefCompatibility;
import net.momirealms.antigrieflib.Flag;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Optional;

public class LandlordCompatibility extends AbstractAntiGriefCompatibility {

    private ILandLord landLord;

    public LandlordCompatibility(Plugin plugin) {
        super(plugin);
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void init() {
        landLord = (ILandLord) Bukkit.getPluginManager().getPlugin("Landlord");
        registerFlagTester(Flag.PLACE, this::landlordMemberCheck);
        registerFlagTester(Flag.BREAK, this::landlordMemberCheck);
        registerFlagTester(Flag.INTERACT, this::landlordMemberCheck);
        registerFlagTester(Flag.INTERACT_ENTITY, this::landlordMemberCheck);
        registerFlagTester(Flag.DAMAGE_ENTITY, this::landlordMemberCheck);
        registerFlagTester(Flag.OPEN_CONTAINER, this::landlordMemberCheck);
        registerFlagTester(Flag.OPEN_DOOR, this::landlordMemberCheck);
        registerFlagTester(Flag.USE_BUTTON, this::landlordMemberCheck);
        registerFlagTester(Flag.USE_PRESSURE_PLATE, this::landlordMemberCheck);
    }

    private boolean landlordMemberCheck(Player player, Entity entity) {
        return landlordMemberCheck(player, entity.getLocation());
    }

    private boolean landlordMemberCheck(final Player player, final Location location) {
        return Optional.ofNullable(landLord.getWGManager().getRegion(location))
                .map(region -> region.isOwner(player.getUniqueId()) || region.isFriend(player.getUniqueId()))
                .orElse(false);
    }
}
