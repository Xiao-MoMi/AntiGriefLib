package net.momirealms.antigrieflib.comp;

import biz.princeps.landlord.api.ILandLord;
import net.momirealms.antigrieflib.AbstractComp;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Optional;

public class LandlordComp extends AbstractComp {

    private ILandLord landLord;

    public LandlordComp(JavaPlugin plugin) {
        super(plugin, "Landlord");
    }

    @Override
    public void init() {
        landLord = (ILandLord) Bukkit.getPluginManager().getPlugin("Landlord");
    }

    @Override
    public boolean canPlace(Player player, Location location) {
        return landlordMemberCheck(player, location);
    }

    @Override
    public boolean canBreak(Player player, Location location) {
        return landlordMemberCheck(player, location);
    }

    @Override
    public boolean canInteract(Player player, Location location) {
        return landlordMemberCheck(player, location);
    }

    @Override
    public boolean canInteractEntity(Player player, Entity entity) {
        return landlordMemberCheck(player, entity.getLocation());
    }

    @Override
    public boolean canDamage(Player player, Entity entity) {
        return landlordMemberCheck(player, entity.getLocation()) && (!(entity instanceof Player) || entity.getWorld().getPVP());
    }

    private boolean landlordMemberCheck(final Player player, final Location location) {
        return Optional.ofNullable(landLord.getWGManager().getRegion(location))
                .map(region -> region.isOwner(player.getUniqueId()) || region.isFriend(player.getUniqueId()))
                .orElse(false);
    }
}
