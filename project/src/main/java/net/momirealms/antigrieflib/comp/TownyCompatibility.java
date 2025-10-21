package net.momirealms.antigrieflib.comp;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.object.TownyPermission;
import com.palmergames.bukkit.towny.utils.PlayerCacheUtil;
import net.momirealms.antigrieflib.AbstractAntiGriefCompatibility;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class TownyCompatibility extends AbstractAntiGriefCompatibility {

    public TownyCompatibility(Plugin plugin) {
        super(plugin);
    }

    @Override
    public void init() {
    }

    @Override
    public boolean canPlace(Player player, Location location) {
        return PlayerCacheUtil.getCachePermission(player, location, location.getBlock().getType(), TownyPermission.ActionType.BUILD);
    }

    @Override
    public boolean canBreak(Player player, Location location) {
        return PlayerCacheUtil.getCachePermission(player, location, location.getBlock().getType(), TownyPermission.ActionType.DESTROY);
    }

    @Override
    public boolean canInteract(Player player, Location location) {
        return PlayerCacheUtil.getCachePermission(player, location, location.getBlock().getType(), TownyPermission.ActionType.ITEM_USE);
    }

    @Override
    public boolean canInteractEntity(Player player, Entity entity) {
        return PlayerCacheUtil.getCachePermission(player, entity.getLocation(), entity.getLocation().getBlock().getType(), TownyPermission.ActionType.ITEM_USE);
    }

    @Override
    public boolean canDamage(Player player, Entity entity) {
        return entity instanceof Player ? TownyAPI.getInstance().isPVP(entity.getLocation()) : PlayerCacheUtil.getCachePermission(player, entity.getLocation(), entity.getLocation().getBlock().getType(), TownyPermission.ActionType.ITEM_USE);
    }

    @Override
    public boolean canOpenContainer(Player player, Location location) {
        return PlayerCacheUtil.getCachePermission(player, location, location.getBlock().getType(), TownyPermission.ActionType.ITEM_USE);
    }
}
