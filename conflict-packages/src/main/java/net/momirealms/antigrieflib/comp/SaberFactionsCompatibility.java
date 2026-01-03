package net.momirealms.antigrieflib.comp;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.zcore.fperms.Access;
import com.massivecraft.factions.zcore.fperms.PermissableAction;
import net.momirealms.antigrieflib.AbstractAntiGriefCompatibility;
import net.momirealms.antigrieflib.Flag;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Optional;

public class SaberFactionsCompatibility extends AbstractAntiGriefCompatibility {

    public SaberFactionsCompatibility(Plugin plugin) {
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
        return Optional.ofNullable(Board.getInstance().getFactionAt(FLocation.wrap(location)))
                .map(faction -> faction.getAccess(FPlayers.getInstance().getByPlayer(player), PermissableAction.BUILD) == Access.ALLOW)
                .orElse(true);
    }

    private boolean canBreak(Player player, Location location) {
        return Optional.ofNullable(Board.getInstance().getFactionAt(FLocation.wrap(location)))
                .map(faction -> faction.getAccess(FPlayers.getInstance().getByPlayer(player), PermissableAction.DESTROY) == Access.ALLOW)
                .orElse(true);
    }

    private boolean canInteract(Player player, Location location) {
        return Optional.ofNullable(Board.getInstance().getFactionAt(FLocation.wrap(location)))
                .map(faction -> faction.getAccess(FPlayers.getInstance().getByPlayer(player), PermissableAction.CONTAINER) == Access.ALLOW)
                .orElse(true);
    }

    private boolean canInteractEntity(Player player, Entity entity) {
        return Optional.ofNullable(Board.getInstance().getFactionAt(FLocation.wrap(entity.getLocation())))
                .map(faction -> faction.getAccess(FPlayers.getInstance().getByPlayer(player), PermissableAction.CONTAINER) == Access.ALLOW)
                .orElse(true);
    }

    private boolean canDamageEntity(Player player, Entity entity) {
        return Optional.ofNullable(Board.getInstance().getFactionAt(FLocation.wrap(entity.getLocation())))
                .map(faction -> faction.getAccess(FPlayers.getInstance().getByPlayer(player), PermissableAction.CONTAINER) == Access.ALLOW)
                .orElse(true);
    }

    private boolean canOpenContainer(Player player, Location location) {
        return Optional.ofNullable(Board.getInstance().getFactionAt(FLocation.wrap(location)))
                .map(faction -> faction.getAccess(FPlayers.getInstance().getByPlayer(player), PermissableAction.CONTAINER) == Access.ALLOW)
                .orElse(true);
    }

    private boolean canOpenDoor(Player player, Location location) {
        return Optional.ofNullable(Board.getInstance().getFactionAt(FLocation.wrap(location)))
                .map(faction -> faction.getAccess(FPlayers.getInstance().getByPlayer(player), PermissableAction.DOOR) == Access.ALLOW)
                .orElse(true);
    }

    private boolean canUseButton(Player player, Location location) {
        return Optional.ofNullable(Board.getInstance().getFactionAt(FLocation.wrap(location)))
                .map(faction -> faction.getAccess(FPlayers.getInstance().getByPlayer(player), PermissableAction.BUTTON) == Access.ALLOW)
                .orElse(true);
    }

    private boolean canUsePressurePlate(Player player, Location location) {
        return Optional.ofNullable(Board.getInstance().getFactionAt(FLocation.wrap(location)))
                .map(faction -> faction.getAccess(FPlayers.getInstance().getByPlayer(player), PermissableAction.BUILD) == Access.ALLOW)
                .orElse(true);
    }
}
