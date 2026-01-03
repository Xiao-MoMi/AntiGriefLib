package net.momirealms.antigrieflib;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public final class Flag<T> {
    public static final Set<Flag<?>> ALL_FLAGS = new ObjectOpenHashSet<>();
    public static final Flag<Location> PLACE = Flag.of(Location.class, "place");
    public static final Flag<Location> BREAK = Flag.of(Location.class, "break");
    public static final Flag<Location> INTERACT = Flag.of(Location.class, "interact");
    public static final Flag<Entity> INTERACT_ENTITY = Flag.of(Entity.class, "interact_entity");
    public static final Flag<Entity> DAMAGE_ENTITY = Flag.of(Entity.class, "damage_entity");
    public static final Flag<Location> OPEN_CONTAINER = Flag.of(Location.class, "open_container");
    public static final Flag<Location> OPEN_DOOR = Flag.of(Location.class, "open_door");
    public static final Flag<Location> USE_BUTTON = Flag.of(Location.class, "use_button");
    public static final Flag<Location> USE_PRESSURE_PLATE = Flag.of(Location.class, "use_pressure_plate");

    public final Class<T> type;
    public final String name;
    @Nullable
    private Integer hashCode;

    private Flag(Class<T> type, String name) {
        this.type = type;
        this.name = name;
        ALL_FLAGS.add(this);
    }

    public static <T> Flag<T> of(@NotNull Class<T> type, @NotNull String name) {
        return new Flag<>(type, name);
    }

    @Override
    public String toString() {
        return "Flag{type=" + this.type + ", name=" + this.name + '}';
    }

    @Override
    public boolean equals(Object other) {
        return this == other || other instanceof Flag<?> flag
                && this.type.equals(flag.type)
                && this.name.equals(flag.name);
    }

    @Override
    public int hashCode() {
        if (this.hashCode == null) {
            this.hashCode = this.generateHashCode();
        }
        return this.hashCode;
    }

    private int generateHashCode() {
        return 31 * this.type.hashCode() + this.name.hashCode();
    }

}
