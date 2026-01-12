package net.momirealms.antigrieflib.comp;

import net.momirealms.antigrieflib.AbstractAntiGriefCompatibility;
import net.momirealms.antigrieflib.Flag;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import p1xel.nobuildplus.api.NBPAPI;
import p1xel.nobuildplus.Flags;
import p1xel.nobuildplus.NoBuildPlus;

public class NoBuildPlusCompatibility extends AbstractAntiGriefCompatibility {
    private NBPAPI nbpAPI;

    public NoBuildPlusCompatibility(Plugin plugin) {
        super(plugin);
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void init() {
        nbpAPI = NoBuildPlus.getInstance().getAPI();
        registerFlagTester(Flag.PLACE, this::canPlace);
        registerFlagTester(Flag.BREAK, this::canBreak);
        registerFlagTester(Flag.INTERACT, this::canInteract);
        registerFlagTester(Flag.INTERACT_ENTITY, this::canInteractEntity);
        registerFlagTester(Flag.DAMAGE_ENTITY, this::canDamageEntity);
        registerFlagTester(Flag.OPEN_CONTAINER, this::canOpenContainer);
        registerFlagTester(Flag.OPEN_DOOR, this::canOpenDoor);
        registerFlagTester(Flag.USE_BUTTON, this::canUseButton);
        registerFlagTester(Flag.USE_PRESSURE_PLATE, this::canUsePressurePlate);
    }

    private boolean canPlace(Player player, Location location) {
        String world = player.getWorld().getName();
        if (!nbpAPI.isWorldEnabled(world)) return true;
        return nbpAPI.canExecute(world, Flags.build);
    }

    private boolean canBreak(Player player, Location location) {
        String world = player.getWorld().getName();
        if (!nbpAPI.isWorldEnabled(world)) return true;
        return nbpAPI.canExecute(world, Flags.destroy);
    }

    private boolean canInteract(Player player, Location location) {
        String world = player.getWorld().getName();
        if (!nbpAPI.isWorldEnabled(world)) return true;
        return nbpAPI.canExecute(world, Flags.use);
    }

    private boolean canInteractEntity(Player player, Entity entity) {
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

    private boolean canDamageEntity(Player player, Entity entity) {
        String world = player.getWorld().getName();
        if (!nbpAPI.isWorldEnabled(world)) return true;
        return nbpAPI.canExecute(
                world,
                entity instanceof Player ? Flags.pvp : Flags.mob_damage
        );
    }

    private boolean canOpenContainer(Player player, Location location) {
        String world = player.getWorld().getName();
        if (!nbpAPI.isWorldEnabled(world)) return true;
        return nbpAPI.canExecute(world, Flags.container);
    }

    private boolean canOpenDoor(Player player, Location location) {
        String world = player.getWorld().getName();
        if (!nbpAPI.isWorldEnabled(world)) return true;
        return nbpAPI.canExecute(world, Flags.door_interact);
    }

    private boolean canUseButton(Player player, Location location) {
        String world = player.getWorld().getName();
        if (!nbpAPI.isWorldEnabled(world)) return true;
        return nbpAPI.canExecute(world, Flags.button);
    }

    private boolean canUsePressurePlate(Player player, Location location) {
        String world = player.getWorld().getName();
        if (!nbpAPI.isWorldEnabled(world)) return true;
        return nbpAPI.canExecute(world, Flags.build);
    }
}
