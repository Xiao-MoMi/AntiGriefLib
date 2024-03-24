package net.momirealms.antigrieflib.comp;

import net.momirealms.antigrieflib.AbstractComp;
import net.william278.husktowns.api.BukkitHuskTownsAPI;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class HuskTownsComp extends AbstractComp {

    private BukkitHuskTownsAPI api;

    public HuskTownsComp(JavaPlugin plugin) {
        super(plugin, "HuskTowns");
    }

    @Override
    public void init() {
        api = BukkitHuskTownsAPI.getInstance();
    }

    @Override
    public boolean canPlace(Player player, Location location) {
        return isTownMember(player, location);
    }

    @Override
    public boolean canBreak(Player player, Location location) {
        return isTownMember(player, location);
    }

    @Override
    public boolean canInteract(Player player, Location location) {
        return isTownMember(player, location);
    }

    @Override
    public boolean canInteractEntity(Player player, Entity entity) {
        return isTownMember(player, entity.getLocation());
    }

    @Override
    public boolean canDamage(Player player, Entity entity) {
        return isTownMember(player, entity.getLocation()) && (!(entity instanceof Player) || entity.getWorld().getPVP());
    }

    public boolean isTownMember(Player player, Location location) {
        return api.getClaimAt(api.getPosition(location))
                .map(townClaim -> townClaim.town().getMembers().containsKey(player.getUniqueId()))
                .orElse(true);
    }
}
