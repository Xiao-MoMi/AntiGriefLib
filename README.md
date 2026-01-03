### Supported Plugins
BentoBox \
CrashClaim \
Dominion \
FabledSkyBlock \
FactionsUUID \
GriefDefender \
GriefPrevention \
HuskClaims \
HuskTowns \
IridiumSkyBlock \
KingdomsX \
Landlord \
Lands \
NoBuildPlus \
PlotSquared \
PreciousStones \
ProtectionStones \
RedProtect \
Residence \
SaberFactions \
SuperiorSkyBlock \
Towny \
UltimateClaims \
UltimateClans \
WorldGuard \
XClaim

### Use this project as dependency
Gradle (kts)
```kotlin
repositories {
    maven("https://repo.momirealms.net/releases/")
}
```
```kotlin
dependencies {
    implementation("net.momirealms:antigrieflib:{VERSION}") // check the version in gradle.properties
}
```

### API Guide

```kotlin
// Create a lib instance on plugin enable
val antiGrief: AntiGriefLib = AntiGriefLib.builder(javaPlugin)
    .ignoreOP(true)
    .silentLogs(false)
    .bypassPermission("myplugin.bypass")
    .suppressErrors(false)
    // remove the default WorldGuard compatibility implementation
    .exclude(java.util.function.Predicate {p: org.bukkit.plugin.Plugin -> p.name == "WorldGuard"})
    // register your own custom WorldGuard implementation, so you can use custom flags
    .register(MyCustomWorldGuardCompatibility())
    .build()

// use the api to check permissions
if (!antiGrief.canPlace(player, location)) {
    player.sendMessage(Component.text("You can't place it here!"))
}
```