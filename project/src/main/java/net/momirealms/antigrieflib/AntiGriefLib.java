package net.momirealms.antigrieflib;

import net.momirealms.antigrieflib.comp.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;

public class AntiGriefLib {

    private final JavaPlugin plugin;
    private final Set<AntiGriefPlugin> plugins;
    private boolean ignoreOP;
    private boolean silentLogs;

    /**
     * Inner constructor
     */
    private AntiGriefLib(JavaPlugin plugin) {
        this.plugins = new HashSet<>();
        this.plugin = plugin;
    }

    private void init() {
        for (AntiGriefPlugin antiGriefPlugin : plugins) {
            antiGriefPlugin.init();
            logHook(antiGriefPlugin.getAntiGriefPluginName());
        }
    }

    /**
     * Get a builder
     *
     * @param plugin plugin
     * @return builder
     */
    public static Builder builder(JavaPlugin plugin) {
        return new Builder(plugin);
    }

    /**
     * Detects if a player has permission to place something at a certain location
     *
     * @param player player
     * @param location location
     * @return has perm or not
     */
    public boolean canPlace(Player player, Location location) {
        if (ignoreOP && player.isOp()) return true;
        for (AntiGriefPlugin antiGriefPlugin : plugins) {
            if (!antiGriefPlugin.canPlace(player, location)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Detects if a player has permission to break something at a certain location
     *
     * @param player player
     * @param location location
     * @return has perm or not
     */
    public boolean canBreak(Player player, Location location) {
        if (ignoreOP && player.isOp()) return true;
        for (AntiGriefPlugin antiGriefPlugin : plugins) {
            if (!antiGriefPlugin.canBreak(player, location)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Detects if a player has permission to interact something at a certain location
     *
     * @param player player
     * @param location location
     * @return has perm or not
     */
    public boolean canInteract(Player player, Location location) {
        if (ignoreOP && player.isOp()) return true;
        for (AntiGriefPlugin antiGriefPlugin : plugins) {
            if (!antiGriefPlugin.canInteract(player, location)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Detects if a player has permission to interact with an entity
     *
     * @param player player
     * @param entity entity
     * @return has perm or not
     */
    public boolean canInteractEntity(Player player, Entity entity) {
        if (ignoreOP && player.isOp()) return true;
        for (AntiGriefPlugin antiGriefPlugin : plugins) {
            if (!antiGriefPlugin.canInteractEntity(player, entity)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Detects if a player has permission to damage an entity
     *
     * @param player player
     * @param entity entity
     * @return has perm or not
     */
    public boolean canDamage(Player player, Entity entity) {
        if (entity instanceof Player another && !another.getWorld().getPVP()) {
            return false;
        }
        if (ignoreOP && player.isOp()) return true;
        for (AntiGriefPlugin antiGriefPlugin : plugins) {
            if (!antiGriefPlugin.canDamage(player, entity)) {
                return false;
            }
        }
        return true;
    }

    public static class Builder {

        private final AntiGriefLib lib;

        public Builder(JavaPlugin plugin) {
            this.lib = new AntiGriefLib(plugin);
            this.lib.detectSupportedPlugins();
        }

        public Builder ignoreOP(boolean ignoreOP) {
            lib.ignoreOP = ignoreOP;
            return this;
        }

        public Builder silentLogs(boolean silent) {
            lib.silentLogs = silent;
            return this;
        }

        public Builder addCompatibility(AntiGriefPlugin antiGriefPlugin) {
            lib.registerNewCompatibility(antiGriefPlugin);
            return this;
        }

        public AntiGriefLib build() {
            lib.init();
            return lib;
        }
    }

    public void registerNewCompatibility(AntiGriefPlugin antiGriefPlugin) {
        this.plugins.add(antiGriefPlugin);
    }

    private void detectSupportedPlugins() {
        PluginManager manager = Bukkit.getPluginManager();
        if (manager.getPlugin("WorldGuard") != null) {
            registerNewCompatibility(new WorldGuardComp(plugin));
        }
        if (manager.getPlugin("Kingdoms") != null) {
            registerNewCompatibility(new KingdomsComp(plugin));
        }
        if (manager.getPlugin("Lands") != null) {
            registerNewCompatibility(new LandsComp(plugin));
        }
        if (manager.getPlugin("IridiumSkyblock") != null) {
            registerNewCompatibility(new IridiumSkyblockComp(plugin));
        }
        if (manager.getPlugin("CrashClaim") != null) {
            registerNewCompatibility(new CrashClaimComp(plugin));
        }
        if (manager.getPlugin("GriefDefender") != null) {
            registerNewCompatibility(new GriefDefenderComp(plugin));
        }
        if (manager.getPlugin("HuskClaims") != null) {
            registerNewCompatibility(new HuskClaimsComp(plugin));
        }
        if (manager.getPlugin("BentoBox") != null) {
            registerNewCompatibility(new BentoBoxComp(plugin));
        }
        if (manager.getPlugin("HuskClaims") != null) {
            registerNewCompatibility(new HuskClaimsComp(plugin));
        }
        if (manager.getPlugin("HuskTowns") != null) {
            registerNewCompatibility(new HuskTownsComp(plugin));
        }
        if (manager.getPlugin("PlotSquared") != null) {
            switch (manager.getPlugin("PlotSquared").getDescription().getVersion().charAt(0)) {
                case '5' -> registerNewCompatibility(new PlotSquaredV5Comp(plugin));
                case '6', '7' -> registerNewCompatibility(new PlotSquaredV6V7Comp(plugin));
            }
        }
        if (manager.getPlugin("Residence") != null) {
            registerNewCompatibility(new ResidenceComp(plugin));
        }
        if (manager.getPlugin("SuperiorSkyblock2") != null) {
            registerNewCompatibility(new SuperiorSkyblockComp(plugin));
        }
        if (manager.getPlugin("Towny") != null) {
            registerNewCompatibility(new TownyComp(plugin));
        }
        if (manager.getPlugin("FabledSkyBlock") != null) {
            registerNewCompatibility(new FabledSkyBlockComp(plugin));
        }
        if (manager.getPlugin("GriefPrevention") != null) {
            registerNewCompatibility(new GriefPreventionComp(plugin));
        }
        if (manager.getPlugin("RedProtect") != null) {
            registerNewCompatibility(new RedProtectComp(plugin));
        }
        if (manager.getPlugin("Landlord") != null) {
            registerNewCompatibility(new LandlordComp(plugin));
        }
        if (manager.getPlugin("uSkyBlock") != null) {
            registerNewCompatibility(new USkyBlockComp(plugin));
        }
        if (manager.getPlugin("XClaim") != null) {
            registerNewCompatibility(new XClaimComp(plugin));
        }
        if (manager.getPlugin("UltimateClaims") != null) {
            registerNewCompatibility(new UltimateClaimsComp(plugin));
        }
        if (manager.getPlugin("UltimateClans") != null) {
            registerNewCompatibility(new UltimateClansComp(plugin));
        }
        if (manager.getPlugin("Factions") != null) {
            try {
                Class.forName("com.massivecraft.factions.zcore.fperms.PermissableAction");
                registerNewCompatibility(new SaberFactionsComp(plugin));
            } catch (ClassNotFoundException ignore) {
            }
            try {
                Class.forName("com.massivecraft.factions.perms.PermissibleActions");
                registerNewCompatibility(new FactionsUUIDComp(plugin));
            } catch (ClassNotFoundException ignore) {
            }
        }
    }

    private void logHook(String pluginName) {
        if (silentLogs) return;
        plugin.getLogger().info("[AntiGriefLib] Enabled " + pluginName + " compatibility.");
    }
}