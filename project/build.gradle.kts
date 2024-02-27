repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")                 // Paper, PlotSquared
    maven("https://maven.enginehub.org/repo/")                                // WorldGuard
    maven("https://jitpack.io/")                                              // Lands
    maven("https://nexus.iridiumdevelopment.net/repository/maven-releases/")  // IridiumSkyblock
    maven("https://repo.codemc.org/repository/maven-public/")                 // BentoBox
    maven("https://repo.glaremasters.me/repository/bloodshot/")               // GriefDefender
    maven("https://repo.william278.net/releases/")                            // HuskTowns, HuskClaims
    maven("https://repo.bg-software.com/repository/api/")                     // SuperiorSkyblock
    maven("https://repo.glaremasters.me/repository/towny/")                   // Towny
    maven("https://repo.craftaro.com/repository/minecraft-plugins/")          // FabledSkyBlock
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")                // Paper
    compileOnly("com.sk89q.worldguard:worldguard-bukkit:7.0.9")                   // WorldGuard
    compileOnly("com.github.angeschossen:LandsAPI:6.44.10")                       // Lands
    compileOnly("com.iridium:IridiumSkyblock:4.0.8")                              // IridiumSkyblock
    compileOnly("world.bentobox:bentobox:2.1.0-SNAPSHOT")                         // BentoBox
    compileOnly("com.griefdefender:api:2.1.0-SNAPSHOT")                           // GriefDefender
    compileOnly("net.william278.husktowns:husktowns-bukkit:3.0.2")                // HuskTowns
    compileOnly("net.william278.huskclaims:huskclaims-bukkit:1.0.3")              // HuskClaims
    compileOnly("com.intellectualsites.plotsquared:plotsquared-bukkit:7.3.5")     // PlotSquared
    compileOnly("com.bgsoftware:SuperiorSkyblockAPI:2023.3")                      // SuperiorSkyblock2
    compileOnly("com.palmergames.bukkit.towny:towny:0.100.1.17")                  // Towny
    compileOnly("com.craftaro:FabledSkyBlock:3.0.4")                              // FabledSkyBlock
}

