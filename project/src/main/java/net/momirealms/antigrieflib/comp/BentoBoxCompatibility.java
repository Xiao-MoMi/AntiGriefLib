package net.momirealms.antigrieflib.comp;

import net.momirealms.antigrieflib.AbstractAntiGriefCompatibility;
import net.momirealms.antigrieflib.Flag;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import world.bentobox.bentobox.BentoBox;
import world.bentobox.bentobox.api.user.User;
import world.bentobox.bentobox.lists.Flags;

public class BentoBoxCompatibility extends AbstractAntiGriefCompatibility {

    public BentoBoxCompatibility(Plugin plugin) {
        super(plugin);
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void init() {
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
        return BentoBox.getInstance()
                .getIslands()
                .getIslandAt(location)
                .map(island -> island.isAllowed(User.getInstance(player), Flags.PLACE_BLOCKS))
                .orElse(true);
    }

    private boolean canBreak(Player player, Location location) {
        return BentoBox.getInstance()
                .getIslands()
                .getIslandAt(location)
                .map(island -> island.isAllowed(User.getInstance(player), Flags.BREAK_BLOCKS))
                .orElse(true);
    }

    private boolean canInteract(Player player, Location location) {
        return BentoBox.getInstance()
                .getIslands()
                .getIslandAt(location)
                .map(island -> island.isAllowed(User.getInstance(player), Flags.CONTAINER))
                .orElse(true);
    }

    private boolean canInteractEntity(Player player, Entity entity) {
        return BentoBox.getInstance()
                .getIslands()
                .getIslandAt(entity.getLocation())
                .map(island -> island.isAllowed(User.getInstance(player), Flags.CONTAINER))
                .orElse(true);
    }

    private boolean canDamageEntity(Player player, Entity entity) {
        return entityOperation(player, entity);
    }

    private boolean canOpenContainer(Player player, Location location) {
        return BentoBox.getInstance()
                .getIslands()
                .getIslandAt(location)
                .map(island -> island.isAllowed(User.getInstance(player), Flags.CONTAINER))
                .orElse(true);
    }

    private boolean canOpenDoor(Player player, Location location) {
        return BentoBox.getInstance()
                .getIslands()
                .getIslandAt(location)
                .map(island -> island.isAllowed(User.getInstance(player), Flags.DOOR))
                .orElse(true);
    }

    private boolean canUseButton(Player player, Location location) {
        return BentoBox.getInstance()
                .getIslands()
                .getIslandAt(location)
                .map(island -> island.isAllowed(User.getInstance(player), Flags.BUTTON))
                .orElse(true);
    }

    private boolean canUsePressurePlate(Player player, Location location) {
        return BentoBox.getInstance()
                .getIslands()
                .getIslandAt(location)
                .map(island -> island.isAllowed(User.getInstance(player), Flags.PRESSURE_PLATE))
                .orElse(true);
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
