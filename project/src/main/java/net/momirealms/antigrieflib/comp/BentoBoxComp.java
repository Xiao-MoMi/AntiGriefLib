package net.momirealms.antigrieflib.comp;

import net.momirealms.antigrieflib.AbstractComp;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import world.bentobox.bentobox.BentoBox;
import world.bentobox.bentobox.api.user.User;
import world.bentobox.bentobox.lists.Flags;

public class BentoBoxComp extends AbstractComp {

    public BentoBoxComp(JavaPlugin plugin) {
        super(plugin, "BentoBox");
    }

    @Override
    public void init() {
    }

    @Override
    public boolean canPlace(Player player, Location location) {
        return BentoBox.getInstance()
                .getIslands()
                .getIslandAt(location)
                .map(island -> island.isAllowed(User.getInstance(player), Flags.PLACE_BLOCKS))
                .orElse(true);
    }

    @Override
    public boolean canBreak(Player player, Location location) {
        return BentoBox.getInstance()
                .getIslands()
                .getIslandAt(location)
                .map(island -> island.isAllowed(User.getInstance(player), Flags.BREAK_BLOCKS))
                .orElse(true);
    }

    @Override
    public boolean canInteract(Player player, Location location) {
        return BentoBox.getInstance()
                .getIslands()
                .getIslandAt(location)
                .map(island -> island.isAllowed(User.getInstance(player), Flags.CONTAINER))
                .orElse(true);
    }

    @Override
    public boolean canInteractEntity(Player player, Entity entity) {
        // I am not sure if I should use Flags.HURT_xxx or Flags.CONTAINER
        return entityOperation(player, entity);
    }

    @Override
    public boolean canDamage(Player player, Entity entity) {
        return entityOperation(player, entity) && (!(entity instanceof Player) || entity.getWorld().getPVP());
    }

    private boolean entityOperation(final Player player, final Entity entity) {
        return BentoBox.getInstance()
                .getIslands()
                .getIslandAt(entity.getLocation())
                .map(island -> island.isAllowed(User.getInstance(player), switch (getType(entity)) {
                    case VILLAGER -> Flags.HURT_VILLAGERS;
                    case HOSTILE -> Flags.HURT_MONSTERS;
                    case PASSIVE -> Flags.HURT_ANIMALS;
                    case PLAYER -> switch (entity.getWorld().getEnvironment()) {
                        case NORMAL, CUSTOM -> Flags.PVP_OVERWORLD;
                        case NETHER -> Flags.PVP_NETHER;
                        case THE_END -> Flags.PVP_END;
                    };
                    default -> Flags.CONTAINER;
                }))
                .orElse(true);
    }

    private enum GameEntityType {
        PASSIVE, VILLAGER, HOSTILE, PLAYER, OTHER
    }

    private static GameEntityType getType(Entity entity) {
        return switch (entity.getType()) {
            case SKELETON, STRAY, WITHER_SKELETON, BLAZE, CAVE_SPIDER, CREEPER, DROWNED, ELDER_GUARDIAN, ENDERMAN, GIANT, GUARDIAN, HUSK, PILLAGER, ILLUSIONER, PIGLIN, PIGLIN_BRUTE, ZOMBIFIED_PIGLIN, EVOKER, VINDICATOR, RAVAGER, WITCH, SILVERFISH, SPIDER, VEX, WARDEN, WITHER, ZOGLIN, ZOMBIE, ZOMBIE_VILLAGER, GHAST, PHANTOM, SLIME, MAGMA_CUBE, SHULKER, ENDER_DRAGON, ENDERMITE, PUFFERFISH ->
                    GameEntityType.HOSTILE;
            case CAMEL, DONKEY, HORSE, LLAMA, MULE, SKELETON_HORSE, TRADER_LLAMA, ZOMBIE_HORSE, AXOLOTL, BEE, CAT, CHICKEN, COW, FOX, FROG, GOAT, HOGLIN, MUSHROOM_COW, OCELOT, PANDA, PARROT, PIG, POLAR_BEAR, RABBIT, SHEEP, SNIFFER, STRIDER, WOLF, IRON_GOLEM, SNOWMAN, COD, DOLPHIN, SALMON, TADPOLE, TROPICAL_FISH, GLOW_SQUID, SQUID, BAT, ALLAY, TURTLE ->
                    GameEntityType.PASSIVE;
            case VILLAGER, WANDERING_TRADER -> GameEntityType.VILLAGER;
            case PLAYER -> GameEntityType.PLAYER;
            default -> GameEntityType.OTHER;
        };
    }
}
