package net.momirealms.antigrieflib.comp;

import net.momirealms.antigrieflib.AbstractAntiGriefCompatibility;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import p1xel.nobuildplus.API.NBPAPI;
import p1xel.nobuildplus.Flags;
import p1xel.nobuildplus.NoBuildPlus;

public class NoBuildPlusCompatibility extends AbstractAntiGriefCompatibility {
    private NBPAPI nbpAPI;

    public NoBuildPlusCompatibility(Plugin plugin) {
        super(plugin);
    }

    @Override
    public void init() {
        nbpAPI = NoBuildPlus.getInstance().getAPI();
    }

    @Override
    public boolean canPlace(Player player, Location location) {
        String world = player.getWorld().getName();
        if (!nbpAPI.isWorldEnabled(world)) return true;
        return nbpAPI.canExecute(world, Flags.build);
    }

    @Override
    public boolean canBreak(Player player, Location location) {
        String world = player.getWorld().getName();
        if (!nbpAPI.isWorldEnabled(world)) return true;
        return nbpAPI.canExecute(world, Flags.destroy);
    }

    @Override
    public boolean canInteract(Player player, Location location) {
        String world = player.getWorld().getName();
        if (!nbpAPI.isWorldEnabled(world)) return true;
        return nbpAPI.canExecute(world, Flags.use);
    }

    @Override
    public boolean canInteractEntity(Player player, Entity entity) {
        String world = player.getWorld().getName();
        if (!nbpAPI.isWorldEnabled(world)) return true;
        return switch (entity.getType()) {
            case VILLAGER -> nbpAPI.canExecute(world, Flags.villager);
            case HORSE, DONKEY, MULE, SKELETON_HORSE, ZOMBIE_HORSE, MINECART, MINECART_CHEST, MINECART_FURNACE,
                 MINECART_HOPPER, MINECART_TNT -> nbpAPI.canExecute(world, Flags.ride);
            case ITEM_FRAME, GLOW_ITEM_FRAME -> nbpAPI.canExecute(world, Flags.frame);
            case ARMOR_STAND -> nbpAPI.canExecute(world, Flags.armorstand);
            case PAINTING -> nbpAPI.canExecute(world, Flags.painting);
            case FISHING_HOOK -> nbpAPI.canExecute(world, Flags.hook);
            default -> true;
        };
    }

    @Override
    public boolean canDamage(Player player, Entity entity) {
        String world = player.getWorld().getName();
        if (!nbpAPI.isWorldEnabled(world)) return true;
        return nbpAPI.canExecute(
                world,
                entity instanceof Player ? Flags.pvp : Flags.mob_damage
        );
    }
}
