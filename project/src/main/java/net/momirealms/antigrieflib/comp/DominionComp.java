package net.momirealms.antigrieflib.comp;

import cn.lunadeer.dominion.api.DominionAPI;
import cn.lunadeer.dominion.api.dtos.DominionDTO;
import cn.lunadeer.dominion.api.dtos.flag.Flags;
import net.momirealms.antigrieflib.AbstractComp;
import net.momirealms.antigrieflib.CompatibilityException;
import net.momirealms.antigrieflib.util.ReflectionUtils;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("UnstableApiUsage")
public class DominionComp extends AbstractComp {
    private DominionAPI api;

    public DominionComp(JavaPlugin plugin) {
        super(plugin, "Dominion");
    }

    @Override
    public boolean checkClazz() {
        return ReflectionUtils.classExists("cn.lunadeer.dominion.api.DominionAPI");
    }

    @Override
    public void init() {
        try {
            this.api = DominionAPI.getInstance();
        } catch (IllegalArgumentException | ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
            throw new CompatibilityException("Could not instantiate Dominion API", e);
        }
    }

    @Override
    public boolean canPlace(Player player, Location location) {
        DominionDTO dto = this.api.getDominion(location);
        if (dto == null) return true;
        return this.api.checkPrivilegeFlag(dto, Flags.PLACE, player);
    }

    @Override
    public boolean canBreak(Player player, Location location) {
        DominionDTO dto = this.api.getDominion(location);
        if (dto == null) return true;
        return this.api.checkPrivilegeFlag(dto, Flags.BREAK_BLOCK, player);
    }

    @Override
    public boolean canInteract(Player player, Location location) {
        DominionDTO dto = this.api.getDominion(location);
        if (dto == null) return true;
        return this.api.checkPrivilegeFlag(dto, Flags.PLACE, player);
    }

    @Override
    public boolean canInteractEntity(Player player, Entity entity) {
        DominionDTO dto = this.api.getDominion(entity.getLocation());
        if (dto == null) return true;
        return this.api.checkPrivilegeFlag(dto, Flags.PLACE, player);
    }

    @Override
    public boolean canDamage(Player player, Entity entity) {
        DominionDTO dto = this.api.getDominion(entity.getLocation());
        if (dto == null) return true;
        if (entity instanceof Monster) {
            return this.api.checkEnvironmentFlag(dto, Flags.MONSTER_DAMAGE);
        } else if (entity instanceof Villager) {
            return this.api.checkPrivilegeFlag(dto, Flags.VILLAGER_KILLING, player);
        } else if (entity instanceof Animals) {
            return this.api.checkPrivilegeFlag(dto, Flags.ANIMAL_KILLING, player);
        } else {
            return false;
        }
    }
}