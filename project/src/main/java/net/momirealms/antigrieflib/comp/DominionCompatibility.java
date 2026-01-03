package net.momirealms.antigrieflib.comp;

import cn.lunadeer.dominion.api.DominionAPI;
import cn.lunadeer.dominion.api.dtos.DominionDTO;
import cn.lunadeer.dominion.api.dtos.flag.Flags;
import net.momirealms.antigrieflib.AbstractAntiGriefCompatibility;
import net.momirealms.antigrieflib.Flag;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.plugin.Plugin;

public class DominionCompatibility extends AbstractAntiGriefCompatibility {
    private DominionAPI api;

    public DominionCompatibility(Plugin plugin) {
        super(plugin);
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void init() {
        try {
            this.api = DominionAPI.getInstance();
        } catch (Exception e) {
            throw new RuntimeException("Failed to init DominionAPI", e);
        }
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
        DominionDTO dto = this.api.getDominion(location);
        if (dto == null) return true;
        return this.api.checkPrivilegeFlag(dto, Flags.PLACE, player);
    }

    private boolean canBreak(Player player, Location location) {
        DominionDTO dto = this.api.getDominion(location);
        if (dto == null) return true;
        return this.api.checkPrivilegeFlag(dto, Flags.BREAK_BLOCK, player);
    }

    private boolean canInteract(Player player, Location location) {
        DominionDTO dto = this.api.getDominion(location);
        if (dto == null) return true;
        return this.api.checkPrivilegeFlag(dto, Flags.PLACE, player);
    }

    private boolean canInteractEntity(Player player, Entity entity) {
        DominionDTO dto = this.api.getDominion(entity.getLocation());
        if (dto == null) return true;
        return this.api.checkPrivilegeFlag(dto, Flags.PLACE, player);
    }

    private boolean canDamageEntity(Player player, Entity entity) {
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

    private boolean canOpenContainer(Player player, Location location) {
        DominionDTO dto = this.api.getDominion(location);
        if (dto == null) return true;
        return this.api.checkPrivilegeFlag(dto, Flags.CONTAINER, player);
    }

    private boolean canOpenDoor(Player player, Location location) {
        DominionDTO dto = this.api.getDominion(location);
        if (dto == null) return true;
        return this.api.checkPrivilegeFlag(dto, Flags.DOOR, player);
    }

    private boolean canUseButton(Player player, Location location) {
        DominionDTO dto = this.api.getDominion(location);
        if (dto == null) return true;
        return this.api.checkPrivilegeFlag(dto, Flags.BUTTON, player);
    }

    private boolean canUsePressurePlate(Player player, Location location) {
        DominionDTO dto = this.api.getDominion(location);
        if (dto == null) return true;
        return this.api.checkPrivilegeFlag(dto, Flags.PRESSURE, player);
    }
}