package net.momirealms.antigrieflib.comp;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.zcore.fperms.Access;
import com.massivecraft.factions.zcore.fperms.PermissableAction;
import net.momirealms.antigrieflib.AbstractComp;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Optional;

public class SaberFactionsComp extends AbstractComp {

    public SaberFactionsComp(JavaPlugin plugin) {
        super(plugin, "SaberFactions");
    }

    @Override
    public void init() {
    }

    @Override
    public boolean canPlace(Player player, Location location) {
        return Optional.ofNullable(Board.getInstance().getFactionAt(FLocation.wrap(location)))
                .map(faction -> faction.getAccess(FPlayers.getInstance().getByPlayer(player), PermissableAction.BUILD) == Access.ALLOW)
                .orElse(true);
    }

    @Override
    public boolean canBreak(Player player, Location location) {
        return Optional.ofNullable(Board.getInstance().getFactionAt(FLocation.wrap(location)))
                .map(faction -> faction.getAccess(FPlayers.getInstance().getByPlayer(player), PermissableAction.DESTROY) == Access.ALLOW)
                .orElse(true);
    }

    @Override
    public boolean canInteract(Player player, Location location) {
        return Optional.ofNullable(Board.getInstance().getFactionAt(FLocation.wrap(location)))
                .map(faction -> faction.getAccess(FPlayers.getInstance().getByPlayer(player), PermissableAction.CONTAINER) == Access.ALLOW)
                .orElse(true);
    }
}
