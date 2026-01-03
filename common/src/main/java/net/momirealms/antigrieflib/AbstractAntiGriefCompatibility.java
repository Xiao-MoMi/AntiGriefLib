package net.momirealms.antigrieflib;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.BiPredicate;

public abstract class AbstractAntiGriefCompatibility implements AntiGriefCompatibility {
    private final Map<Flag<?>, BiPredicate<Player, ?>> flagTesters = new IdentityHashMap<>(9);
    protected final Plugin plugin;

    public AbstractAntiGriefCompatibility(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public Plugin plugin() {
        return this.plugin;
    }

    protected final <T> void registerFlagTester(Flag<T> flag, BiPredicate<Player, T> tester) {
        this.flagTesters.put(flag, tester);
    }

    public <T> boolean test(Player player, @NotNull Flag<T> flag, T value) {
        @SuppressWarnings("unchecked")
        BiPredicate<Player, T> tester = (BiPredicate<Player, T>) this.flagTesters.get(flag);
        return tester != null && tester.test(player, value);
    }
}
