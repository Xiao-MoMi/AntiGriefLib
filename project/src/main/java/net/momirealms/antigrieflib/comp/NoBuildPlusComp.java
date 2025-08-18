package net.momirealms.antigrieflib.comp;

import net.momirealms.antigrieflib.AbstractComp;
import net.momirealms.antigrieflib.util.ReflectionUtils;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import p1xel.nobuildplus.API.NBPAPI;
import p1xel.nobuildplus.Flags;
import p1xel.nobuildplus.NoBuildPlus;

public class NoBuildPlusComp extends AbstractComp {
    private NBPAPI nbpAPI;

    public NoBuildPlusComp(JavaPlugin plugin) {
        super(plugin, "NoBuildPlus");
    }

    @Override
    public boolean checkClazz() {
        return ReflectionUtils.classExists("p1xel.nobuildplus.NoBuildPlus");
    }

    @Override
    public void init() {
        nbpAPI = NoBuildPlus.getInstance().getAPI();
    }

    @Override
    public boolean canPlace(Player player, Location location) {
        return nbpAPI.canExecute(player.getWorld().getName(), Flags.build);
    }

    @Override
    public boolean canBreak(Player player, Location location) {
        return nbpAPI.canExecute(player.getWorld().getName(), Flags.destroy);
    }

    @Override
    public boolean canInteract(Player player, Location location) {
        return nbpAPI.canExecute(player.getWorld().getName(), Flags.use);
    }

    @Override
    public boolean canInteractEntity(Player player, Entity entity) {
        String world = player.getWorld().getName();
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
        return nbpAPI.canExecute(
                player.getWorld().getName(),
                entity instanceof Player ? Flags.pvp : Flags.mob_damage
        );
    }
}
