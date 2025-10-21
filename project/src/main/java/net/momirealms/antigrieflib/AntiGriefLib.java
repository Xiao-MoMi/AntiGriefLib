package net.momirealms.antigrieflib;

import net.momirealms.antigrieflib.comp.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.logging.Level;

public final class AntiGriefLib {
    private final JavaPlugin plugin;
    private final AntiGriefCompatibility[] providers;
    private final boolean ignoreOP;
    private final boolean silentLogs;
    private final boolean suppressErrors;
    private final String bypassPermission;

    /**
     * Inner constructor
     */
    private AntiGriefLib(JavaPlugin plugin,
                         AntiGriefCompatibility[] providers,
                         boolean ignoreOP,
                         boolean silentLogs,
                         boolean logErrors,
                         String bypassPermission) {
        this.plugin = plugin;
        this.providers = providers;
        this.ignoreOP = ignoreOP;
        this.silentLogs = silentLogs;
        this.suppressErrors = logErrors;
        this.bypassPermission = bypassPermission;
    }

    @NotNull
    public AntiGriefCompatibility[] providers() {
        return this.providers;
    }

    @Nullable
    public String bypassPermission() {
        return bypassPermission;
    }

    @NotNull
    public JavaPlugin plugin() {
        return plugin;
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
        if (this.ignoreOP && player.isOp()) return true;
        if (this.bypassPermission != null && player.hasPermission(this.bypassPermission)) return true;
        try {
            for (AntiGriefCompatibility antiGrief : this.providers) {
                if (!antiGrief.canPlace(player, location)) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            if (!this.suppressErrors) {
                throw new AntiGriefException(e);
            }
            return false;
        }
    }
    
    /**
     * Detects if a player has permission to break something at a certain location
     *
     * @param player player
     * @param location location
     * @return has perm or not
     */
    public boolean canBreak(Player player, Location location) {
        if (this.ignoreOP && player.isOp()) return true;
        if (this.bypassPermission != null && player.hasPermission(this.bypassPermission)) return true;
        try {
            for (AntiGriefCompatibility antiGrief : this.providers) {
                if (!antiGrief.canBreak(player, location)) {
                    return false;
                }
            }
        } catch (Exception e) {
            if (!this.suppressErrors) {
                throw new AntiGriefException(e);
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
        if (this.ignoreOP && player.isOp()) return true;
        if (this.bypassPermission != null && player.hasPermission(this.bypassPermission)) return true;
        try {
            for (AntiGriefCompatibility antiGrief : this.providers) {
                if (!antiGrief.canInteract(player, location)) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            if (!this.suppressErrors) {
                throw new AntiGriefException(e);
            }
            return false;
        }
    }

    /**
     * Detects if a player has permission to interact with an entity
     *
     * @param player player
     * @param entity entity
     * @return has perm or not
     */
    public boolean canInteractEntity(Player player, Entity entity) {
        if (this.ignoreOP && player.isOp()) return true;
        if (this.bypassPermission != null && player.hasPermission(this.bypassPermission)) return true;
        try {
            for (AntiGriefCompatibility antiGrief : this.providers) {
                if (!antiGrief.canInteractEntity(player, entity)) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            if (!this.suppressErrors) {
                throw new AntiGriefException(e);
            }
            return false;
        }
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
        if (this.ignoreOP && player.isOp()) return true;
        if (this.bypassPermission != null && player.hasPermission(this.bypassPermission)) return true;
        try {
            for (AntiGriefCompatibility antiGrief : this.providers) {
                if (!antiGrief.canDamage(player, entity)) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            if (!this.suppressErrors) {
                throw new AntiGriefException(e);
            }
            return false;
        }
    }

    /**
     * Detects if a player has permission to open a container
     *
     * @param player player
     * @param location location
     * @return has perm or not
     */
    public boolean canOpenContainer(Player player, Location location) {
        if (this.ignoreOP && player.isOp()) return true;
        if (this.bypassPermission != null && player.hasPermission(this.bypassPermission)) return true;
        try {
            for (AntiGriefCompatibility antiGrief : this.providers) {
                if (!antiGrief.canOpenContainer(player, location)) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            if (!this.suppressErrors) {
                throw new AntiGriefException(e);
            }
            return false;
        }
    }

    public static class Builder {
        private final Map<String, AntiGriefCompatibility> byId = new HashMap<>();
        private final JavaPlugin plugin;
        private boolean ignoreOP = false;
        private boolean silentLogs = false;
        private boolean suppressErrors = false;
        private String bypassPermission = null;
        private Predicate<Plugin> exclude = (p) -> false;

        public Builder(JavaPlugin plugin) {
            this.plugin = plugin;
        }

        public Builder ignoreOP(boolean ignoreOP) {
            this.ignoreOP = ignoreOP;
            return this;
        }

        public Builder bypassPermission(String bypassPermission) {
            this.bypassPermission = bypassPermission;
            return this;
        }

        public Builder silentLogs(boolean silent) {
            this.silentLogs = silent;
            return this;
        }
        
        public Builder suppressErrors(boolean suppress) {
            this.suppressErrors = suppress;
            return this;
        }
        
        public Builder exclude(@NotNull Predicate<Plugin> exclude) {
            this.exclude = exclude;
            return this;
        }

        public Builder register(@NotNull AntiGriefCompatibility antiGrief) {
            AntiGriefCompatibility previous = this.byId.get(antiGrief.id());
            if (previous != null && previous != antiGrief) {
                throw new IllegalStateException("AntiGriefCompatibility '" + antiGrief.id() + "' has already been registered");
            }
            antiGrief.init();
            this.byId.put(antiGrief.id(), antiGrief);
            this.logHookInternal(antiGrief);
            return this;
        }

        @Nullable
        public AntiGriefCompatibility removeById(String id) {
            return this.byId.remove(id);
        }

        public AntiGriefLib build() {
            this.detectSupportedPlugins(s -> {
                try {
                    AntiGriefCompatibility compatibility = s.get();
                    this.register(compatibility);
                } catch (Throwable t) {
                    if (!this.suppressErrors) {
                        this.plugin.getLogger().log(Level.SEVERE, "Please report this error on https://github.com/Xiao-MoMi/AntiGriefLib/issues", t);
                    }
                }
            });
            return new AntiGriefLib(
                    this.plugin,
                    this.byId.values().toArray(new AntiGriefCompatibility[0]),
                    this.ignoreOP,
                    this.silentLogs,
                    this.suppressErrors,
                    this.bypassPermission
            );
        }
        
        private boolean isIncluded(Plugin plugin) {
            return !this.exclude.test(plugin);
        }

        private void logHookInternal(AntiGriefCompatibility plugin) {
            if (this.silentLogs) return;
            this.plugin.getLogger().info("[AntiGriefLib] Enabled " + plugin.plugin().getName() + " compatibility.");
        }
        
        private Optional<Plugin> getOptionalPlugin(String name) {
            return Optional.ofNullable(Bukkit.getPluginManager().getPlugin(name)).filter(this::isIncluded);
        }

        private boolean classExists(@NotNull final String clazz) {
            try {
                Class.forName(clazz.replace("{}", "."));
                return true;
            } catch (Throwable e) {
                return false;
            }
        }

        @SuppressWarnings("deprecation")
        private void detectSupportedPlugins(Consumer<Supplier<AntiGriefCompatibility>> consumer) {
            getOptionalPlugin("WorldGuard").ifPresent(plugin -> {
                if (classExists("com{}sk89q{}worldguard{}WorldGuard")) {
                    consumer.accept(() -> new WorldGuardCompatibility(plugin));
                }
            });
            getOptionalPlugin("Kingdoms").ifPresent(plugin -> {
                if (classExists("org{}kingdoms{}constants{}land{}Land")) {
                    consumer.accept(() -> new KingdomsCompatibility(plugin));
                }
            });
            getOptionalPlugin("Lands").ifPresent(plugin -> {
                if (classExists("me{}angeschossen{}lands{}api{}LandsIntegration")) {
                    consumer.accept(() -> new LandsCompatibility(plugin, this.plugin));
                }
            });
            getOptionalPlugin("IridiumSkyblock").ifPresent(plugin -> {
                if (classExists("com{}iridium{}iridiumskyblock{}api{}IridiumSkyblockAPI")) {
                    if (plugin.getDescription().getVersion().startsWith("4")) {
                        consumer.accept(() -> new IridiumSkyblockCompatibility(plugin));
                    }
                }
            });
            getOptionalPlugin("CrashClaim").ifPresent(plugin -> {
                if (classExists("net{}crashcraft{}crashclaim{}CrashClaim")) {
                    consumer.accept(() -> new CrashClaimCompatibility(plugin));
                }
            });
            getOptionalPlugin("GriefDefender").ifPresent(plugin -> {
                if (classExists("com{}griefdefender{}api{}GriefDefender")) {
                    consumer.accept(() -> new GriefDefenderCompatibility(plugin));
                }
            });
            getOptionalPlugin("HuskClaims").ifPresent(plugin -> {
                if (classExists("net{}william278{}huskclaims{}api{}BukkitHuskClaimsAPI")) {
                    consumer.accept(() -> new HuskClaimsCompatibility(plugin));
                }
            });
            getOptionalPlugin("BentoBox").ifPresent(plugin -> {
                if (classExists("world{}bentobox{}bentobox{}BentoBox")) {
                    consumer.accept(() -> new BentoBoxCompatibility(plugin));
                }
            });
            getOptionalPlugin("HuskTowns").ifPresent(plugin -> {
                if (classExists("net{}william278{}husktowns{}api{}BukkitHuskTownsAPI")) {
                    consumer.accept(() -> new HuskTownsCompatibility(plugin));
                }
            });
            getOptionalPlugin("PlotSquared").ifPresent(plugin -> {
                if (classExists("com{}plotsquared{}bukkit{}util{}BukkitUtil")) {
                    switch (plugin.getDescription().getVersion().charAt(0)) {
                        case '5' -> consumer.accept(() -> new PlotSquaredV5Compatibility(plugin));
                        case '6', '7' -> consumer.accept(() -> new PlotSquaredV6V7Compatibility(plugin));
                    }
                }
            });
            getOptionalPlugin("Residence").ifPresent(plugin -> {
                if (classExists("com{}bekvon{}bukkit{}residence{}Residence")) {
                    consumer.accept(() -> new ResidenceCompatibility(plugin));
                }
            });
            getOptionalPlugin("SuperiorSkyblock2").ifPresent(plugin -> {
                if (classExists("com{}bgsoftware{}superiorskyblock{}SuperiorSkyblockPlugin")) {
                    consumer.accept(() -> new SuperiorSkyblockCompatibility(plugin));
                }
            });
            getOptionalPlugin("Towny").ifPresent(plugin -> {
                if (classExists("com{}palmergames{}bukkit{}towny{}TownyAPI")) {
                    consumer.accept(() -> new TownyCompatibility(plugin));
                }
            });
            getOptionalPlugin("FabledSkyBlock").ifPresent(plugin -> {
                if (classExists("com{}craftaro{}skyblock{}api{}SkyBlockAPI")) {
                    consumer.accept(() -> new FabledSkyBlockCompatibility(plugin));
                }
            });
            getOptionalPlugin("GriefPrevention").ifPresent(plugin -> {
                if (classExists("me{}ryanhamshire{}GriefPrevention{}Claim")) {
                    consumer.accept(() -> new GriefPreventionCompatibility(plugin));
                }
            });
            getOptionalPlugin("RedProtect").ifPresent(plugin -> {
                if (classExists("br{}net{}fabiozumbi12{}RedProtect{}Bukkit{}API{}RedProtectAPI")) {
                    consumer.accept(() -> new RedProtectCompatibility(plugin));
                }
            });
            getOptionalPlugin("Landlord").ifPresent(plugin -> {
                if (classExists("biz{}princeps{}landlord{}api{}ILandLord")) {
                    consumer.accept(() -> new LandlordCompatibility(plugin));
                }
            });
            getOptionalPlugin("uSkyBlock").ifPresent(plugin -> {
                if (classExists("us{}talabrek{}ultimateskyblock{}api{}uSkyBlockAPI")) {
                    consumer.accept(() -> new USkyBlockCompatibility(plugin));
                }
            });
            getOptionalPlugin("XClaim").ifPresent(plugin -> {
                if (classExists("codes{}wasabi{}xclaim{}api{}Claim")) {
                    consumer.accept(() -> new XClaimCompatibility(plugin));
                }
            });
            getOptionalPlugin("UltimateClaims").ifPresent(plugin -> {
                if (classExists("com{}craftaro{}ultimateclaims{}UltimateClaims")) {
                    consumer.accept(() -> new UltimateClaimsCompatibility(plugin));
                }
            });
            getOptionalPlugin("UltimateClans").ifPresent(plugin -> {
                if (classExists("me{}ulrich{}clans{}Clans")) {
                    consumer.accept(() -> new UltimateClansCompatibility(plugin));
                }
            });
            getOptionalPlugin("PreciousStones").ifPresent(plugin -> {
                if (classExists("net{}sacredlabyrinth{}Phaed{}PreciousStones{}api{}Api")) {
                    consumer.accept(() -> new PreciousStonesCompatibility(plugin));
                }
            });
            getOptionalPlugin("hClaims").ifPresent(plugin -> {
                if (classExists("com{}hakan{}claim{}shadow{}com{}hakan{}spinjection{}SpigotBootstrap")) {
                    consumer.accept(() -> new HClaimsCompatibility(plugin));
                }
            });
            getOptionalPlugin("Dominion").ifPresent(plugin -> {
                if (classExists("cn{}lunadeer{}dominion{}api{}DominionAPI")) {
                    consumer.accept(() -> new DominionCompatibility(plugin));
                }
            });
            getOptionalPlugin("Factions").ifPresent(plugin -> {
                if (classExists("com{}massivecraft{}factions{}zcore{}fperms{}PermissableAction")) {
                    consumer.accept(() -> new SaberFactionsCompatibility(plugin));
                }
                if (classExists("com{}massivecraft{}factions{}perms{}PermissibleActions")) {
                    consumer.accept(() -> new FactionsUUIDCompatibility(plugin));
                }
            });
            getOptionalPlugin("NoBuildPlus").ifPresent(plugin -> {
                if (classExists("p1xel{}nobuildplus{}NoBuildPlus")) {
                    consumer.accept(() -> new NoBuildPlusCompatibility(plugin));
                }
            });
        }
    }
}