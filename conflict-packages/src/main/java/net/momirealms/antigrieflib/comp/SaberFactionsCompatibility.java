package net.momirealms.antigrieflib.comp;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.zcore.fperms.Access;
import com.massivecraft.factions.zcore.fperms.PermissableAction;
import net.momirealms.antigrieflib.AbstractAntiGriefCompatibility;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Optional;

public class SaberFactionsCompatibility extends AbstractAntiGriefCompatibility {

    public SaberFactionsCompatibility(Plugin plugin) {
        super(plugin);
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

    @Override
    public boolean canInteractEntity(Player player, Entity entity) {
        return Optional.ofNullable(Board.getInstance().getFactionAt(FLocation.wrap(entity.getLocation())))
                .map(faction -> faction.getAccess(FPlayers.getInstance().getByPlayer(player), PermissableAction.CONTAINER) == Access.ALLOW)
                .orElse(true);
    }

    @Override
    public boolean canDamage(Player player, Entity entity) {
        return Optional.ofNullable(Board.getInstance().getFactionAt(FLocation.wrap(entity.getLocation())))
                .map(faction -> faction.getAccess(FPlayers.getInstance().getByPlayer(player), PermissableAction.CONTAINER) == Access.ALLOW)
                .orElse(true);
    }

    @Override
    public boolean canOpenContainer(Player player, Location location) {
        return Optional.ofNullable(Board.getInstance().getFactionAt(FLocation.wrap(location)))
                .map(faction -> faction.getAccess(FPlayers.getInstance().getByPlayer(player), PermissableAction.CONTAINER) == Access.ALLOW)
                .orElse(true);
    }
}
