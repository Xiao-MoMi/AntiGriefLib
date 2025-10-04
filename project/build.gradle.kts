repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")                 // Paper, PlotSquared
    maven("https://maven.enginehub.org/repo/")                                // WorldGuard
    maven("https://jitpack.io/")                                              // Lands, GriefPrevention
    maven("https://nexus.iridiumdevelopment.net/repository/maven-releases/")  // IridiumSkyBlock
    maven("https://repo.codemc.org/repository/maven-public/")                 // BentoBox
    maven("https://repo.glaremasters.me/repository/bloodshot/")               // GriefDefender
    maven("https://repo.william278.net/releases/")                            // HuskTowns, HuskClaims
    maven("https://repo.bg-software.com/repository/api/")                     // SuperiorSkyBlock
    maven("https://repo.glaremasters.me/repository/towny/")                   // Towny
    maven("https://repo.songoda.com/repository/minecraft-plugins/")           // FabledSkyBlock, UltimateClaims
    maven("https://ci.ender.zone/plugin/repository/everything/")              // FactionsUUID
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")     // RedProtect
    maven("https://eldonexus.de/repository/maven-releases/")                  // Landlord
    maven("https://www.uskyblock.ovh/maven/uskyblock/")                       // uSkyBlock
}

@Suppress
dependencies {
    implementation(project(":common"))
    implementation(project(":conflict-packages"))                                 // SaberFactions

    compileOnly("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")                // Paper
    compileOnly("com.sk89q.worldguard:worldguard-bukkit:7.0.9")                   // WorldGuard
    compileOnly("com.github.angeschossen:LandsAPI:7.17.2")                         // Lands
    compileOnly("com.iridium:IridiumSkyblock:4.0.8")                              // IridiumSkyBlock
    compileOnly("world.bentobox:bentobox:2.1.0-SNAPSHOT")                         // BentoBox
    compileOnly("com.griefdefender:api:2.1.0-SNAPSHOT")                           // GriefDefender
    compileOnly("net.william278.husktowns:husktowns-bukkit:3.1.4")                // HuskTowns
    compileOnly("net.william278.huskclaims:huskclaims-bukkit:1.5.10")              // HuskClaims
    compileOnly("com.intellectualsites.plotsquared:plotsquared-bukkit:7.3.5")     // PlotSquared
    compileOnly("com.bgsoftware:SuperiorSkyblockAPI:2025.1")                      // SuperiorSkyBlock2 - API
    compileOnly("com.palmergames.bukkit.towny:towny:0.101.2.5")                  // Towny
    compileOnly("com.craftaro:FabledSkyBlock:3.0.4")                              // FabledSkyBlock
    compileOnly("com.craftaro:UltimateClaims:2.2.1")                              // UltimateClaims
    compileOnly("com.github.TechFortress:GriefPrevention:16.18.2")                // GriefPrevention
    compileOnly("com.massivecraft:Factions:1.6.9.5-U0.6.40")                      // FactionsUUID
    compileOnly("biz.princeps:landlord-core:4.365")                               // Landlord
    compileOnly("com.github.UlrichBR:UClans-API:8.14.0")                        // UltimateClans
    compileOnly("cn.lunadeer:DominionAPI:4.6.0")                                    // UltimateClans

    compileOnly(files("libs/Residence-pruned.jar"))                        // Residence
    compileOnly(files("libs/KingdomsX-pruned.jar"))                        // KingdomsX
    compileOnly(files("libs/CrashClaim-pruned.jar"))                       // CrashClaim
    compileOnly(files("libs/XClaim.jar"))                                  // XClaim
    compileOnly(files("libs/SuperiorSkyblock2-2025.1.jar"))                // SuperiorSkyBlock2 - Plugin
    compileOnly(files("libs/uSkyBlock-3.0.0.jar"))                         // uSkyBlock
    compileOnly(files("libs/PreciousStones-1.17.1.2.jar"))                 // PreciousStones
    compileOnly(files("libs/hClaims-pruned.jar"))                          // hClaims
    compileOnly(files("libs/NoBuildPlus-1.5.7.jar"))                       // NoBuildPlus

    compileOnly("io.github.fabiozumbi12.RedProtect:RedProtect-Core:8.1.1") { exclude(group = "*") }        // RedProtect
    compileOnly("io.github.fabiozumbi12.RedProtect:RedProtect-Spigot:8.1.1") { exclude(group = "*") }      // RedProtect
}

java {
    withSourcesJar()
}

artifacts {
    archives(tasks.shadowJar)
}

tasks {
    shadowJar {
        archiveClassifier = ""
        archiveFileName = "AntiGriefLib-${rootProject.properties["project_version"]}.jar"
    }
}

publishing {
    repositories {
        maven {
            url = uri("https://repo.momirealms.net/releases")
            credentials(PasswordCredentials::class) {
                username = System.getenv("REPO_USERNAME")
                password = System.getenv("REPO_PASSWORD")
            }
        }
    }
    publications {
        create<MavenPublication>("mavenJava") {
            groupId = "net.momirealms"
            artifactId = "antigrieflib"
            version = rootProject.properties["project_version"].toString()
            artifact(tasks["sourcesJar"])
            from(components["shadow"])
        }
    }
}