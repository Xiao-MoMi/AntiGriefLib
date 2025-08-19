package net.momirealms.antigrieflib;

import net.momirealms.antigrieflib.comp.*;
import net.momirealms.antigrieflib.util.ReflectionUtils;
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
            try {
                this.lib.detectSupportedPlugins();
            } catch (Exception e) {
                throw new CompatibilityException("AntiGriefLib failed to init", e);
            }
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

    @SuppressWarnings("deprecation")
    private void detectSupportedPlugins() {
        PluginManager manager = Bukkit.getPluginManager();
        if (manager.getPlugin("WorldGuard") != null) {
            if (ReflectionUtils.classExists("com{}sk89q{}worldguard{}WorldGuard")) {
                registerNewCompatibility(new WorldGuardComp(plugin));
            }
        }
        if (manager.getPlugin("Kingdoms") != null) {
            if (ReflectionUtils.classExists("org{}kingdoms{}constants{}land{}Land")) {
                registerNewCompatibility(new KingdomsComp(plugin));
            }
        }
        if (manager.getPlugin("Lands") != null) {
            if (ReflectionUtils.classExists("me{}angeschossen{}lands{}api{}LandsIntegration")) {
                registerNewCompatibility(new LandsComp(plugin));
            }
        }
        if (manager.getPlugin("IridiumSkyblock") != null) {
            if (ReflectionUtils.classExists("com{}iridium{}iridiumskyblock{}api{}IridiumSkyblockAPI")) {
                if (manager.getPlugin("IridiumSkyblock").getDescription().getVersion().startsWith("4.0")) {
                    registerNewCompatibility(new IridiumSkyblockComp(plugin));
                }
            }
        }
        if (manager.getPlugin("CrashClaim") != null) {
            if (ReflectionUtils.classExists("net{}crashcraft{}crashclaim{}CrashClaim")) {
                registerNewCompatibility(new CrashClaimComp(plugin));
            }
        }
        if (manager.getPlugin("GriefDefender") != null) {
            if (ReflectionUtils.classExists("com{}griefdefender{}api{}GriefDefender")) {
                registerNewCompatibility(new GriefDefenderComp(plugin));
            }
        }
        if (manager.getPlugin("HuskClaims") != null) {
            if (ReflectionUtils.classExists("net{}william278{}huskclaims{}api{}BukkitHuskClaimsAPI")) {
                registerNewCompatibility(new HuskClaimsComp(plugin));
            }
        }
        if (manager.getPlugin("BentoBox") != null) {
            if (ReflectionUtils.classExists("world{}bentobox{}bentobox{}BentoBox")) {
                registerNewCompatibility(new BentoBoxComp(plugin));
            }
        }
        if (manager.getPlugin("HuskTowns") != null) {
            if (ReflectionUtils.classExists("net{}william278{}husktowns{}api{}BukkitHuskTownsAPI")) {
                registerNewCompatibility(new HuskTownsComp(plugin));
            }
        }
        if (manager.getPlugin("PlotSquared") != null) {
            if (ReflectionUtils.classExists("com{}plotsquared{}bukkit{}util{}BukkitUtil")) {
                switch (manager.getPlugin("PlotSquared").getDescription().getVersion().charAt(0)) {
                    case '5' -> registerNewCompatibility(new PlotSquaredV5Comp(plugin));
                    case '6', '7' -> registerNewCompatibility(new PlotSquaredV6V7Comp(plugin));
                }
            }
        }
        if (manager.getPlugin("Residence") != null) {
            if (ReflectionUtils.classExists("com{}bekvon{}bukkit{}residence{}Residence")) {
                registerNewCompatibility(new ResidenceComp(plugin));
            }
        }
        if (manager.getPlugin("SuperiorSkyblock2") != null) {
            if (ReflectionUtils.classExists("com{}bgsoftware{}superiorskyblock{}SuperiorSkyblockPlugin")) {
                registerNewCompatibility(new SuperiorSkyblockComp(plugin));
            }
        }
        if (manager.getPlugin("Towny") != null) {
            if (ReflectionUtils.classExists("com{}palmergames{}bukkit{}towny{}TownyAPI")) {
                registerNewCompatibility(new TownyComp(plugin));
            }
        }
        if (manager.getPlugin("FabledSkyBlock") != null) {
            if (ReflectionUtils.classExists("com{}craftaro{}skyblock{}api{}SkyBlockAPI")) {
                registerNewCompatibility(new FabledSkyBlockComp(plugin));
            }
        }
        if (manager.getPlugin("GriefPrevention") != null) {
            if (ReflectionUtils.classExists("me{}ryanhamshire{}GriefPrevention{}Claim")) {
                registerNewCompatibility(new GriefPreventionComp(plugin));
            }
        }
        if (manager.getPlugin("RedProtect") != null) {
            if (ReflectionUtils.classExists("br{}net{}fabiozumbi12{}RedProtect{}Bukkit{}API{}RedProtectAPI")) {
                registerNewCompatibility(new RedProtectComp(plugin));
            }
        }
        if (manager.getPlugin("Landlord") != null) {
            if (ReflectionUtils.classExists("biz{}princeps{}landlord{}api{}ILandLord")) {
                registerNewCompatibility(new LandlordComp(plugin));
            }
        }
        if (manager.getPlugin("uSkyBlock") != null) {
            if (ReflectionUtils.classExists("us{}talabrek{}ultimateskyblock{}api{}uSkyBlockAPI")) {
                registerNewCompatibility(new USkyBlockComp(plugin));
            }
        }
        if (manager.getPlugin("XClaim") != null) {
            if (ReflectionUtils.classExists("codes{}wasabi{}xclaim{}api{}Claim")) {
                registerNewCompatibility(new XClaimComp(plugin));
            }
        }
        if (manager.getPlugin("UltimateClaims") != null) {
            if (ReflectionUtils.classExists("com{}craftaro{}ultimateclaims{}UltimateClaims")) {
                registerNewCompatibility(new UltimateClaimsComp(plugin));
            }
        }
        if (manager.getPlugin("UltimateClans") != null) {
            if (ReflectionUtils.classExists("me{}ulrich{}clans{}Clans")) {
                registerNewCompatibility(new UltimateClansComp(plugin));
            }
        }
        if (manager.getPlugin("PreciousStones") != null) {
            if (ReflectionUtils.classExists("net{}sacredlabyrinth{}Phaed{}PreciousStones{}api{}Api")) {
                registerNewCompatibility(new PreciousStonesComp(plugin));
            }
        }
        if (manager.getPlugin("hClaims") != null) {
            if (ReflectionUtils.classExists("com{}hakan{}claim{}shadow{}com{}hakan{}spinjection{}SpigotBootstrap")) {
                registerNewCompatibility(new HClaimsComp(plugin));
            }
        }
        if (manager.getPlugin("Dominion") != null) {
            if (ReflectionUtils.classExists("cn{}lunadeer{}dominion{}api{}DominionAPI")) {
                registerNewCompatibility(new DominionComp(plugin));
            }
        }
        if (manager.getPlugin("Factions") != null) {
            if (ReflectionUtils.classExists("com{}massivecraft{}factions{}zcore{}fperms{}PermissableAction")) {
                registerNewCompatibility(new SaberFactionsComp(plugin));
            }
            if (ReflectionUtils.classExists("com{}massivecraft{}factions{}perms{}PermissibleActions")) {
                registerNewCompatibility(new FactionsUUIDComp(plugin));
            }
        }
        if (manager.getPlugin("NoBuildPlus") != null) {
            if (ReflectionUtils.classExists("p1xel{}nobuildplus{}NoBuildPlus")) {
                registerNewCompatibility(new NoBuildPlusComp(plugin));
            }
        }
    }

    private void logHook(String pluginName) {
        if (silentLogs) return;
        plugin.getLogger().info("[AntiGriefLib] Enabled " + pluginName + " compatibility.");
    }
}