package net.momirealms.antigrieflib.comp;

import br.net.fabiozumbi12.RedProtect.Bukkit.API.RedProtectAPI;
import br.net.fabiozumbi12.RedProtect.Bukkit.RedProtect;
import net.momirealms.antigrieflib.AbstractAntiGriefCompatibility;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Optional;

public class RedProtectCompatibility extends AbstractAntiGriefCompatibility {

    private RedProtectAPI api;

    public RedProtectCompatibility(Plugin plugin) {
        super(plugin);
    }

    @Override
    public void init() {
        api = RedProtect.get().getAPI();
    }

    @Override
    public boolean canPlace(Player player, Location location) {
        return Optional.ofNullable(api.getRegion(location))
                .map(region -> region.canBuild(player))
                .orElse(true);
    }

    @Override
    public boolean canBreak(Player player, Location location) {
        return Optional.ofNullable(api.getRegion(location))
                .map(region -> region.canBuild(player))
                .orElse(true);
    }

    @Override
    public boolean canInteract(Player player, Location location) {
        return Optional.ofNullable(api.getRegion(location))
                .map(region -> region.canBuild(player))
                .orElse(true);
    }

    @Override
    public boolean canInteractEntity(Player player, Entity entity) {
        return Optional.ofNullable(api.getRegion(entity.getLocation()))
                .map(region -> region.canBuild(player))
                .orElse(true);
    }

    @Override
    public boolean canDamage(Player player, Entity entity) {
        return Optional.ofNullable(api.getRegion(entity.getLocation()))
                .map(region -> {
                    if (entity instanceof Player another) {
                        return region.canPVP(player, another);
                    } else {
                        return region.canBuild(player);
                    }
                })
                .orElse(true);
    }

    @Override
    public boolean canInteractContainer(Player player, Location location) {
        return Optional.ofNullable(api.getRegion(location))
                .map(region -> region.canChest(player))
                .orElse(true);
    }
}
