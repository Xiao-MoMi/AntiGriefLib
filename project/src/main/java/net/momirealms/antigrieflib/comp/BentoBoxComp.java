package net.momirealms.antigrieflib.comp;

import net.momirealms.antigrieflib.AbstractComp;
import org.bukkit.Location;
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
}
